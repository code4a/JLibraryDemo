package com.code4a.jlibrary.crashhandler;

import android.app.Application;
import android.support.annotation.StringRes;

/**
 * Created by code4a on 2016/12/27.
 */

public final class AndroidCrash {

    /**
     * 在application 的onCreate方法中，调用初始化crashHandler
     * @param appInstance 应用的Application
     * @param resId 异常崩溃的提示信息
     * @param restartClass 异常重启，需要回到的界面class
     */
    public static void initCrashHandler(Application appInstance, @StringRes int resId, Class<?> restartClass){
        // 设置该CrashHandler为程序的默认处理器
        UnCeHandler catchExcep = new UnCeHandler(appInstance, resId, restartClass);
        Thread.setDefaultUncaughtExceptionHandler(catchExcep);
    }
}
