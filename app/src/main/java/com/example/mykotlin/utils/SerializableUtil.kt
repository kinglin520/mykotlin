package com.example.mykotlin.utils

import android.text.TextUtils
import java.io.*

class SerializableUtil {
    companion object {
        @Throws(IOException::class)
        fun serialize(serializable: Serializable?): String? {
            if (serializable == null)
                return null
            val byteArrayOutputStream = ByteArrayOutputStream()
            val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
            objectOutputStream.writeObject(serializable)
            var serStr = byteArrayOutputStream.toString("ISO-8859-1")
            serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
            objectOutputStream.close()
            byteArrayOutputStream.close()
            return serStr
        }

        @Throws(IOException::class, ClassNotFoundException::class)
        fun deSerialization(serialStr: String): Serializable? {
            if (TextUtils.isEmpty(serialStr))
                return null
            val redStr = java.net.URLDecoder.decode(serialStr, "UTF-8")
            val byteArrayInputStream =
                ByteArrayInputStream(redStr.toByteArray(charset("ISO-8859-1")))
            val objectInputStream = ObjectInputStream(byteArrayInputStream)
            val serializable = objectInputStream.readObject() as Serializable
            objectInputStream.close()
            byteArrayInputStream.close()
            return serializable
        }
    }
}