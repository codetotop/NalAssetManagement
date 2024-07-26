package com.example.nalassetmanagement.screen.asset_filter

import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.nalassetmanagement.R
import com.example.nalassetmanagement.databinding.ActivityAssetFilterBinding
import com.example.nalassetmanagement.model.AssetFilter
import com.example.nalassetmanagement.view.custom.ActionBarView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class AssetFilterActivity : AppCompatActivity(), AssetFilterContract.View,
    ActionBarView.ActionBarViewListener, AssetFilterAdapter.OnClickListener {

    private lateinit var presenter: AssetFilterContract.Presenter
    private lateinit var binding: ActivityAssetFilterBinding
    private lateinit var adapter: AssetFilterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAssetFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        addListener()
    }

    private fun initView() {
        adapter = AssetFilterAdapter(
            listOf(
                AssetFilter("Nhân viên sử dụng", ""),
                AssetFilter("Danh mục", ""),
                AssetFilter("Vị trí", ""),
                AssetFilter("Công ty trực thuộc", ""),
                AssetFilter("Nhà sản xuất", ""),
            ),
            this
        )
        binding.rcvAssetFilter.adapter = adapter
    }

    private fun addListener() {
        binding.abvAssetFilter.setActionBarViewListener(this)
    }

    override fun loadData() {

    }

    override fun onClickLeftButton() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun onClickRightButton() {

    }

    override fun onItemClick(position: Int) {
        val dialog: BottomSheetDialog
        when (position) {
            0 -> {

                // retrieve display dimensions
                val displayRectangle = Rect()
                val window: Window = this.window
                window.decorView.getWindowVisibleDisplayFrame(displayRectangle)

                //val minimumHeight = (displayRectangle.height() * 0.48f).toInt()

                dialog = BottomSheetDialog(this)
                val parentView: View = layoutInflater.inflate(R.layout.bottom_sheet_user, null)
                dialog.setContentView(parentView)
                /*val bottomSheetBehavior: BottomSheetBehavior<*> =
                    BottomSheetBehavior.from<View>(parentView.parent as View)
                bottomSheetBehavior.peekHeight = minimumHeight*/

                val designBottomSheet = dialog.findViewById<ConstraintLayout>(R.id.tvDesignBottomSheet)

                BottomSheetBehavior.from(designBottomSheet!!).peekHeight = 100
                /*val listView: BottomSheetListView? =
                    dialog.findViewById<View>(R.id.listViewBtmSheet) as BottomSheetListView?
                val adapter: OffersAdapter = OffersAdapter(this@MainActivity, listViewData)
                listView.setAdapter(adapter)*/
                dialog.show()
            }

            2 -> {

            }
        }
    }
}
