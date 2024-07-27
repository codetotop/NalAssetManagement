package com.example.nalassetmanagement.screen.inventory

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nalassetmanagement.databinding.ActivityInventoryBinding
import com.example.nalassetmanagement.model.Asset
import com.example.nalassetmanagement.model.inventory.InventorySession
import com.example.nalassetmanagement.screen.inventory.inventory_detail.InventoryBottomSheet
import com.example.nalassetmanagement.screen.inventory.inventory_detail.InventorySessionDetailActivity


class InventoryActivity : AppCompatActivity(), InventoryContract.View {
    companion object {
        const val BUNDLE_TAG = "InventoryActivity"
    }

    private lateinit var presenter: InventoryContract.Presenter

    private val inventoryBottomSheet by lazy { InventoryBottomSheet() }

    private val binding by lazy { ActivityInventoryBinding.inflate(layoutInflater) }
    private val adapterRcvInventoryInProgress by lazy { InventorySessionAdapter(onClickItemInventorySession =
        object : InventorySessionAdapter.OnClickItemInventorySession {
            override fun onClickItemInventorySession(inventorySession: InventorySession) {
                this@InventoryActivity.navigateInventoryDetail(inventorySession)
            }
        }
    )}
    private val adapterRcvInventoryEnd by lazy { InventorySessionAdapter(onClickItemInventorySession =
    object : InventorySessionAdapter.OnClickItemInventorySession {
        override fun onClickItemInventorySession(inventorySession: InventorySession) {
            this@InventoryActivity.navigateInventoryDetail(inventorySession)
        }
    }
    )}

    private fun navigateInventoryDetail(inventorySession: InventorySession) {
        val intent = Intent(
            this,
            InventorySessionDetailActivity::class.java
        )
        intent.putExtra(BUNDLE_TAG, inventorySession)

        startActivity(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = InventoryPresenter(this)
        presenter.getInventorySessions()

        binding.recyclerViewInventory.adapter = adapterRcvInventoryInProgress
        binding.recyclerViewInventoryEnd.adapter = adapterRcvInventoryEnd
    }

    override fun loadData() {

    }

    override fun getInventorySessionsSuccess(listInventorySessionsEnd: List<InventorySession>, listInventorySessionsInProgress : List<InventorySession>) {
        adapterRcvInventoryInProgress.submitList(listInventorySessionsInProgress)
        adapterRcvInventoryEnd.submitList(listInventorySessionsEnd)
    }


    override fun getInventorySessionsError() {
    }

    override fun getAssetsFromLocalSuccess(listAssets: List<Asset>) {

    }

    override fun getAssetsFromLocalError() {

    }

}