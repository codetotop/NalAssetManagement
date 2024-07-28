package com.example.nalassetmanagement.screen.inventory.inventory_detail

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.nalassetmanagement.common.Constants
import com.example.nalassetmanagement.databinding.ActivityInventoryDetailsBinding
import com.example.nalassetmanagement.model.inventory.InventorySession
import com.example.nalassetmanagement.model.local.ObjectFilter
import com.example.nalassetmanagement.model.server.Asset
import com.example.nalassetmanagement.screen.inventory.InventoryActivity
import com.example.nalassetmanagement.view.custom.ActionBarView
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class InventorySessionDetailActivity : AppCompatActivity(), InventoryDetailContract.View,
    ActionBarView.ActionBarViewListener {

    private lateinit var presenter: InventoryDetailContract.Presenter
    private var inventorySession  : InventorySession? = null

    private val inventoryBottomSheet = InventoryBottomSheet()

    private var listAsset = mutableListOf<Asset>()

    private val binding by lazy { ActivityInventoryDetailsBinding.inflate(layoutInflater) }

    private val qrCodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(this@InventorySessionDetailActivity, "Qr code không hợp lệ!", Toast.LENGTH_LONG).show()
        } else {
            presenter.searchQrCodeFormLocal(result.contents)
            binding.searchView.setText(result.contents)
        }
    }
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showCamera()
            } else {
                Toast.makeText(
                    this@InventorySessionDetailActivity,
                    "Hãy mở Camera để quét mã!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    private fun checkPermissionEndShowActivity(context: Context) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            showCamera()
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {

        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun showCamera() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan QR code")
        options.setCameraId(0) // Use a specific camera of the device
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        options.setOrientationLocked(false)
        qrCodeLauncher.launch(options)
    }

    private val adapterRcvUnChecked by lazy {
        InventorySessionDetailsAdapter(object : InventorySessionDetailsAdapter.OnClickItemAsset {
            override fun onClickItemAsset(item: Asset) {
                showBottomSheet(item = item)
            }
        })
    }
    private val adapterRcvChecked by lazy {
        InventorySessionDetailsAdapter(object : InventorySessionDetailsAdapter.OnClickItemAsset {
            override fun onClickItemAsset(item: Asset) {
               showBottomSheet(item = item)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        presenter = InventoryDetailPresenter(this)
        presenter.getAllAsset()
        binding.toolbar.setActionBarViewListener(this)
        binding.recyclerViewUnchecked.adapter = adapterRcvUnChecked
        binding.recyclerViewChecked.adapter = adapterRcvChecked

        presenter.getAssetsFromLocal()
        getDataIntent()
        binding.qrCode.setOnClickListener {
            checkPermissionEndShowActivity(context = this)
        }

        handleSearchView()
    }
    private fun getDataIntent() {
        inventorySession = intent?.getSerializableExtra(InventoryActivity.BUNDLE_TAG) as? InventorySession
        if (inventorySession != null) {
            presenter.getInventorySessionDetails(inventorySession!!)
        }
    }

    override fun loadData() {

    }

    override fun getAllAssetSuccess(listAsset: MutableList<Asset>) {
        this@InventorySessionDetailActivity.listAsset  = listAsset
    }

    override fun getAssetsFromLocalSuccess(
        listAssetsChecked: MutableList<Asset>,
        listAssetsNotChecked: MutableList<Asset>
    ) {
        binding.apply {
            if (listAssetsNotChecked.size == 0) {
                gr1.visibility = View.GONE
                dataInventoryAsset.weightSum = 1F
            } else {
                gr1.visibility = View.VISIBLE
                dataInventoryAsset.weightSum = 2F
            }
            if (listAssetsChecked.size == 0) {
                gr2.visibility = View.GONE
                dataInventoryAsset.weightSum = 1F
            } else {
                gr2.visibility = View.VISIBLE
                dataInventoryAsset.weightSum = 2F
            }
        }
        adapterRcvChecked.submitList(listAssetsChecked)
        adapterRcvUnChecked.submitList(listAssetsNotChecked)

    }

    fun showBottomSheet(item: Asset) {
        if (item.id == null || inventorySession == null) return
        if (inventorySession!!.id == null) return

        val itemAssetInventorySession =
            presenter.findInListAssetInventorySession(item.id, inventorySession!!.id!!)

        inventoryBottomSheet.showDialog(
            this.supportFragmentManager,
            "TAG",
            itemAssetInventorySession
        )
        inventoryBottomSheet.onSuccessInventorySession = {
            presenter.addAssetAlreadyInInventory(it) {
                presenter.getInventorySessionDetails(inventorySession!!)
            }
        }
    }

    private fun handleSearchView() {
        binding.searchView.addTextChangedListener( object  : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                presenter.searchNameAssetOrQrCodeOrNameUserUse(listAsset, s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }
    private fun showDialog() {
        Toast.makeText(this@InventorySessionDetailActivity.applicationContext, "Đã kiểm kê thành công", Toast.LENGTH_SHORT).show()
    }
    override fun getAssetsFromLocalError() {

    }

    override fun onClickLeftButton() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun onClickRightButton() {

    }
}
