package com.lwh.jackknife

import android.content.Context
import com.lwh.jackknife.crash.CrashHandler
import com.lwh.jackknife.crash.CrashInfo
import com.lwh.jackknife.crash.filter.CrashFilter
import com.lwh.jackknife.crash.filter.DefaultCrashFilter
import com.lwh.jackknife.crash.policy.CrashReportPolicy
import com.lwh.jackknife.crash.policy.StoragePolicy

/**
 * Configuration of global crash information collection, as well as partial log information
 * collection.
 * 全局崩溃信息收集的配置，也包括部分日志信息收集的配置。
 */
class CrashReport(config: Config) {

    var policy //崩溃信息上报策略，提供本地存储、邮件接收、网页查看等内置策略，也可自定义
            : CrashReportPolicy
    var filter //决定是否要上报该次崩溃信息
            : CrashFilter
    var info //包含崩溃信息内容
            : CrashInfo
    var enabled //是否开启崩溃日志收集功能，这个值可以灵活设置
            : Boolean
    var interceptCrash //收集崩溃信息后，是否让应用闪退，true则不闪退
            : Boolean

    class Config(context: Context) {

        private var context: Context = context
        var policy: CrashReportPolicy = StoragePolicy()
        var filter: CrashFilter = DefaultCrashFilter()
        var info: CrashInfo
        var enabled = true
        var interceptCrash = false

        fun crashReportPolicy(policy: CrashReportPolicy): Config {
            this.policy = policy
            return this
        }

        // Either you create a CrashReportFilter directly, or you use the method named
        // "CrashFilterChain#getFilter()".
        // 要么你直接创建一个CrashFilter，要么你使用CrashFilterChain的getFilter()方法。
        fun filterChain(filter: CrashFilter): Config {
            this.filter = filter
            return this
        }

        fun crashInfo(info: CrashInfo): Config {
            this.info = info
            return this
        }

        // Determines if you want to activate all functions of this framework.
        // 确定你是否想要激活属于这个框架的所有功能。
        fun enabled(enabled: Boolean): Config {
            this.enabled = enabled
            return this
        }

        fun interceptCrash(interceptCrash: Boolean): Config {
            this.interceptCrash = interceptCrash
            return this
        }

        private fun build(): CrashReport {
            return CrashReport(this)
        }

        fun start(block: Config.() -> Unit) : Config = apply {
            block()
            start()
        }

        fun start() {
            CrashHandler.init(context, build())
        }

        init {
            info = CrashInfo(context)
        }
    }

    init {
        policy = config.policy
        filter = config.filter
        info = config.info
        enabled = config.enabled
        interceptCrash = config.interceptCrash
    }

    companion object {

        val init: (context: Context) -> Unit = {
            Config(it).start()
        }
    }
}