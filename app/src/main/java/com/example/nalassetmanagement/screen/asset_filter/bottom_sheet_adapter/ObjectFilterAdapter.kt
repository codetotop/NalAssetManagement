package com.example.nalassetmanagement.screen.asset_filter.bottom_sheet_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.nalassetmanagement.R
import com.example.nalassetmanagement.databinding.ItemFitlerUserBinding
import com.example.nalassetmanagement.model.ObjectFilter

class ObjectFilterAdapter(
    private val list: List<ObjectFilter>,
    private val listener: OnClickListener
) :
    Adapter<ObjectFilterAdapter.UserFilterViewHolder>() {

    private var selectedPosition: Int = -1

    fun setSelectedPosition(position: Int) {
        this.selectedPosition = position
    }

    class UserFilterViewHolder(val binding: ItemFitlerUserBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFilterViewHolder {
        val binding =
            ItemFitlerUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserFilterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UserFilterViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                val context = binding.root.context
                binding.rbUser.text = name
                binding.rbUser.isChecked = position == selectedPosition
                val color = if (position == selectedPosition) {
                    context.getColor(R.color.base_color)
                } else {
                    context.getColor(R.color.black)
                }
                binding.rbUser.setTextColor(color)
                binding.rbUser.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedPosition = bindingAdapterPosition
                        listener.onItemClick(position)
                    }
                }
            }
        }
    }

    interface OnClickListener {
        fun onItemClick(position: Int)
    }
}
