package com.example.nalassetmanagement.screen.inventory.inventory_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nalassetmanagement.databinding.ActivityInventoryBinding
import com.example.nalassetmanagement.databinding.ActivityInventoryDetailsBinding
import com.example.nalassetmanagement.model.Asset
import com.example.nalassetmanagement.model.inventory.InventorySession
import com.example.nalassetmanagement.screen.inventory.InventoryActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class InventorySessionDetailActivity : AppCompatActivity(), InventoryDetailContract.View {

    private lateinit var presenter: InventoryDetailContract.Presenter

    private val inventoryBottomSheet by lazy { InventoryBottomSheet() }

    private val binding by lazy { ActivityInventoryDetailsBinding.inflate(layoutInflater) }
    private val adapterRcvUnChecked by lazy {
        InventorySessionDetailsAdapter(object : InventorySessionDetailsAdapter.OnClickItemAsset {
            override fun onClickItemAsset(item: Asset) {

            }
        })
    }
    private val adapterRcvChecked by lazy {
        InventorySessionDetailsAdapter(object : InventorySessionDetailsAdapter.OnClickItemAsset {
            override fun onClickItemAsset(item: Asset) {

            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = InventoryDetailPresenter(this)

        binding.recyclerViewUnchecked.adapter = adapterRcvUnChecked
        binding.recyclerViewChecked.adapter = adapterRcvChecked

        presenter.getAssetsFromLocal()
        getDataIntent()
    }
    private fun getDataIntent() {
        val inventorySession = intent?.getSerializableExtra(InventoryActivity.BUNDLE_TAG) as? InventorySession
        if (inventorySession != null) {
            presenter.getInventorySessionDetails(inventorySession)
        }
    }

    override fun loadData() {

    }
    override fun getAssetsFromLocalSuccess(
        listAssetsChecked: MutableList<Asset>,
        listAssetsNotChecked: MutableList<Asset>
    ) {
        adapterRcvChecked.submitList(listAssetsChecked)
        adapterRcvUnChecked.submitList(listAssetsNotChecked)
    }


    override fun getAssetsFromLocalError() {

    }

}