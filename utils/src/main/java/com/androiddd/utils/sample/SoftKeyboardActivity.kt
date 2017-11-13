package com.androiddd.utils.sample

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.androiddd.utils.R
import com.androiddd.utils.SoftKeyboardDetector
import kotlinx.android.synthetic.main.activity_soft_keyboard.*

class SoftKeyboardActivity : AppCompatActivity() {
    private val TAG = "SoftKeyboardDetector"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soft_keyboard)
        SoftKeyboardDetector.registerKeyboardEventListener(window, object : SoftKeyboardDetector.OnKeyboardEventListener {
            override fun onKeyboardEvent(isShown: Boolean, softKeyboardHeight: Int, visibleFrame: Rect) {
                logcat_view.print("isShown:$isShown, softKeyboardHeight:$softKeyboardHeight, visibleFrame:$visibleFrame")
                Log.e(TAG, "isShown:$isShown, softKeyboardHeight:$softKeyboardHeight, visibleFrame:$visibleFrame")
            }
        })
    }
}
