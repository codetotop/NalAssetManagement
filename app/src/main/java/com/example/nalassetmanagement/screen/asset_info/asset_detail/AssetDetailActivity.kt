package com.example.nalassetmanagement.screen.asset_info.asset_detail

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.nalassetmanagement.R
import com.example.nalassetmanagement.common.Constants
import com.example.nalassetmanagement.common.selectedPosition
import com.example.nalassetmanagement.common.singleSelected
import com.example.nalassetmanagement.databinding.ActivityAssetDetailBinding
import com.example.nalassetmanagement.model.local.ObjectFilter
import com.example.nalassetmanagement.model.server.Asset
import com.example.nalassetmanagement.model.server.FilterList
import com.example.nalassetmanagement.model.server.KeyValue
import com.example.nalassetmanagement.screen.asset_filter.bottom_sheet_adapter.ObjectFilterAdapter
import com.example.nalassetmanagement.view.custom.ActionBarView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions


class AssetDetailActivity : AppCompatActivity(), AssetDetailContract.View,
    ActionBarView.ActionBarViewListener {

    private lateinit var presenter: AssetDetailContract.Presenter
    private lateinit var binding: ActivityAssetDetailBinding

    private var assetDetail: Asset? = null
    private lateinit var statusList: ArrayList<ObjectFilter>
    private lateinit var statusAdapter: ObjectFilterAdapter

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showCamera()
            } else {
                // Show why you need permission
            }
        }

    private val qrCodeLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(this@AssetDetailActivity, "Qr code không hợp lệ!", Toast.LENGTH_LONG).show()
        } else {
            presenter.updateStatusAsset(assetDetail.id, "qr")
            Toast.makeText(
                this@AssetDetailActivity,
                "Scanned: " + result.contents,
                Toast.LENGTH_LONG
            ).show()
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
        super.onCreate(savedInstanceState)

        binding = ActivityAssetDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        updateView()
        callApi()
        addListener()
    }

    private fun initView() {
        binding.loading.visibility = View.VISIBLE

        val extras = intent.extras
        assetDetail = extras?.getSerializable(Constants.KEY_ASSET_DETAIL) as Asset?
    }

    private fun updateView() {
        binding.blockStatus.tvStatus.text = assetDetail?.status?.name ?: "Tốt"
        binding.blockCategory.tvCategoryName.text = assetDetail?.category?.name ?: "Máy tính"
        binding.blockProductDes.tvProducer.text = assetDetail?.producer?.name ?: "Apple Inc"
        binding.blockProductDes.tvModel.text = assetDetail?.models?.name ?: "Mackbook Pro 038"
        binding.blockProductOwner.tvProductOwner1.text =
            assetDetail?.address?.name ?: "Nal building"
    }

    private fun callApi() {
        presenter = AssetDetailPresenter(this)
        presenter.fetchFilterList()
    }

    private fun addListener() {
        binding.abvAssetDetail.setActionBarViewListener(this)
        binding.blockQrCode.imgQrCode.setOnClickListener {

        }

        binding.blockStatus.imgUpdateStatus.setOnClickListener {
            showStatusBottomSheet()
        }

        binding.blockQrCode.imgQrCode.setOnClickListener {
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

    private fun loadStatusList(statusList: List<KeyValue>) {

        this.statusList = arrayListOf()
        for (index in statusList.indices) {
            statusList[index].name?.let {
                var selected = false
                if (statusList[index].id == assetDetail?.statusId) selected = true
                this.statusList.add(
                    ObjectFilter(
                        statusList[index].id,
                        statusList[index].name,
                        selected
                    )
                )
            }
            if (index > 2) return
        }

        if (this.statusList.size == 1) {
            this.statusList.addAll(
                arrayListOf(
                    ObjectFilter(0, "Tốt", false),
                    ObjectFilter(1, "Bình thường", false),
                    ObjectFilter(2, "Tệ", false),
                )
            )
        }
    }

    private fun showStatusBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_update_status, null)
        val rcvProducer = view.findViewById<RecyclerView>(R.id.rcvStatusList)
        val selectionPosition = statusList.selectedPosition()
        statusAdapter =
            ObjectFilterAdapter(statusList, object : ObjectFilterAdapter.OnClickListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemClick(position: Int) {
                    rcvProducer.post {
                        statusAdapter.notifyDataSetChanged()
                        statusList.singleSelected(position)
                    }
                }
            })

        val btnClose = view.findViewById<TextView>(R.id.tvClose)
        val btnUpdate = view.findViewById<TextView>(R.id.tvUpdate)
        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        btnUpdate.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            dialog.dismiss()
            presenter.updateStatusAsset(
                assetDetail?.id!!,
                "status",
                "",
                statusList[statusList.selectedPosition()].id.toString()
            )
        }

        statusAdapter.setSelectedPosition(selectionPosition)
        rcvProducer.adapter = statusAdapter
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    override fun onClickLeftButton() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun onClickRightButton() {

    }

    override fun fetchFilterListSuccess(data: FilterList?) {
        binding.loading.visibility = View.GONE
        loadStatusList(data?.status ?: listOf())
        Toast.makeText(this, "Lấy thông tin thành công!", Toast.LENGTH_SHORT).show()
    }

    override fun fetchFilterListFailure() {
        binding.loading.visibility = View.GONE
    }

    override fun updateStatusSuccess() {
        binding.loading.visibility = View.GONE
        binding.blockStatus.tvStatus.text = statusList[statusList.selectedPosition()].name
        Toast.makeText(this, "Cập nhật trạng thái thành công!", Toast.LENGTH_LONG).show()
    }

    override fun updateStatusFailure() {
        Toast.makeText(this, "Cập nhật trạng thái không thành công!", Toast.LENGTH_LONG).show()
    }
}
