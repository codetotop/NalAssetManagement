package com.example.nalassetmanagement.screen.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.example.nalassetmanagement.R
import com.example.nalassetmanagement.common_view.ActionBarView
import com.example.nalassetmanagement.databinding.ActivityMainBinding
import com.example.nalassetmanagement.model.Data
import com.example.nalassetmanagement.screen.asset_list.AssetListFragment
import com.example.nalassetmanagement.screen.inventory.InventoryFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity(), MainContract.View, ActionBarView.ActionBarViewListener {

    private lateinit var mainPresenter: MainContract.Presenter
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().setKeepOnScreenCondition {
            runBlocking {
                delay(1500)
                false
            }
        }

        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        mainPresenter = MainPresenter(this)
        initView()
        addListener()
    }

    private fun initView() {

    }

    private fun addListener() {

        mainBinding.abvMain.setActionBarViewListener(this)
    }

    override fun loginSuccess(data: Data) {

    }

    override fun loginFailure() {

    }

    override fun onClickLeftButton() {
        Toast.makeText(this, "Left click", Toast.LENGTH_LONG).show()
    }

    override fun onClickRightButton() {
        Toast.makeText(this, "Right click", Toast.LENGTH_LONG).show()
    }
}
