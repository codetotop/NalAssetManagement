package com.example.nalassetmanagement.screen.asset_list

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.nalassetmanagement.R
import com.example.nalassetmanagement.common.Constants
import com.example.nalassetmanagement.view.custom.ActionBarView
import com.example.nalassetmanagement.databinding.ActivityAssetListBinding
import com.example.nalassetmanagement.model.server.Asset
import com.example.nalassetmanagement.model.Data
import com.example.nalassetmanagement.model.server.AssetList
import com.example.nalassetmanagement.screen.asset_filter.AssetFilterActivity
import com.example.nalassetmanagement.screen.asset_info.AssetInfoActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class AssetListActivity : AppCompatActivity(), AssetListContract.View,
    ActionBarView.ActionBarViewListener,
    AssetListAdapter.OnClickListener {

    private lateinit var presenter: AssetListContract.Presenter
    private lateinit var binding: ActivityAssetListBinding

    private lateinit var assetListResponses: List<Asset>
    private lateinit var assetListAdapter: AssetListAdapter

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showCamera()
            } else {
                Toast.makeText(this@AssetListActivity, "Hãy mở Camera để quét mã!", Toast.LENGTH_LONG)
                    .show()
            }
        }

    private val qrCodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(this@AssetListActivity, "Qr code không hợp lệ!", Toast.LENGTH_LONG)
                .show()
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
        options.setBeepEnabled(false)
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
        assetListAdapter = AssetListAdapter(listOf(), this)
        binding.rcvAssetList.adapter = assetListAdapter
    }

    private fun callApi() {
        presenter = AssetListPresenter(this)
        presenter.fetchAssetList(1)
    }

    private fun addListener() {
        binding.abvAssetList.setActionBarViewListener(this)
        binding.imgFilter.setOnClickListener {
            val intent = Intent(this, AssetFilterActivity::class.java)
            startActivity(intent)
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
        Toast.makeText(this, "Mã qr hợp lệ!" + asset.qrCode, Toast.LENGTH_SHORT).show()
    }

    override fun searchQrFailure() {
        binding.loading.visibility = View.GONE
        Toast.makeText(this, "Mã qr không hợp lệ hoặc tài sản không có trong danh sách!", Toast.LENGTH_SHORT).show()
    }

    override fun onClickLeftButton() {

    }

    override fun onClickRightButton() {

    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, AssetInfoActivity::class.java)
        intent.putExtra(Constants.KEY_ASSET_ID, assetListResponses[position].id)
        intent.putExtra(Constants.KEY_CATEGORY_ID, assetListResponses[position].categoryId)
        startActivity(intent)
    }
}
