package com.lwh.jackknife.crash.group

/**
 * Default group, is no group.
 * 默认的分组，即不分组。
 */
class DefaultCrashGroup : CrashGroup {

    override fun matches(): Boolean {
        return true
    }

    override fun groupName(): String {
        return javaClass.simpleName
    }
}