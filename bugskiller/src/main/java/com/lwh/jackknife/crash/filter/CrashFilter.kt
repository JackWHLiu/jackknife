package com.lwh.jackknife.crash.filter

import com.lwh.jackknife.crash.CrashInfo

/**
 * At the beginning, affects whether the crash info will report.
 * 在一开始的时候，影响崩溃信息是否会上报。
 */
abstract class CrashFilter {

    private var next: CrashFilter? = null

    fun setNextFilter(filter: CrashFilter) {
        next = filter
    }

    fun nextFilter(): CrashFilter? {
        return next
    }

    fun filterCrashInfo(info: CrashInfo): Boolean {
        val result = handle(info)
        return if (result && next != null) {
            next!!.filterCrashInfo(info)
        } else {
            result
        }
    }

    abstract fun handle(info: CrashInfo): Boolean
}