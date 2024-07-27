package com.example.nalassetmanagement.screen.asset_list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class AssetListActivity : AppCompatActivity(), AssetListContract.View,
    ActionBarView.ActionBarViewListener,
    AssetListAdapter.OnClickListener {

    private lateinit var presenter: AssetListContract.Presenter
    private lateinit var binding: ActivityAssetListBinding

    private lateinit var assetListResponses: List<Asset>
    private lateinit var assetListAdapter: AssetListAdapter
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

    override fun onClickLeftButton() {

    }

    override fun onClickRightButton() {

    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, AssetInfoActivity::class.java)
        intent.putExtra(Constants.KEY_ID, assetListResponses[position].id)
        startActivity(intent)
    }
}
