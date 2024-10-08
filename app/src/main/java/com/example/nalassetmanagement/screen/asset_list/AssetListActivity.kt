package com.example.nalassetmanagement.screen.asset_list

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.nalassetmanagement.R
import com.example.nalassetmanagement.common.Constants
import com.example.nalassetmanagement.databinding.ActivityAssetListBinding
import com.example.nalassetmanagement.model.Data
import com.example.nalassetmanagement.model.local.ObjectFilter
import com.example.nalassetmanagement.model.server.Asset
import com.example.nalassetmanagement.model.server.AssetList
import com.example.nalassetmanagement.screen.asset_filter.AssetFilterActivity
import com.example.nalassetmanagement.screen.asset_info.AssetInfoActivity
import com.example.nalassetmanagement.screen.inventory.InventoryActivity
import com.example.nalassetmanagement.view.custom.ActionBarView
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


class AssetListActivity : AppCompatActivity(), AssetListContract.View,
    ActionBarView.ActionBarViewListener,
    AssetListAdapter.AssetListEventListener {

    private lateinit var presenter: AssetListContract.Presenter
    private lateinit var binding: ActivityAssetListBinding

    private lateinit var assetListResponses: List<Asset>
    private lateinit var assetListAdapter: AssetListAdapter

    private val filterLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data?.extras

            data?.let {
                val userFilter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getSerializable(Constants.KEY_FILTER_USER, ObjectFilter::class.java)
                } else {
                    it.getSerializable(Constants.KEY_FILTER_USER) as ObjectFilter?
                }
                val categoryFilter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getSerializable(
                        Constants.KEY_FILTER_CATEGORY,
                        ObjectFilter::class.java
                    )
                } else {
                    it.getSerializable(Constants.KEY_FILTER_CATEGORY) as ObjectFilter?
                }
                val addressFilter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getSerializable(
                        Constants.KEY_FILTER_ADDRESS,
                        ObjectFilter::class.java
                    )
                } else {
                    it.getSerializable(Constants.KEY_FILTER_ADDRESS) as ObjectFilter?
                }
                val producerFilter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getSerializable(
                        Constants.KEY_FILTER_PRODUCER,
                        ObjectFilter::class.java
                    )
                } else {
                    it.getSerializable(Constants.KEY_FILTER_PRODUCER) as ObjectFilter?
                }

                assetListAdapter.filterList(
                    assetListResponses,
                    user = userFilter,
                    category = categoryFilter,
                    address = addressFilter,
                    producer = producerFilter
                )

            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showCamera()
            } else {
                Toast.makeText(
                    this@AssetListActivity,
                    "Hãy mở Camera để quét mã!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    private val qrCodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            //Toast.makeText(this@AssetListActivity, "Qr code không hợp lệ!", Toast.LENGTH_LONG).show()
        } else {
            binding.loading.visibility = View.VISIBLE
            presenter.searchQr(result.contents, assetListResponses)
        }
    }

    private fun showCamera() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan QR code")
        options.setCameraId(0) // Use a specific camera of the device
        options.setBeepEnabled(true)
        options.setBarcodeImageEnabled(true)
        options.setOrientationLocked(false)
        qrCodeLauncher.launch(options)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().setKeepOnScreenCondition {
            runBlocking {
                delay(1500)
                false
            }
        }

        super.onCreate(savedInstanceState)
        binding = ActivityAssetListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        callApi()
        addListener()
    }

    private fun initView() {
        binding.loading.visibility = View.VISIBLE
        assetListResponses = listOf()
        assetListAdapter = AssetListAdapter(listOf(), this)
        binding.rcvAssetList.adapter = assetListAdapter
    }

    private fun callApi() {
        presenter = AssetListPresenter(this)
        presenter.fetchAssetList(1)
    }

    private fun addListener() {
        binding.abvAssetList.setActionBarViewListener(this)
        binding.swRefreshAssetList.setOnRefreshListener {
            binding.swRefreshAssetList.isRefreshing = false
            binding.edtSearch.text?.clear()
            presenter.fetchAssetList(1)
        }

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do Nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                assetListAdapter.searchEditText(assetListResponses, s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // Do Nothing
            }

        })
        binding.imgFilter.setOnClickListener {
            val intent = Intent(this, AssetFilterActivity::class.java)

            filterLauncher.launch(intent)
        }

        binding.imgQrCode.setOnClickListener {
            checkPermissionEndShowActivity(this)
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

    override fun loginSuccess(data: Data) {

    }

    override fun loginFailure() {

    }

    override fun fetchAssetListSuccess(data: AssetList?) {
        binding.loading.visibility = View.GONE
        data?.data?.let {
            assetListResponses = it
            assetListAdapter.replaceList(assetListResponses)
        }
    }

    override fun fetchAssetListFailure() {
        binding.loading.visibility = View.GONE
        Toast.makeText(this, getString(R.string.msg_call_api_failure), Toast.LENGTH_SHORT).show()
    }

    override fun searchQrSuccess(asset: Asset) {
        binding.loading.visibility = View.GONE
        assetListAdapter.replaceList(listOf(asset))
        Toast.makeText(this, "Mã qr hợp lệ!" + asset.qrCode, Toast.LENGTH_SHORT).show()
    }

    override fun searchQrFailure() {
        binding.loading.visibility = View.GONE
        assetListAdapter.replaceList(listOf())
        Toast.makeText(
            this,
            "Mã qr không hợp lệ hoặc tài sản không có trong danh sách!",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onClickLeftButton() {

    }

    override fun onClickRightButton() {
        startActivity(Intent(this, InventoryActivity::class.java))

    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, AssetInfoActivity::class.java)
        intent.putExtra(Constants.KEY_ASSET_ID, assetListResponses[position].id)
        intent.putExtra(Constants.KEY_MODEL_ID, assetListResponses[position].modelId)

        activityResultLauncher.launch(intent)
    }

    private var activityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            callApi()
        }

    override fun onAssetListChange(list: List<Asset>) {
        if (list.isEmpty()) {
            binding.tvNoData.visibility = View.VISIBLE
        } else {
            binding.tvNoData.visibility = View.GONE
        }
    }
}
