package com.example.nalassetmanagement.screen.main

import com.example.nalassetmanagement.model.Data

interface MainContract {

    interface Presenter {
        fun login(displayName: String, password: String)
    }

    interface View {
        fun loginSuccess(data: Data)
        fun loginFailure()
    }
}