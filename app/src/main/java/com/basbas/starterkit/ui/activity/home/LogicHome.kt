package com.basbas.starterkit.ui.activity.home

import android.content.Context
import com.basbas.starterkit.entity.ResponseMeal
import com.basbas.starterkit.network.ApiConfig
import com.basbas.starterkit.utils.hasNetwork
import retrofit2.Call
import retrofit2.Response

class LogicHome (private val view: InterfaceHome.View, private val context: Context) : InterfaceHome.Presenter  {
    override fun loadDataMeals(string: String) {
        if (hasNetwork(context)!! == true){
                //get data online

            view.showLoading()
            ApiConfig().instance().loadDataMeal(string).enqueue(object  : retrofit2.Callback<ResponseMeal>{
                override fun onFailure(call: Call<ResponseMeal>, t: Throwable) {
                    // data failure
                    view.hideLoading()
                }

                override fun onResponse(call: Call<ResponseMeal>, response: Response<ResponseMeal>) {
                    if (response.isSuccessful){
                        if (response.body()?.meals?.size == 0){
                            view.showInfoMessage("Data Kosong")
                            view.hideLoading()
                        } else{
                            view.hideLoading()
                            view.loadData(response.body()?.meals)
                        }
                    }

                }

            })




        } else{

            //get data offline
        }



    }
}