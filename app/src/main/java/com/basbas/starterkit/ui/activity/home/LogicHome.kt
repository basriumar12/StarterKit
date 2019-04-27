package com.basbas.starterkit.ui.activity.home

import android.content.Context
import android.graphics.BitmapFactory
import android.os.AsyncTask
import com.basbas.starterkit.db.DatabaseHelper
import com.basbas.starterkit.db.database
import com.basbas.starterkit.entity.ResponseMeal
import com.basbas.starterkit.network.ApiConfig
import com.basbas.starterkit.utils.hasNetwork
import retrofit2.Call
import retrofit2.Response
import java.lang.IndexOutOfBoundsException
import java.net.URL

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


                            /// insert data di background
                            class task : AsyncTask<String?, Int?, Void?>() {
                                override fun doInBackground(vararg params: String?): Void? {


                                    for (i in 0 until response.body()?.meals?.size!!){
                                        try {

                                            val url = response.body()!!.meals?.get(i)?.strMealThumb
                                            val newUrl = url?.replace(" ", "%20")
                                            val stream = URL(newUrl).openStream()
                                            val bitmap = BitmapFactory.decodeStream(stream)

                                            DatabaseHelper(context).mealAdd(
                                                response.body()!!.meals?.get(i)?.strMeal,
                                                response.body()!!.meals?.get(i)?.strMeal,
                                                bitmap

                                            )

                                        } catch (e : IndexOutOfBoundsException){

                                        }
                                    }

                                    return null
                                }

                                override fun onPreExecute() {
                                    super.onPreExecute()
                                 //   view.showLoading()
                                }

                                override fun onPostExecute(result: Void?) {
                                    super.onPostExecute(result)
                                   //  view.hideLoading()

                                }


                            }
                            //task di execute
                            task().execute()



                        }
                    }

                }

            })




        } else{

            //get data offline
            val dataItem = DatabaseHelper(context).loadDataOffline()
            view.loadDataOffline(dataItem)

        }



    }
}