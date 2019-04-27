package com.basbas.starterkit.entity

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName
import java.sql.Blob

data class MealsItemOffline(
	var id:Int?,
	var title: String?,
	var namePhoto:String?,
	var photo:Bitmap
)