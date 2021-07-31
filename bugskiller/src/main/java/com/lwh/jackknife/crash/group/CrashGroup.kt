package com.lwh.jackknife.crash.group

/**
 * Groups are used to distribute policies.
 * 分组被用来分发策略。
 */
interface CrashGroup {

    /**
     * Through calculation, determine whether the group of rules.
     * 通过计算，判断是否符合该组规则。
     */
    fun matches(): Boolean

    /**
     * The name of group.
     * 分组的名称。
     */
    fun groupName(): String
}