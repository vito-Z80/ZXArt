package com.example.zx_art.app.file

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import okio.FileSystem
import java.io.FileDescriptor
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


object ZxFile {

    const val JSON = "json"


    fun readFold(context: Context): String? {
        var fos: FileInputStream? = null
        return try {
            fos = context.openFileInput("")
            fos.reader().toString()
        } catch (ex: IOException) {
            ex.message
        } finally {
            try {
                fos?.close()
            } catch (ex: IOException) {
                ex.message
            }
        }
    }

    fun save(
        context: Context,
        fileName: String,
        textContent: String,
        extension: String = JSON,
        mode: Int = ComponentActivity.MODE_PRIVATE,
    ): String? {
        var fos: FileOutputStream? = null
        return try {
//            fos = FileOutputStream("$fileName.$extension", true)
            fos = context.openFileOutput("$fileName.$extension", mode)
            fos.write(textContent.toByteArray())
            "Файл сохранен"
        } catch (ex: IOException) {
            ex.message
        } finally {
            try {
                fos?.close()
            } catch (ex: IOException) {
                ex.message
            }
        }
    }


    // открытие файла
    fun open(context: Context, fileName: String, extension: String = JSON): String? {
        var fin: FileInputStream? = null
        return try {
            fin = context.openFileInput("$fileName.$extension")
            val bytes = ByteArray(fin.available())
            fin.read(bytes)
            String(bytes)
        } catch (ex: IOException) {
            println(ex.message)
            null
            //            Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
        } finally {
            try {
                fin?.close()
            } catch (ex: IOException) {
                println(ex.message)
                //                Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}