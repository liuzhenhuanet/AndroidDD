package com.androiddd.utils

import android.content.res.AssetManager
import android.util.Log

/**
 * Created by liuzhenhua on 2017/11/22.
 */
private val tag = "AssetUtil"

fun createAssertManager(path: String): AssetManager? {
    try {
        val assetManager = AssetManager::class.java.newInstance()
        val addAssetPath = assetManager.javaClass.getMethod("addAssetPath", String::class.java)//反射调用方法addAssetPath(String path)
        //第二个参数是apk的路径：Environment.getExternalStorageDirectory().getPath()+File.separator+"plugin"+File.separator+"apkplugin.apk"
        addAssetPath.invoke(assetManager, path) //将未安装的Apk文件的添加进AssetManager中，第二个参数为apk文件的路径带apk名
        return assetManager
    } catch (throwable: Throwable) {
        Log.e(tag, "获取AssetManager失败，path: $path", throwable)
    }
    return null
}

