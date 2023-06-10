package com.qnecesitas.elreteninventario.auxiliary

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ImageTools {

companion object {
    val ANCHO_DE_FOTO_A_SUBIR = 900
    val ALTO_DE_FOTO_A_SUBIR = 900

    fun convertImageString(bitmap: Bitmap?): String? {
        val BitRec =
            Bitmap.createScaledBitmap(bitmap!!, ANCHO_DE_FOTO_A_SUBIR, ALTO_DE_FOTO_A_SUBIR, true)
        val byteArrayOutputStream = ByteArrayOutputStream()
        BitRec.compress(Bitmap.CompressFormat.JPEG, 28, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun getHoraActual(SumaDeFormatos: String): String {
        return SimpleDateFormat(
            SumaDeFormatos,
            Locale.getDefault()
        ).format(Date())
    }

    @Throws(IOException::class)
    fun createTempImageFile(context: Context, nombre: String): File {
        val storageDir = context.externalCacheDir
        return File.createTempFile(nombre, ".png", storageDir)
    }

    @Throws(IOException::class)
    fun obtenerTempImageFile(context: Context, nombre: String): File {
        val storageDir = context.externalCacheDir
        return File(storageDir, nombre)
    }
}

}