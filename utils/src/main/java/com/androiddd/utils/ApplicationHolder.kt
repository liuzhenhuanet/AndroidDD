package com.androiddd.utils

import android.annotation.SuppressLint
import android.app.Application
import android.os.Handler
import android.os.Looper

@SuppressLint("StaticFieldLeak")
/**
 * Created by liuzhenhua on 2017/11/22.
 */
object ApplicationHolder {
    var mApplication: Application? = null
    private var mHanlder = Handler(Looper.getMainLooper())
}