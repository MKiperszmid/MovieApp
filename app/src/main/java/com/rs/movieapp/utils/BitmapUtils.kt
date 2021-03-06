package com.rs.movieapp.utils

import android.graphics.*

object BitmapUtils {
    fun toGrayscale(bitmap: Bitmap): Bitmap {
        val height: Int = bitmap.height
        val width: Int = bitmap.width
        val bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmpGrayscale)
        val paint = Paint()
        val cm = ColorMatrix()
        cm.setSaturation(0f)
        val f = ColorMatrixColorFilter(cm)
        paint.colorFilter = f
        c.drawBitmap(bitmap, 0f, 0f, paint)
        return bmpGrayscale
    }
}