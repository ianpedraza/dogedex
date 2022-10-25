package com.ianpedraza.dogedex.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.ianpedraza.dogedex.domain.models.User

class SharedPreferencesUtils(context: Context) {

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun saveUser(user: User) {
        val gson = Gson()
        val userJson = gson.toJson(user)

        sharedPreferences.edit().apply {
            putString(USER_KEY, userJson)
        }.apply()
    }

    fun removeUser() {
        sharedPreferences.edit().apply {
            putString(USER_KEY, null)
        }.apply()
    }

    fun getUser(): User? {
        val gson = Gson()
        val userJson = sharedPreferences.getString(USER_KEY, null) ?: return null
        return gson.fromJson(userJson, User::class.java)
    }

    companion object {
        private const val AUTH_PREFERENCES = "auth-preferences"

        private const val USER_KEY = "user"
    }
}
