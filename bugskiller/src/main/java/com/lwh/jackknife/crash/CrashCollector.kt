package com.lwh.jackknife.crash

import com.lwh.jackknife.crash.group.CrashGroup
import com.lwh.jackknife.crash.policy.CrashReportPolicy

/**
 * It is designed to collect exceptions thrown by the application.
 * 专门用来收集应用抛出的异常。
 */
class CrashCollector {

    private lateinit var crashInfo: CrashInfo

    fun collect(info: CrashInfo) {
        crashInfo = info
    }

    fun report(policy: CrashReportPolicy) {
        reportCrash(policy, policy.group)
    }

    private fun reportCrash(policy: CrashReportPolicy, group: CrashGroup) {
        policy.report(crashInfo, group)
    }
}