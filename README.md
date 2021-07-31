## Jackknife（BUG杀手）![License: GPL-3.0](https://img.shields.io/badge/license-GPL--3.0-orange.svg) ![Release](https://jitpack.io/v/JackWHLiu/jackknife.svg)

简介：BUG杀手

使用方式（简单两步）：

1. **配置依赖**

   ```groovy
   maven { url 'https://jitpack.io' }
   
   def latest_version = 'x.x'
   
   api "com.github.JackWHLiu:jackknife:$latest_version"
   ```

2. **代码**

   ```kotlin
   // 配置一下存崩溃日志的文件夹
   CrashReport.Config(context)
               .crashReportPolicy(StoragePolicy("AppFolder/log")) 
               .start()
   ```

   或

   ```kotlin
   CrashReport.Config(context).start {
     					// 使用高阶函数配置一下存崩溃日志的文件夹
               crashReportPolicy(StoragePolicy("AppFolder"))	
           }
   ```

   或

   ```kotlin
   CrashReport.init(context)	//使用一行代码的默认配置
   ```

   
