package com.lwh.jackknife.crash.policy

import com.lwh.jackknife.crash.group.CrashGroup
import com.lwh.jackknife.crash.CrashInfo

/**
 * Strategies. You can use different strategies to distribute information.
 * 策略，你可以使用不同的策略来分发信息。
 */
interface ICrashReportPolicy {

    fun report(info: CrashInfo, group: CrashGroup)
    val group: CrashGroup
}