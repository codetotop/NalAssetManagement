package com.example.nalassetmanagement.screen.asset_info.asset_depreciation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.nalassetmanagement.R

class AssetDepreciationActivity : AppCompatActivity(), AssetDepreciationContract.View {

    private lateinit var presenter: AssetDepreciationContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun loadAssetList() {

    }
}
