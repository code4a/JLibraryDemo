package com.code4a.jlibrary.crashhandler;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.code4a.jlibrary.utils.AppUtil;
import com.code4a.jlibrary.utils.DateUtil;
import com.code4a.jlibrary.utils.LogUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class UnCeHandler implements UncaughtExceptionHandler {

    private static final String TAG = UnCeHandler.class.getSimpleName();

    private Map<String, String> infos = new HashMap<String, String>();

    private UncaughtExceptionHandler mDefaultHandler;
    private Application appInstance;

    private int resId;
    private Class<?> restartClass;

    protected UnCeHandler(Application appInstance, @StringRes int resId, Class<?> restartClass) {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        this.appInstance = appInstance;
        this.resId = resId;
        this.restartClass = restartClass;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        LogUtil.i(TAG, "uncaughtException ---------------- ");
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            LogUtil.i(TAG, "uncaughtException ---------------- write crash log to file ");
            collectDeviceInfo(appInstance); //手机手机信息
            writeCrashInfoToFile(ex); //写入崩溃文件
            restart(appInstance); // 应用重启
        }

    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex 异常
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(appInstance.getApplicationContext(), resId2String(resId), Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        return true;
    }

    private String resId2String(int resId) {
        return appInstance.getResources().getString(resId);
    }

    /**
     * @param ctx 手机设备相关信息
     */
    private void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
                infos.put("crashTime", DateUtil.getCurDateStr(DateUtil.FORMAT_YMDHMS));
            }
        } catch (NameNotFoundException e) {
            LogUtil.e(TAG, "an error occured when collect package info" + e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                LogUtil.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                LogUtil.e(TAG, "an error occured when collect crash info" + e);
            }
        }
    }

    /**
     * @param ex 将崩溃写入文件系统
     */
    private void writeCrashInfoToFile(Throwable ex) {
        LogUtil.i(TAG, "----- writeCrashInfoToFile --------");
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        // 这里把刚才异常堆栈信息写入SD卡的Log日志里面
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String sdcardPath = Environment.getExternalStorageDirectory().getPath();
            String filePath = sdcardPath + "/" + AppUtil.getAppName(appInstance).toLowerCase() +  "_crash_log/";
            LogUtil.i(TAG, "----- writeLog start --------" + filePath);
            String localFileUrl = writeLog(sb.toString(), filePath);
            LogUtil.i(TAG, "----- writeLog end --------" + localFileUrl);
        }
    }

    /**
     * @param log log内容
     * @param filePath log名称
     * @return 返回写入的文件路径 写入Log信息的方法，写入到SD卡里面
     */
    private String writeLog(String log, String filePath) {
        LogUtil.i("TAG", "writeLog 创建日志文件 start");
        String timestamp = "crash_" + DateUtil.getCurDateStr(DateUtil.FORMAT_YMDHMS) + ".log";
        String filename = filePath + timestamp;
        LogUtil.i("TAG", "writeLog 创建日志文件 start fileName = " + filename);
        File file = new File(filename);
        boolean isSuccess = false;
        if (!file.getParentFile().exists()) {
            isSuccess = file.getParentFile().mkdirs();
            LogUtil.i("TAG", "writeLog 创建日志文件父目录 file.getParentFile() = " + file.getParentFile() + " , isSuccess : " + isSuccess);
        }

        try {
            LogUtil.i("TAG", "检测父级目录是否创建成功");
            if(isSuccess){
                file.createNewFile();
                file.canRead();
                file.canWrite();
            }else{
                file = new File(appInstance.getExternalFilesDir("logs").getAbsoluteFile() + File.separator + timestamp);
            }
            LogUtil.i("TAG", "writeLog 写入到SD卡里面 start file exists = " + file.exists() + " , filename : " + file.getAbsolutePath());
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            // 写入相关Log到文件
            bw.write(log);
            bw.newLine();
            bw.close();
            fw.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            LogUtil.e(TAG, "an error occured while writing file..."+e);
            e.printStackTrace();
            return null;
        }
    }

    private void restart(Context mContext) {
        LogUtil.i(TAG, "----- restart --------");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LogUtil.e(TAG, "error : "+e);
        }
        Intent intent = new Intent(mContext.getApplicationContext(), restartClass);
        PendingIntent restartIntent = PendingIntent.getActivity(mContext.getApplicationContext(), 0, intent, PendingIntent.FLAG_NO_CREATE);
        // 退出程序
        AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1秒钟后重启应用
        exitApp();
    }

    private void exitApp() {
        // 杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

}
