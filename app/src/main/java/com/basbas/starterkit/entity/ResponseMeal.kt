package com.basbas.starterkit.entity

import com.google.gson.annotations.SerializedName

data class ResponseMeal(

	@field:SerializedName("meals")
	val meals: List<MealsItem?>? = null
)