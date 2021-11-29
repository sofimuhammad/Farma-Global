package com.example.farmaglobal

import android.content.Context
import android.content.SharedPreferences

class Preferences (context: Context)
{
    private val pref_key_token = "key_token"

    private val preferences: SharedPreferences = context.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)

    var token: String
        get() = preferences.getString(pref_key_token, "") ?: ""
        set(value) = preferences.edit().putString(pref_key_token, value).apply()

    var isLogin: Boolean
        get() = preferences.getBoolean("key_islogin", false)
        set(value) = preferences.edit().putBoolean("key_islogin", value).apply()
}