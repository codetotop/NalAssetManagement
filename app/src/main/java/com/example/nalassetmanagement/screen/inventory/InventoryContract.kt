package com.example.nalassetmanagement.screen.inventory

interface InventoryContract {

    interface Presenter {
        fun fetchData()
        fun executeInventory()
    }

    interface View {
        fun loadData()
    }
}