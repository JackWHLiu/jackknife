package com.lwh.jackknife.crash.policy

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

    private var folderName = ""//手机系统根目录保存日志文件夹的名称

    constructor(folderName: String = "jackknife", group: CrashGroup = DefaultCrashGroup(),
                policy: CrashReportPolicy? = null) : super(
        group,
        policy
    ) {
        this.folderName = folderName
    }

    override fun report(info: CrashInfo, group: CrashGroup) {
        super.report(info, group)
        try {
            if (group.matches()) {
                val path = Environment.getExternalStorageDirectory().absolutePath
                val simpleDateFormat = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
                val time = simpleDateFormat.format(Date())
                val folder = File(path, folderName)
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