package com.example.mylibrary.core.ui.component

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.widget.TextView
import com.example.mylibrary.R

/**
 * 支持描边的TextView
 */
@SuppressLint("AppCompatCustomView")
open class StrokeTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr, R.style.Widget_StrokeTextView) {

    var strokeWidth: Float = 0F
    var strokeColor: Int = 0

    var onDrawingFlag = false

    init {
        val newContext = getContext()
        val ta = newContext.obtainStyledAttributes(attrs, R.styleable.StrokeTextView)
        strokeWidth = ta.getDimension(R.styleable.StrokeTextView_uxsdk_textStrokeWidth, 0F)
        strokeColor = ta.getColor(R.styleable.StrokeTextView_uxsdk_textStrokeColor, 0)
        ta.recycle()


        try {
            val field = TextView::class.java.getDeclaredField("mShadowRadius")
            field.isAccessible = true
            field.setFloat(this, strokeWidth)
            field.isAccessible = false
        } catch (exception: Exception) {
            // 如果上面的方法失败了，使用下面这个备用方式
            setShadowLayer(strokeWidth, 0F, 0F, Color.TRANSPARENT)
        }
    }

    override fun invalidate() {
        if (!onDrawingFlag) {
            super.invalidate()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        if (isInEditMode) {
            super.onDraw(canvas)
            return
        }
        onDrawingFlag = true
        val lastColor = textColors
        setTextColor(strokeColor)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth
        super.onDraw(canvas)

        setTextColor(lastColor)
        paint.style = Paint.Style.FILL
        super.onDraw(canvas)
        onDrawingFlag = false
    }
}