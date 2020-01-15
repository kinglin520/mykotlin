package com.example.mykotlin.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import java.io.IOException
import java.io.Serializable

class SharedPreferencesUtil {
    companion object {
        private val threadLocal = ThreadLocal<SharedPreferences>()

        private val `object` = Any()

        private fun getSharedPreferences(context: Context, name: String): SharedPreferences? {
            var sharedPreferences: SharedPreferences? = threadLocal.get()
            if (sharedPreferences == null) {
                synchronized(`object`) {
                    if (sharedPreferences == null) {
                        sharedPreferences =
                            context.getSharedPreferences(name, Activity.MODE_PRIVATE)
                        threadLocal.set(sharedPreferences)
                    }
                }
            }
            return sharedPreferences
        }

        fun getEditor(context: Context, name: String): SharedPreferences.Editor {
            return getSharedPreferences(context, name)!!.edit()
        }

        fun remove(context: Context, name: String, key: String) {
            val editor = getEditor(context, name)
            editor.remove(key)
            editor.commit()
        }

        fun putSerializable(
            context: Context,
            name: String,
            key: String,
            serializable: Serializable
        ) {
            val editor = getEditor(context, name)
            try {
                editor.putString(key, SerializableUtil.serialize(serializable))
            } catch (e: IOException) {
                e.printStackTrace()
            }

            editor.commit()
        }

        fun getSerializable(context: Context, name: String, key: String): Serializable? {
            val sharedPreferences = getSharedPreferences(context, name)
            val serializableStr = sharedPreferences!!.getString(key, null)
            try {
                return serializableStr?.let { SerializableUtil.deSerialization(it) }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }

            return null
        }

        fun putBoolean(context: Context, name: String, key: String, value: Boolean) {
            val editor = getEditor(context, name)
            editor.putBoolean(key, value)
            editor.commit()
        }

        fun getBoolean(context: Context, name: String, key: String, defValue: Boolean): Boolean {
            val sharedPreferences = getSharedPreferences(context, name)
            return sharedPreferences!!.getBoolean(key, defValue)
        }

        fun putString(context: Context, key: String, value: String) {
            val editor = getEditor(context, "wenlin")
            editor.putString(key, value)
            editor.commit()
        }

        fun getString(context: Context, key: String, defValue: String): String? {
            val sharedPreferences = getSharedPreferences(context, "wenlin")
            return sharedPreferences!!.getString(key, defValue)
        }

        fun getInt(context: Context, name: String, key: String, defValue: Int): Int {
            val sharedPreferences = getSharedPreferences(context, name)
            return sharedPreferences!!.getInt(key, defValue)
        }
    }
}