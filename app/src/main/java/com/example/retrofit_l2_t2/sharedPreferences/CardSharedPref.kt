package com.example.retrofit_l2_t2.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class CardSharedPref(context: Context) {
    private val sharedPref: SharedPreferences = context.getSharedPreferences("CardPref",Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveFirstJoin() {
        val editor = sharedPref.edit()
        editor.putBoolean("va",false)
        editor.apply()
    }
    fun getJoins(): Boolean{
        val v = sharedPref.getBoolean("va",true)
        return v
    }
}