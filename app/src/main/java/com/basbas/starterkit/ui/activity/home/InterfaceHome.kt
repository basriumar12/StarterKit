package com.basbas.starterkit.ui.activity.home

import com.basbas.starterkit.base.Baseview
import com.basbas.starterkit.entity.MealsItem
import com.basbas.starterkit.entity.MealsItemOffline

interface InterfaceHome {

    interface Presenter{
        fun loadDataMeals(string : String)
    }

    interface View : Baseview {
        fun loadData( data : List<MealsItem?>?)
        fun loadDataOffline( data : List<MealsItemOffline?>?)
        fun showLoading()
        fun hideLoading()
    }

}