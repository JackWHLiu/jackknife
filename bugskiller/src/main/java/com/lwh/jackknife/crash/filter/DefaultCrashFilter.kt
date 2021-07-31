package com.lwh.jackknife.crash.filter

import com.lwh.jackknife.crash.CrashInfo

/**
 * No filtering is done by default.
 * 默认不作过滤处理。
 */
class DefaultCrashFilter : CrashFilter() {

    override fun handle(info: CrashInfo): Boolean {
        return true
    }
}