package com.androiddd.utils

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView

/**
 * Created by liuzhenhua on 2017/11/13.
 */
class LogcatView : FrameLayout {
    private val textView = TextView(context)
    private val button = Button(context)

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        textView.scrollBarStyle = SCROLLBARS_INSIDE_OVERLAY
        textView.movementMethod = ScrollingMovementMethod.getInstance()
        button.text = "清除Log"
        button.gravity = Gravity.CENTER
        button.setOnClickListener { clear() }
        val buttonLayoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val textLayoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        buttonLayoutParams.gravity = Gravity.BOTTOM
        textLayoutParams.bottomMargin = dp2px(context, 50.toFloat()).toInt()
        addView(textView, textLayoutParams)
        addView(button, buttonLayoutParams)
    }

    fun print(log: String) {
        textView.text = log + "\n" + textView.text.toString()
    }

    fun clear() {
        textView.text = ""
    }

}