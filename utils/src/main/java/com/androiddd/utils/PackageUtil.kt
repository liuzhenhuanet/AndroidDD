package com.androiddd.utils

import android.content.Context
import android.content.res.XmlResourceParser
import android.os.Build
import android.util.Log
import org.xmlpull.v1.XmlPullParser
import java.io.IOException

/**
 * Created by liuzhenhua on 2017/11/22.
 */
private val tag = "PackageUtil"

private fun getMinSdkVersionByAsset(apkFile: String): String? {
    val assetManager = createAssertManager(apkFile) ?: return null
    val xmlParser: XmlResourceParser?
    try {
        xmlParser = assetManager.openXmlResourceParser("AndroidManifest.xml")
    } catch (e: IOException) {
        Log.e(tag, "读取AndroidManifest.xml出错")
        return null
    }

    try {
        var event = xmlParser!!.eventType   //先获取当前解析器光标在哪
        while (event != XmlPullParser.END_DOCUMENT) {    //如果还没到文档的结束标志，那么就继续往下处理
            when (event) {
                XmlPullParser.START_TAG -> {
                    //两种方法获取属性值
                    if (xmlParser.name == "uses-sdk")
                        (0 until xmlParser.attributeCount)
                                .filter { "minSdkVersion" == xmlParser.getAttributeName(it) }
                                .forEach { return xmlParser.getAttributeValue(it) }
                }
            }
            event = xmlParser.next()   //将当前解析器光标往下一步移
        }
    } catch (throwable: Throwable) {
        Log.e(tag, "解析xml出错", throwable)
    }

    return null
}

fun getMinSdkVersion(context: Context, apkFile: String): Int {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        val pm = context.packageManager
        val info = pm.getPackageArchiveInfo(apkFile, 0)
        if (info != null) {
            val appInfo = info.applicationInfo
            return appInfo.minSdkVersion
        }
    } else {
        return Integer.parseInt(getMinSdkVersionByAsset(apkFile))
    }
    return 0
}