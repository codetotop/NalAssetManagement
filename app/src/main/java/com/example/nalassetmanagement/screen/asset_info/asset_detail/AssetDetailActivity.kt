package com.example.nalassetmanagement.screen.asset_info.asset_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AssetDetailActivity : AppCompatActivity(), AssetDetailContract.View {

    private lateinit var presenter: AssetDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun loadData() {

    }
}
