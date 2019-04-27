package com.basbas.starterkit.entity

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName
import java.sql.Blob

data class MealsItem(

	@field:SerializedName("strMealThumb")
	val strMealThumb: String? = null,

	@field:SerializedName("idMeal")
	val idMeal: String? = null,

	@field:SerializedName("strMeal")
	val strMeal: String? = null,

	val image: Bitmap? = null
)