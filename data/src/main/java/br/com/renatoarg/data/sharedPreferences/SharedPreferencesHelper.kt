package br.com.renatoarg.data.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import br.com.renatoarg.data.R

class SharedPreferencesHelper(context: Context) {

    private val sharedPref: SharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)

    fun write(key: String, value: Any) {
        when (value) {
            is String -> writeString(key, value)
            is Int -> writeInt(key, value)
            is Boolean -> writeBoolean(key, value)
            is Long -> writeLong(key, value)
            is Float -> writeFloat(key, value)
        }
    }

    private fun writeString(key: String, value: String) {
        with(sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun readString(key: String): String? {
        return sharedPref.getString(key, null)
    }

    private fun writeInt(key: String, value: Int) {
        with(sharedPref.edit()) {
            putInt(key, value)
            apply()
        }
    }

    fun readInt(key: String, defaultValue: Int = -999): Int {
        return sharedPref.getInt(key, defaultValue)
    }

    private fun writeBoolean(key: String, value: Boolean) {
        with(sharedPref.edit()) {
            putBoolean(key, value)
            apply()
        }
    }

    fun readBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPref.getBoolean(key, defaultValue)
    }

    private fun writeFloat(key: String, value: Float) {
        with(sharedPref.edit()) {
            putFloat(key, value)
            apply()
        }
    }

    fun readFloat(key: String, defaultValue: Float = -999f): Float {
        return sharedPref.getFloat(key, defaultValue)
    }

    private fun writeLong(key: String, value: Long) {
        with(sharedPref.edit()) {
            putLong(key, value)
            apply()
        }
    }

    fun readLong(key: String, defaultValue: Long = -999L): Long {
        return sharedPref.getLong(key, defaultValue)
    }
}