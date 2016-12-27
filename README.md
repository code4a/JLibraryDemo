# JLibrary

抽取的Activity和Fragment基类以及一些工具类!

* 支持程序crash后log收集，程序异常退出后，重启app
* 新增bugly的bug上报和应用更新！通过JbuglyManger修改appid和appkey 并初始化bugly
* 添加支持设置系统状态栏颜色，兼容Android 4.4.2(API 19)以上

##### 使用CrashHandler收集crash日志，并重启应用

需要在Application的onCreate方法中，调用如下代码。
```java
    AndroidCrash.initCrashHandler(Application appInstance, @StringRes int resId, Class<?> restartClass);
```

**正常情况下日志信息存放在SD卡路径下的appname_crash_log目录下，但Android7.0因权限问题，文件目前只能存放在应用的logs文件夹内**

##### 初始化与使用bugly

需要在Application的onCreate方法中，先调用如下代码，初始化自己工程的appid和appkey。
```java
    JBuglyManager.setAppIdAndKey(appId, appKey);
```
接着调用如下代码。
```java
    JBuglyManager.initCrashReportAndUpdate(this, true);
```

##### 修改状态栏颜色

如何你的Activity继承自该库的base，则在其onCreate方法中调用以下代码。
```java
    setStatusBarColor(int color);
```
如果是自己的Activity，则需要在onCreate中调用以下代码。
```java
    StatusBarCompat.setStatusBarColor(this, color, lightStatusBar);
```
或者是
```java
    StatusBarCompat.setStatusBarColor(this, color);
```

# 如何依赖

#### Gradle
```
dependencies {
  compile 'com.code4a:jlibrary:1.0.4'
}
```

#### Maven
```
<dependency>
  <groupId>com.code4a</groupId>
  <artifactId>jlibrary</artifactId>
  <version>1.0.4</version>
  <type>pom</type>
</dependency>
```

# 依赖

This project use this libraries ~ Thanks to them.

  [Butterknife](https://github.com/JakeWharton/butterknife)  <br>
  [Gson](https://github.com/google/gson)  <br>
  [Bugly](https://github.com/BuglyDevTeam/Bugly-Android-Demo)  <br>
  [status-bar-compat](https://github.com/msdx/status-bar-compat)  <br>


License
--------

    Copyright 2016 code4a.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


