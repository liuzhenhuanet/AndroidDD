package com.androiddd.utils.java;

import android.content.Context;

/**
 * Created by liuzhenhua on 2017/11/13.
 */

public class JavaUnitUtil {
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }
}
