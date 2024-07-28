package com.example.nalassetmanagement.screen.asset_info.asset_location

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nalassetmanagement.databinding.ActivityAssetLocationBinding
import com.example.nalassetmanagement.model.local.AssetLocation
import com.example.nalassetmanagement.view.custom.ActionBarView

class AssetLocationActivity : AppCompatActivity(), AssetLocationContract.View,
    ActionBarView.ActionBarViewListener {

    private lateinit var presenter: AssetLocationContract.Presenter
    private lateinit var binding: ActivityAssetLocationBinding
    private lateinit var locationAdapter: AssetLocationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAssetLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

     initView()
     addListener()
    }

    private fun initView() {

        locationAdapter = AssetLocationAdapter(listOf(
            AssetLocation("20/06/2020 14h30", "vietbq", "Kosmo - Tây Hồ"),
            AssetLocation("16/09/2021 10h30", "dungnb", "Nal building"),
            AssetLocation("24/10/2022 16h30", "vietbq", "Nal building"),
            AssetLocation("22/09/2023 17h30", "thaott", "Kosmo - Tây Hồ"),
            AssetLocation("11/06/2023 08h30", "cuongnm1", "Kosmo - Tây Hồ"),
            AssetLocation("05/06/2024 08h30", "dungnb", "Kosmo - Tây Hồ"),
            AssetLocation("09/03/2024 15h30", "nguyetntt", "Kosmo - Tây Hồ"),
            AssetLocation("17/02/2023 14h30", "hoangpm1", "Kosmo - Tây Hồ"),

        ))

        binding.rcvLocationList.adapter = locationAdapter
    }

    private fun addListener() {
        binding.abvAssetLocation.setActionBarViewListener(this)
    }

    override fun loadData() {

    }

    override fun loadError() {

    }

    override fun onClickLeftButton() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun onClickRightButton() {

    }
}
