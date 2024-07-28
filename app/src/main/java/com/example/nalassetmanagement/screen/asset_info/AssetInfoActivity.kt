package com.example.nalassetmanagement.screen.asset_info

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        val extras = intent.extras
        val modelId = extras?.getInt(Constants.KEY_MODEL_ID, -1) ?: -1
        binding.imgAssetPhoto.loadWithPicasso("", modelId)
    }

    private fun callApi() {

    }

    private fun addListener() {
        binding.abvAssetInfo.setActionBarViewListener(this)
        binding.btnAssetDetail.setOnClickListener {

            val extras = intent.extras
            val assetId = extras?.getInt(Constants.KEY_ASSET_ID, -1) ?: -1
            val intent = Intent(this, AssetDetailActivity::class.java)
            intent.putExtra(Constants.KEY_ASSET_ID, assetId)
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

    override fun onClickLeftButton() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun onClickRightButton() {

    }
}
