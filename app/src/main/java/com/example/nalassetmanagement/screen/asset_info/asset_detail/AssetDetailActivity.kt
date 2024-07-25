package com.example.nalassetmanagement.screen.asset_info.asset_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nalassetmanagement.databinding.ActivityAssetDetailBinding
import com.example.nalassetmanagement.view.custom.ActionBarView

class AssetDetailActivity : AppCompatActivity(), AssetDetailContract.View,
    ActionBarView.ActionBarViewListener {

    private lateinit var presenter: AssetDetailContract.Presenter
    private lateinit var binding: ActivityAssetDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAssetDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

     initView()
     addListener()
    }

    private fun initView() {

    }

    private fun addListener() {
        binding.abvAssetDetail.setActionBarViewListener(this)
    }

    override fun loadData() {

    }

    override fun onClickLeftButton() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun onClickRightButton() {

    }
}
