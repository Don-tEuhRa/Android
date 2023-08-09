package com.dongminpark.reborn

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class App: Application() {
    companion object{
        lateinit var instance: App
            private set
        lateinit var token_prefs : TokenSharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}

class TokenSharedPreferences(context: Context) {
    private val prefsFilename = "token_prefs"
    private val key_accessToken = "accessToken"
    private val key_accessTokenExpireDate = "accessTokenExpireDate"
    private val key_grantType = "grantType"
    private val key_refreshToken = "refreshToken"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFilename,0)

    var accessToken: String?
        get() = prefs.getString(key_accessToken,"")
        set(value) = prefs.edit().putString(key_accessToken,value).apply()
    var accessTokenExpireDate:Int
        get() = prefs.getInt(key_accessTokenExpireDate,0)
        set(value) = prefs.edit().putInt(key_accessTokenExpireDate,value).apply()
    var grantType:String?
        get() = prefs.getString(key_grantType,"")
        set(value) = prefs.edit().putString(key_grantType,value).apply()
    var refreshToken: String?
        get() = prefs.getString(key_refreshToken,"")
        set(value) = prefs.edit().putString(key_refreshToken,value).apply()
}