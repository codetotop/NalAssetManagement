package com.example.nalassetmanagement.screen.main

import com.example.nalassetmanagement.api.ApiRepository
import com.example.nalassetmanagement.model.LoginData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(private val mainView: MainContract.View) : MainContract.Presenter {
    private val apiRepository: ApiRepository = ApiRepository()

    override fun login(displayName: String, password: String) {
        apiRepository.fetchLoginData(displayName, password)?.enqueue(object : Callback<LoginData> {
            override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
                response.body()?.let { body ->
                    if (response.isSuccessful && body.status == 200) {
                        mainView.loginSuccess(body.data)
                    } else {
                        mainView.loginFailure()
                    }
                }
            }

            override fun onFailure(call: Call<LoginData>, t: Throwable) {
                mainView.loginFailure()
            }
        })
    }
}