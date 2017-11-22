package com.androiddd.all

import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        text.text = "hello kotlin!!!"

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        Log.e("liuzhenhua", cacheDir.absolutePath)
        Log.e("liuzhenhua", externalCacheDir.absolutePath)
        Log.e("liuzhenhua", externalCacheDirs.joinToString { it.absolutePath })

//        val intent = Intent(this, SoftKeyboardActivity::class.java)
//        startActivity(intent)
    }

    fun paserApk() {
        val xmlParser = assetManager("/sdcard/aaa.apk").openXmlResourceParser("AndroidManifest.xml")
        try {
            var event = xmlParser.getEventType()   //先获取当前解析器光标在哪
            while (event != XmlPullParser.END_DOCUMENT) {    //如果还没到文档的结束标志，那么就继续往下处理
                when (event) {
                    XmlPullParser.START_DOCUMENT -> Log.i("liuzhenhua", "xml解析开始")
                    XmlPullParser.START_TAG -> {
                        //一般都是获取标签的属性值，所以在这里数据你需要的数据
                        Log.d("liuzhenhua", "当前标签是：" + xmlParser.getName())
                        //两种方法获取属性值
                        if (xmlParser.name.equals("uses-sdk"))
                            for (i in 0 until xmlParser.attributeCount) {
                                Log.d("liuzhenhua", "第一个属性：" + xmlParser.getAttributeName(i)
                                        + ": " + xmlParser.getAttributeValue(i))
                            }
                    }
                    XmlPullParser.TEXT -> Log.d("liuzhenhua", "Text:" + xmlParser.getText())
                    XmlPullParser.END_TAG -> {
                    }
                    else -> {
                    }
                }
                event = xmlParser.next()   //将当前解析器光标往下一步移
            }
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun assetManager(apkName: String): AssetManager {
        val assetManager = AssetManager::class.java.newInstance()
        val addAssetPath = assetManager.javaClass.getMethod("addAssetPath", String::class.java)//反射调用方法addAssetPath(String path)
        //第二个参数是apk的路径：Environment.getExternalStorageDirectory().getPath()+File.separator+"plugin"+File.separator+"apkplugin.apk"
        addAssetPath.invoke(assetManager, apkName) //将未安装的Apk文件的添加进AssetManager中，第二个参数为apk文件的路径带apk名
        return assetManager
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
//        val apk = AndroidApk(File("${Environment.getExternalStorageDirectory() }/aaa.apk"))
//        Log.e("liuzhenhua", "appversion:${apk.appVersion}, appVersionCode:${apk.appVersionCode}, minSdkVersion:${apk.minSdkVersion}, maxSdkVersion:${apk.maxSdkVersion}, targetSdkVersion:${apk.targetSdkVersion}, packageName:${apk.packageName}")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
