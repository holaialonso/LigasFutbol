package com.example.ligasfutbol.ui.helpers

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class GrayscaleTransformation(context: Context) : BitmapTransformation() {

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        // Creamos un bitmap en escala de grises con las mismas dimensiones que la imagen original
        val grayscaleBitmap = Bitmap.createBitmap(toTransform.width, toTransform.height, Bitmap.Config.ARGB_8888)

        // Iteramos sobre cada píxel de la imagen original
        for (x in 0 until toTransform.width) {
            for (y in 0 until toTransform.height) {
                // Obtenemos el color del píxel original
                val color = toTransform.getPixel(x, y)

                // Calculamos el valor promedio de los componentes RGB
                val grayValue = (color shr 16 and 0xFF + color shr 8 and 0xFF + color and 0xFF) / 3

                // Creamos un nuevo color en escala de grises con el mismo valor para los componentes RGB
                val grayColor = grayValue shl 16 or (grayValue shl 8) or grayValue

                // Establecemos el color en escala de grises en el bitmap de salida
                grayscaleBitmap.setPixel(x, y, grayColor)
            }
        }

        return grayscaleBitmap
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        // No se necesita implementación
    }
}