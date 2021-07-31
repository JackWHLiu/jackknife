package com.lwh.jackknife.crash.policy

import com.lwh.jackknife.crash.group.CrashGroup
import com.lwh.jackknife.crash.CrashInfo
import com.lwh.jackknife.crash.group.DefaultCrashGroup

/**
 * Wrap it so that various strategies can be combined freely, the innermost policy is invoked first,
 * and so on. To extend this class, override the report() method.
 * 包装一下，便于多种策略自由组合，最里面的策略最先被执行，以此类推，扩展此类请重写report()方法。
 */
abstract class CrashReportPolicyWrapper protected constructor(//策略自己的分组
    override val group: CrashGroup = DefaultCrashGroup(), private val basePolicy: CrashReportPolicy?

) : ICrashReportPolicy {

    override fun report(info: CrashInfo, group: CrashGroup) {
        basePolicy?.report(info, basePolicy.group)
    }
}