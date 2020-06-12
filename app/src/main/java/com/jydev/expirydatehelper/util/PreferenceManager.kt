package com.jydev.expirydatehelper.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager {
    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("EnpiryDateHelper", Context.MODE_PRIVATE)
    }

    /**
     * int 값 저장
     * @param context
     * @param key
     * @param value
     */
    fun setInt(context: Context?, key: String?, value: Int) {
        val prefs = getPreferences(context!!)
        val editor = prefs.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    /**
     * int 값 로드
     * @param context
     * @param key
     * @return
     */
    fun getInt(context: Context?, key: String?): Int {
        val prefs = getPreferences(context!!)
        return prefs.getInt(key, 0)
    }
}