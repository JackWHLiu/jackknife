package com.lwh.jackknife.crash

import android.content.Context
import android.os.Process
import com.lwh.jackknife.CrashReport

/**
 * It is used to intercept all exceptions thrown by the application.
 * 它被用于拦截应用抛出的所有异常。
 */
object CrashHandler : Thread.UncaughtExceptionHandler {

    private lateinit var context: Context
    private lateinit var report: CrashReport
    private lateinit var handler: Thread.UncaughtExceptionHandler

    fun init(context: Context, report: CrashReport) {
        CrashHandler.context = context.applicationContext
        CrashHandler.report = report
        handler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        if (report.enabled) {
            val filterResult = report.filter.filterCrashInfo(report.info)
            if (filterResult) {
                interceptException(t, e)
            }
        }
        if (!report.interceptCrash) {
            // If the Android system provides an exception handling class, it shall have handled
            // by the system.
            // 如果系统提供了异常处理类，则交给系统去处理
            if (handler != null) {
                handler.uncaughtException(t, e)
            } else {
                // Otherwise we handle it ourselves, we handle it ourselves usually by letting
                // the app exit
                // 否则我们自己处理，自己处理通常是让app退出
                Process.killProcess(Process.myPid())
                System.exit(0)
            }
        }
    }

    private fun interceptException(t: Thread?, e: Throwable?) {
        // Collect exception information and do our own handling
        // 收集异常信息，做我们自己的处理
        val collector = CrashCollector()
        val info = report.info
        info.throwable = e
        info.thread = t
        collector.collect(info)
        collector.report(report.policy)
    }
}