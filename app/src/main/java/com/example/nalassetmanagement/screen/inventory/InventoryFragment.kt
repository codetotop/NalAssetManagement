package com.example.nalassetmanagement.screen.inventory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nalassetmanagement.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class InventoryFragment : Fragment(), InventoryContract.View {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var inventoryPresenter: InventoryContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inventoryPresenter = InventoryPresenter(this)
        return inflater.inflate(R.layout.fragment_inventory, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = InventoryFragment()

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InventoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun loadData() {

    }
}