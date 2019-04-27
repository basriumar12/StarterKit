package com.basbas.starterkit.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import com.basbas.starterkit.entity.MealsItem
import com.basbas.starterkit.entity.MealsItemOffline
import com.basbas.starterkit.utils.getBitmapFromByte
import com.basbas.starterkit.utils.getPictureByteOfArray
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper

class DatabaseHelper(contex: Context) : ManagedSQLiteOpenHelper(contex, "meal.db", null, 1)  {

    companion object {
        private var instance: DatabaseHelper? = null
        const val TABLE_MEAL = "meal_table"

        val CREATE_MEAL_TABLE = "CREATE TABLE $TABLE_MEAL (" +
                "meal_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "meal_title VARCHAR(100)," +
                "meal_name_photo TEXT not null," +
                "meal_photo blob not null" +
                ")"

        @Synchronized
        fun getInstance(contex: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(contex)
            }
            return instance as DatabaseHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_MEAL_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun mealAdd( title: String?, namePhoto: String?, photo: Bitmap?) {
        val db = writableDatabase
        val values = ContentValues()

        values.put("meal_title", title)
        values.put("meal_name_photo", namePhoto)
        values.put("meal_photo", getPictureByteOfArray(photo!!))

        try {
            db.insert(TABLE_MEAL, null, values)
        } catch (e: Exception) {

        }
        db.close()

    }

    fun loadDataOffline(): ArrayList<MealsItemOffline> {
        val itemCategory = ArrayList<MealsItemOffline>()
        val db = writableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_MEAL", null)
        if (cursor.moveToFirst()) {
            for (i in 0 until cursor.count) {
                itemCategory.add(MealsItemOffline(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    getBitmapFromByte(cursor.getBlob(cursor.getColumnIndex("meal_photo")))
                ))
                cursor.moveToNext()
            }
        }
        db.close()
        return itemCategory
    }
}

val Context.database: DatabaseHelper
    get() = DatabaseHelper.getInstance(this)