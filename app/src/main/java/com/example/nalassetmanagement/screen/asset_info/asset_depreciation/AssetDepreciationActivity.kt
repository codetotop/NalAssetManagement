package com.example.nalassetmanagement.screen.asset_info.asset_depreciation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.nalassetmanagement.R
import com.example.nalassetmanagement.databinding.ActivityAssetDepreciationBinding
import com.example.nalassetmanagement.databinding.ActivityAssetDetailBinding
import com.example.nalassetmanagement.view.custom.ActionBarView

class AssetDepreciationActivity : AppCompatActivity(), AssetDepreciationContract.View,
    ActionBarView.ActionBarViewListener {

    private lateinit var binding: ActivityAssetDepreciationBinding
    private lateinit var presenter: AssetDepreciationContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAssetDepreciationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        addListener()
    }

    private fun initView() {

    }

    private fun addListener() {

        binding.abvAssetDepreciation.setActionBarViewListener(this)
    }

    override fun loadAssetList() {

    }

    override fun onClickLeftButton() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun onClickRightButton() {

    }
}
