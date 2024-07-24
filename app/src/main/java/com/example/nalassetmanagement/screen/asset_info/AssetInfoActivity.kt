package com.example.nalassetmanagement.screen.asset_info

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nalassetmanagement.common_view.ActionBarView
import com.example.nalassetmanagement.databinding.ActivityAssetInfoBinding
import com.example.nalassetmanagement.screen.asset_list.loadWithPicasso

class AssetInfoActivity : AppCompatActivity(), AssetInfoContract.View,
    ActionBarView.ActionBarViewListener {

    private lateinit var presenter: AssetInfoContract.Presenter
    private lateinit var binding: ActivityAssetInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAssetInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = AssetInfoPresenter(this)
        initView()
        addListener()
    }

    private fun initView() {
        binding.imgAssetPhoto.loadWithPicasso("")
    }

    private fun addListener() {
        binding.abvAssetInfo.setActionBarViewListener(this)
    }

    override fun loadData() {

    }

    override fun onClickLeftButton() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun onClickRightButton() {
        Toast.makeText(this, "Right click", Toast.LENGTH_LONG).show()
    }
}
