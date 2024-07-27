package com.example.nalassetmanagement.screen.asset_info

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nalassetmanagement.R
import com.example.nalassetmanagement.common.Constants
import com.example.nalassetmanagement.view.custom.ActionBarView
import com.example.nalassetmanagement.databinding.ActivityAssetInfoBinding
import com.example.nalassetmanagement.model.server.Asset
import com.example.nalassetmanagement.screen.asset_info.asset_depreciation.AssetDepreciationActivity
import com.example.nalassetmanagement.screen.asset_info.asset_detail.AssetDetailActivity
import com.example.nalassetmanagement.screen.asset_info.asset_location.AssetLocationActivity
import com.example.nalassetmanagement.view.extension.loadWithPicasso

class AssetInfoActivity : AppCompatActivity(), AssetInfoContract.View,
    ActionBarView.ActionBarViewListener {

    private lateinit var presenter: AssetInfoContract.Presenter
    private lateinit var binding: ActivityAssetInfoBinding

    private var assetDetail: Asset? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssetInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        callApi()
        addListener()
    }

    private fun initView() {
        binding.imgAssetPhoto.loadWithPicasso("")
    }

    private fun callApi() {
        val extras = intent.extras
        val id = extras?.getInt(Constants.KEY_ID, -1) ?: -1
        presenter = AssetInfoPresenter(this)
        presenter.fetchAssetDetail(id)
    }

    private fun addListener() {
        binding.abvAssetInfo.setActionBarViewListener(this)
        binding.btnAssetDetail.setOnClickListener {
            val intent = Intent(this, AssetDetailActivity::class.java)
            intent.putExtra(Constants.KEY_ASSET_DETAIL, assetDetail)
            startActivity(intent)
        }
        binding.btnAssetDepreciation.setOnClickListener {
            val intent = Intent(this, AssetDepreciationActivity::class.java)
            startActivity(intent)
        }
        binding.btnAssetLocation.setOnClickListener {
            val intent = Intent(this, AssetLocationActivity::class.java)
            startActivity(intent)
        }
    }

    override fun fetchAssetDetailSuccess(data: Asset?) {
        binding.loading.visibility = View.GONE
        assetDetail = data
    }

    override fun fetchAssetDetailFailure() {
        binding.loading.visibility = View.GONE
    }

    override fun onClickLeftButton() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun onClickRightButton() {

    }
}
