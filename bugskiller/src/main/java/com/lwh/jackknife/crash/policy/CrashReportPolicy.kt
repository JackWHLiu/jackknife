package com.lwh.jackknife.crash.policy

import com.lwh.jackknife.crash.group.CrashGroup
import com.lwh.jackknife.crash.group.DefaultCrashGroup

/**
 * A channel used to represent crash information.
 * 用来表示崩溃信息的通道。
 */
abstract class CrashReportPolicy protected constructor(group: CrashGroup = DefaultCrashGroup(),
                                                       policy: CrashReportPolicy?) :
    CrashReportPolicyWrapper(group, policy)