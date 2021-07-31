package com.lwh.jackknife.crash

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build

/**
 * Developers can extend this custom crash information so that the toString() method must be
 * overridden to take effect.
 * 开发者可以扩展此类来自定义崩溃信息，这样的话，必须重写toString()方法生效。
 */
class CrashInfo(val context: Context) {

    var versionName //版本名称
            : String? = null
    var versionCode //版本号
            = 0
    val sdkVersion //SDK版本号
            : Int
    val release //Android版本号
            : String
    val model //手机型号
            : String
    val brand //手机制造商
            : String
    var thread //崩溃线程
            : Thread? = null
    var throwable //崩溃异常信息
            : Throwable? = null
    val exception: String
        get() = if (throwable != null) {
            buildStackTrace(throwable!!.stackTrace)
        } else ""

    override fun toString(): String {
        return """
               
               Crash线程：${thread!!.name}#${thread!!.id}
               手机型号：$model
               手机品牌：$brand
               SDK版本：$sdkVersion
               Android版本：$release
               版本名称：$versionName
               版本号：$versionCode
               异常信息：${throwable.toString()}$exception
               """.trimIndent()
    }

    private fun buildStackTrace(lines: Array<StackTraceElement>): String {
        val sb = StringBuilder()
        for (line in lines) {
            sb.append("\n").append("at ").append(line.className).append(".").append(line.methodName)
                .append("(").append(line.fileName + ":" + line.lineNumber).append(")")
        }
        return sb.toString()
    }

    init {
        //Gets some information about mobile phone.
        //获取手机的一些信息
        val pm = context.packageManager
        val pkgInfo: PackageInfo
        try {
            pkgInfo = pm.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
            versionName = pkgInfo.versionName
            versionCode = pkgInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            versionName = "unknown"
            versionCode = -1
        }
        sdkVersion = Build.VERSION.SDK_INT
        release = Build.VERSION.RELEASE
        model = Build.MODEL
        brand = Build.MANUFACTURER
    }
}