package com.staynight.moviedb.data.storage

import android.content.SharedPreferences
import javax.inject.Inject

class SessionManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val SESSION_ID = "SESSION_ID"
    }

    private val editor = sharedPreferences.edit()

    fun saveSessionID(sessionID: String) {
        editor.putString(SESSION_ID, sessionID).apply()
    }

    fun getSessionID(): String {
        return sharedPreferences.getString(SESSION_ID, "") ?: ""
    }
}