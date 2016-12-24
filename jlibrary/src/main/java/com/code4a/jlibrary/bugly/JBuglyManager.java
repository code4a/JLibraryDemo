package com.code4a.jlibrary.bugly;

import android.content.Context;
import android.text.TextUtils;

import com.tencent.bugly.Bugly;

/**
 * Created by code4a on 2016/12/24.
 */

public class JBuglyManager {

    /**
     * 修改bugly appID 和 appKey
     * @param appId appId
     * @param appKey appKey
     */
    public static void setAppIdAndKey(String appId, String appKey){
        if(!TextUtils.isEmpty(appId) && !TextUtils.isEmpty(appKey)){
            Constant.BUGLY_APPID = appId;
            Constant.BUGLY_APPKEY = appKey;
        }
    }

    /**
     * 初始化bugly crash report
     * 需要在application onCreate 方法中调用
     * 为了保证运营数据的准确性，建议不要在异步线程初始化Bugly。
     * 第二个参数为SDK调试模式开关，调试模式的行为特性如下：
     *    输出详细的Bugly SDK的Log；
     *    每一条Crash都会被立即上报；
     *    自定义日志将会在Logcat中输出。
     *    建议在测试阶段建议设置成true，发布时设置为false。
     *
     * @param mContext 上下文对象
     */
    public static void initCrashReportAndUpdate(Context mContext, boolean isDebug){
        Bugly.init(mContext.getApplicationContext(), Constant.BUGLY_APPID, isDebug);
    }


}
