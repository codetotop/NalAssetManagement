package com.example.nalassetmanagement.screen.asset_filter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.nalassetmanagement.R
import com.example.nalassetmanagement.common.selectedPosition
import com.example.nalassetmanagement.common.singleSelected
import com.example.nalassetmanagement.databinding.ActivityAssetFilterBinding
import com.example.nalassetmanagement.model.local.AssetFilter
import com.example.nalassetmanagement.model.server.FilterList
import com.example.nalassetmanagement.model.server.KeyValue
import com.example.nalassetmanagement.model.server.User
import com.example.nalassetmanagement.model.local.ObjectFilter
import com.example.nalassetmanagement.screen.asset_filter.bottom_sheet_adapter.ObjectFilterAdapter
import com.example.nalassetmanagement.view.custom.ActionBarView
import com.google.android.material.bottomsheet.BottomSheetDialog


class AssetFilterActivity : AppCompatActivity(), AssetFilterContract.View,
    ActionBarView.ActionBarViewListener, AssetFilterAdapter.OnClickListener {

    private lateinit var presenter: AssetFilterContract.Presenter
    private lateinit var binding: ActivityAssetFilterBinding
    private lateinit var adapter: AssetFilterAdapter
    private lateinit var filterUserList: ArrayList<ObjectFilter>
    private lateinit var filterCategoryList: ArrayList<ObjectFilter>
    private lateinit var filterAddressList: ArrayList<ObjectFilter>
    private lateinit var filterProducerList: ArrayList<ObjectFilter>
    private var filterUserAdapter: ObjectFilterAdapter? = null
    private lateinit var filterCategoryAdapter: ObjectFilterAdapter
    private lateinit var filterAddressAdapter: ObjectFilterAdapter
    private lateinit var filterProducerAdapter: ObjectFilterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAssetFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = AssetFilterPresenter(this)
        presenter.fetchFilterList()
        initView()
        addListener()
    }

    private fun initView() {
        binding.loading.visibility = View.VISIBLE
        adapter = AssetFilterAdapter(
            listOf(
                AssetFilter(1, "Nhân viên sử dụng: ", ""),
                AssetFilter(2, "Danh mục: ", ""),
                AssetFilter(3, "Vị trí: ", ""),
                AssetFilter(4, "Nhà sản xuất: ", ""),
            ),
            this
        )
        binding.rcvAssetFilter.adapter = adapter
    }

    private fun addListener() {
        binding.abvAssetFilter.setActionBarViewListener(this)
    }

    override fun onClickLeftButton() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun onClickRightButton() {

    }

    @SuppressLint("MissingInflatedId")
    override fun onItemClick(position: Int) {
        when (position) {
            0 -> {
                showFilterUserBottomSheet()
            }

            1 -> {
                showFilterCategoryBottomSheet()
            }

            2 -> {
                showFilterAddressBottomSheet()
            }

            3 -> {
                showFilterProducerBottomSheet()
            }
        }
    }

    private fun showFilterUserBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_filter_common, null)
        val rcvUser = view.findViewById<RecyclerView>(R.id.rcvFilterListCommon)
        val selectedPosition = filterUserList.selectedPosition()
        filterUserAdapter =
            ObjectFilterAdapter(filterUserList, object : ObjectFilterAdapter.OnClickListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemClick(position: Int) {
                    rcvUser.post {
                        filterUserAdapter?.notifyDataSetChanged()
                        filterUserList.singleSelected(position)
                    }
                    //Case selected: None
                    if (position == 0) {
                        adapter.updateItem(0, "")
                    } else {
                        adapter.updateItem(0, filterUserList[position].name)
                    }
                }
            })

        filterUserAdapter!!.setSelectedPosition(selectedPosition)
        rcvUser.adapter = filterUserAdapter
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun showFilterCategoryBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_filter_common, null)
        val rcvCategory = view.findViewById<RecyclerView>(R.id.rcvFilterListCommon)
        val selectedPosition = filterCategoryList.selectedPosition()
        filterCategoryAdapter =
            ObjectFilterAdapter(filterCategoryList, object : ObjectFilterAdapter.OnClickListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemClick(position: Int) {
                    rcvCategory.post {
                        filterCategoryAdapter.notifyDataSetChanged()
                        filterCategoryList.singleSelected(position)
                    }
                    //Case selected: None
                    if (position == 0) {
                        adapter.updateItem(1, "")
                    } else {
                        adapter.updateItem(1, filterCategoryList[position].name)
                    }
                }
            })

        filterCategoryAdapter.setSelectedPosition(selectedPosition)
        rcvCategory.adapter = filterCategoryAdapter
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun showFilterAddressBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_filter_common, null)
        val rcvAddress = view.findViewById<RecyclerView>(R.id.rcvFilterListCommon)
        val selectionPosition = filterAddressList.selectedPosition()
        filterAddressAdapter =
            ObjectFilterAdapter(filterAddressList, object : ObjectFilterAdapter.OnClickListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemClick(position: Int) {
                    rcvAddress.post {
                        filterAddressAdapter.notifyDataSetChanged()
                        filterAddressList.singleSelected(position)
                    }
                    //Case selected: None
                    if (position == 0) {
                        adapter.updateItem(2, "")
                    } else {
                        adapter.updateItem(2, filterAddressList[position].name)
                    }
                }
            })

        filterAddressAdapter.setSelectedPosition(selectionPosition)
        rcvAddress.adapter = filterAddressAdapter
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun showFilterProducerBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_filter_common, null)
        val rcvProducer = view.findViewById<RecyclerView>(R.id.rcvFilterListCommon)
        val selectionPosition = filterProducerList.selectedPosition()
        filterProducerAdapter =
            ObjectFilterAdapter(filterProducerList, object : ObjectFilterAdapter.OnClickListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemClick(position: Int) {
                    rcvProducer.post {
                        filterProducerAdapter.notifyDataSetChanged()
                        filterProducerList.singleSelected(position)
                    }
                    //Case selected: None
                    if (position == 0) {
                        adapter.updateItem(3, "")
                    } else {
                        adapter.updateItem(3, filterProducerList[position].name)
                    }
                }
            })

        filterProducerAdapter.setSelectedPosition(selectionPosition)
        rcvProducer.adapter = filterProducerAdapter
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    override fun fetchFilterListSuccess(filterList: FilterList?) {
        binding.loading.visibility = View.GONE
        Toast.makeText(this, "Lấy thông tin thành công!", Toast.LENGTH_SHORT).show()
        filterList?.let {
            loadUser(it.user)
            loadCategory(it.category)
            loadAddress(it.address)
            loadProducer(it.producer)
        }
    }

    private fun loadUser(users: List<User>) {
        filterUserList = arrayListOf()
        filterUserList.add(ObjectFilter(0, "None", false))
        for (index in users.indices) {
            users[index].userName?.let {
                filterUserList.add(ObjectFilter(users[index].id, users[index].userName, false))
            }

            if (index > 2) return
        }

        if (filterUserList.size == 1) {
            filterUserList.addAll(
                arrayListOf(
                    ObjectFilter(1, "DungNB", false),
                    ObjectFilter(2, "CuongNM", false),
                    ObjectFilter(3, "VietVV", false),
                )
            )
        }
    }

    private fun loadCategory(categories: List<KeyValue>) {

        filterCategoryList = arrayListOf()
        filterCategoryList.add(ObjectFilter(0, "None", false))
        for (index in categories.indices) {
            categories[index].name?.let {
                filterCategoryList.add(
                    ObjectFilter(
                        categories[index].id,
                        categories[index].name,
                        false
                    )
                )
            }
            if (index > 2) return
        }

        if (filterCategoryList.size == 1) {
            filterCategoryList.addAll(
                arrayListOf(
                    ObjectFilter(1, "Máy tính", false),
                    ObjectFilter(2, "Điện thoại", false),
                    ObjectFilter(3, "Adapter", false),
                )
            )
        }
    }

    private fun loadAddress(categories: List<KeyValue>) {

        filterAddressList = arrayListOf()
        filterAddressList.add(ObjectFilter(0, "None", false))
        for (index in categories.indices) {
            categories[index].name?.let {
                filterAddressList.add(
                    ObjectFilter(
                        categories[index].id,
                        categories[index].name,
                        false
                    )
                )
            }
            if (index > 2) return
        }

        if (filterAddressList.size == 1) {
            filterAddressList.addAll(
                arrayListOf(
                    ObjectFilter(1, "NAl", false),
                    ObjectFilter(2, "Kosmo", false),
                    ObjectFilter(3, "LA", false),
                )
            )
        }
    }

    private fun loadProducer(producers: List<KeyValue>) {

        filterProducerList = arrayListOf()
        filterProducerList.add(ObjectFilter(0, "None", false))
        for (index in producers.indices) {
            producers[index].name?.let {
                filterProducerList.add(
                    ObjectFilter(
                        producers[index].id,
                        producers[index].name,
                        false
                    )
                )
            }
            if (index > 2) return
        }

        if (filterProducerList.size == 1) {
            filterProducerList.addAll(
                arrayListOf(
                    ObjectFilter(1, "Apple Inc", false),
                    ObjectFilter(2, "Microsoft Inc", false),
                    ObjectFilter(3, "Samsung Electronic", false),
                )
            )
        }
    }


    override fun fetchFilterListFailure() {
        binding.loading.visibility = View.GONE
        Toast.makeText(this, getString(R.string.msg_call_api_failure), Toast.LENGTH_SHORT).show()
    }
}
