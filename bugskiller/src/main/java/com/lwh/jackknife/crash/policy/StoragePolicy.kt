package com.lwh.jackknife.crash.policy

import android.annotation.TargetApi
import android.os.Build
import android.os.Environment
import android.util.Log
import com.lwh.jackknife.crash.group.CrashGroup
import com.lwh.jackknife.crash.CrashInfo
import com.lwh.jackknife.crash.group.DefaultCrashGroup
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Save crash log information to SD card, please apply for storage permission by yourself.
 * 把崩溃日志信息保存到SD卡，请自行申请存储权限。
 */
class StoragePolicy : CrashReportPolicy {

    /**
     * 由于Android11的机制，该方案被淘汰。
     */
    @Deprecated("使用path替代")
    private var folderName = ""//手机系统根目录保存日志文件夹的名称

    private var path = ""

    constructor(folderName: String = "jackknife", group: CrashGroup = DefaultCrashGroup(),
                policy: CrashReportPolicy? = null) : super(
        group,
        policy
    ) {
        this.folderName = folderName
    }

    constructor(folderName: String = "jackknife",
                path: String = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath}/${folderName}",
                group: CrashGroup = DefaultCrashGroup(),
                policy: CrashReportPolicy? = null) : super(
        group,
        policy
    ) {
        this.folderName = folderName
        this.path = path
    }

    override fun report(info: CrashInfo, group: CrashGroup) {
        super.report(info, group)
        try {
            if (group.matches()) {
                val simpleDateFormat = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
                val time = simpleDateFormat.format(Date())
                val folder = File(path)
                folder.mkdirs()
                val file = File(folder.absolutePath, "crash$time.txt")
                if (!file.exists()) {
                    file.createNewFile()
                }
                val buffer = info.toString().trim { it <= ' ' }.toByteArray()
                val fileOutputStream = FileOutputStream(file)
                fileOutputStream.write(buffer, 0, buffer.size)
                fileOutputStream.flush()
                fileOutputStream.close()
            }
        } catch (e: IOException) {
            Log.e("jackknife", "崩溃日志信息存储失败，$e")
        }
    }
}