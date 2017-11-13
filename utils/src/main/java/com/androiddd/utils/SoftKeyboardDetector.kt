package com.androiddd.utils

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowManager
import java.lang.ref.WeakReference

/**
 * Created by liuzhenhua on 2017/11/13.
 */
object SoftKeyboardDetector {
    private val TAG = "SoftKeyboardDetector"
    private val KEYBOARD_VISIBLE_THRESHOLD_DIP = 100

    fun registerKeyboardEventListener(window: Window, listener: OnKeyboardEventListener): DefaultUnRegister? {
        val attributes = window.attributes
        val softInputMode = attributes.softInputMode
        if (softInputMode == WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING || softInputMode == WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN) {
            Log.e(TAG, "SoftKeyboard detector can't work with softInputMode is SOFT_INPUT_ADJUST_NOTHING or SOFT_INPUT_ADJUST_PAN")
            return null
        }
        val contentView = getContentView(window) ?: return null
        val layoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
            private val visibleFrame = Rect()
            private val threshold = dp2px(contentView.context, KEYBOARD_VISIBLE_THRESHOLD_DIP.toFloat())
            private var wasKeyboardOpened = false
            private var visibleHeight = -1

            override fun onGlobalLayout() {
                contentView.getWindowVisibleDisplayFrame(visibleFrame)
                val heightDiff = contentView.height - visibleFrame.height()
                val isOpen = heightDiff > threshold
                if (visibleFrame.height() == this.visibleHeight) {
                    return
                }
                this.visibleHeight = visibleFrame.height()

                wasKeyboardOpened = isOpen
                listener.onKeyboardEvent(isOpen, heightDiff, visibleFrame)
            }
        }
        contentView.viewTreeObserver?.addOnGlobalLayoutListener(layoutListener)
        return DefaultUnRegister(window, layoutListener)
    }

    fun getContentView(window: Window?): View? {
        return window?.findViewById(android.R.id.content)
    }

    interface OnKeyboardEventListener {
        fun onKeyboardEvent(isShown: Boolean, softKeyboardHeight: Int, visibleFrame: Rect)
    }

    class DefaultUnRegister(window: Window, listener: ViewTreeObserver.OnGlobalLayoutListener) {

        private val activityRef: WeakReference<Window> = WeakReference(window)
        private val listenerRef: WeakReference<ViewTreeObserver.OnGlobalLayoutListener> = WeakReference(listener)

        @SuppressLint("ObsoleteSdkInt")
        fun execute() {
            val window = activityRef.get()
            val listener = listenerRef.get()

            if (window != null && listener != null) {
                val root = getContentView(window)
                if (root != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        root.viewTreeObserver.removeOnGlobalLayoutListener(listener)
                    } else {
                        root.viewTreeObserver.removeGlobalOnLayoutListener(listener)
                    }
                }
            }
            activityRef.clear()
            listenerRef.clear()
        }
    }
}