package com.lwh.jackknife.crash.filter

import java.util.*

/**
 * It is used to combine the superposition of two or more filters.
 * 通过它来组合两种或两种以上的过滤器的叠加。
 */
class CrashFilterChain {

    private val filters: MutableList<CrashFilter>

    fun addFirst(filter: CrashFilter): CrashFilterChain {
        filters.add(0, filter)
        return this
    }

    fun addLast(filter: CrashFilter): CrashFilterChain {
        filters.add(filter)
        return this
    }

    /**
     * The whole filter chain.
     * 一条完整的过滤器链。
     */
    val filter: CrashFilter?
        get() {
            var first: CrashFilter? = null
            var last: CrashFilter? = null //上次的
            for (i in filters.indices) {
                val filter = filters[i]
                if (i == 0) {
                    first = filter
                } else {
                    last!!.setNextFilter(filter)
                }
                last = filter
            }
            return first
        }

    init {
        filters = LinkedList()
    }
}