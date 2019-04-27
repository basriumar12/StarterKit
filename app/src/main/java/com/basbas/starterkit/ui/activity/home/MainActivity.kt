package com.basbas.starterkit.ui.activity.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.basbas.starterkit.R
import com.basbas.starterkit.base.Baseactivity
import com.basbas.starterkit.entity.MealsItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Baseactivity(), InterfaceHome.View {
    override fun loadData(data: List<MealsItem?>?) {

        if (data!= null) {
            val adapterMeal = AdapterMeal(this, data)
            adapterMeal.notifyDataSetChanged()
            rv_meal.adapter = adapterMeal
        }
    }

    override fun showLoading() {
        progress_circular.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_circular.visibility = View.GONE
    }

    private var presenter: InterfaceHome.Presenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv_meal.layoutManager = LinearLayoutManager(this)

        presenter = LogicHome(this, this)
        presenter?.loadDataMeals("Canadian")


    }
}
