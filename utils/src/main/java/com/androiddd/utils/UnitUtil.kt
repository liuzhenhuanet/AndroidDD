package com.androiddd.utils

import android.content.Context

/**
 * Created by liuzhenhua on 2017/11/13.
 */
fun dp2px(context: Context, dp: Float) = dp * context.resources.displayMetrics.density + 0.5f