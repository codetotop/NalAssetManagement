package com.example.nalassetmanagement.screen.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.example.nalassetmanagement.R
import com.example.nalassetmanagement.databinding.ActivityMainBinding
import com.example.nalassetmanagement.model.Data
import com.example.nalassetmanagement.screen.asset_list.AssetListFragment
import com.example.nalassetmanagement.screen.inventory.InventoryFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var mainPresenter: MainContract.Presenter
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().setKeepOnScreenCondition {
            runBlocking {
                delay(3000)
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
        // fragment default is AssetListFragment
        replaceFragment(AssetListFragment.newInstance())
    }

    private fun addListener() {
        /*mainBinding.btnLogin.setOnClickListener {
            mainPresenter.login("abcd", "1234")
        }*/

        mainBinding.bnvMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.asset_list -> {
                    replaceFragment(AssetListFragment.newInstance())
                }

                R.id.asset_inventory -> {
                    replaceFragment(InventoryFragment.newInstance())
                }

                else -> {
                    replaceFragment(AssetListFragment.newInstance())
                }
            }

            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flMain, fragment)
        fragmentTransaction.commit()
    }

    override fun loginSuccess(data: Data) {

    }

    override fun loginFailure() {

    }
}