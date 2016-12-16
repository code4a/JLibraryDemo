package com.code4a.jlibrary.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.code4a.jlibrary.service.DeleteService;

/**
 * Created by sk on 2016/6/13.
 */

public class NotificationUtil {

    public static final String ACTION_NOTIFITION_DISMISS = "com.code4a.action.ACTION_NOTIFY_DISMISSED";

    private static int NOTIFICATION_ID;
    private NotificationManager nm;
    private Notification notification;
    private NotificationCompat.Builder cBuilder;
    private Notification.Builder nBuilder;
    private Context mContext;
    int requestCode = (int) SystemClock.uptimeMillis();
    private static final int FLAG = PendingIntent.FLAG_NO_CREATE;

    public NotificationUtil(Context context, int ID) {
        this.NOTIFICATION_ID = ID;
        mContext = context;
        // 获取系统服务来初始化对象
        nm = (NotificationManager) mContext
                .getSystemService(Activity.NOTIFICATION_SERVICE);
        cBuilder = new NotificationCompat.Builder(mContext);
    }

    /**
     * 设置在顶部通知栏中的各种信息
     *
     * @param intent
     * @param smallIcon
     * @param ticker
     */
    private void setCompatBuilder(Intent intent, int smallIcon, String ticker,
                                  String title, String msg) {
        // 如果当前Activity启动在前台，则不开启新的Activity。
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // 当设置下面PendingIntent.FLAG_UPDATE_CURRENT这个参数的时候，常常使得点击通知栏没效果，你需要给notification设置一个独一无二的requestCode
        // 将Intent封装进PendingIntent中，点击通知的消息后，就会启动对应的程序
        PendingIntent pIntent = PendingIntent.getActivity(mContext,
                requestCode, intent, FLAG);

        cBuilder.setContentIntent(pIntent);// 该通知要启动的Intent

        cBuilder.setSmallIcon(smallIcon);// 设置顶部状态栏的小图标
        cBuilder.setTicker(ticker);// 在顶部状态栏中的提示信息

        cBuilder.setContentTitle(title);// 设置通知中心的标题
        cBuilder.setContentText(msg);// 设置通知中心中的内容
        cBuilder.setWhen(System.currentTimeMillis());

        /*
         * 将AutoCancel设为true后，当你点击通知栏的notification后，它会自动被取消消失,
         * 不设置的话点击消息后也不清除，但可以滑动删除
         */
        cBuilder.setAutoCancel(true);
        // 将Ongoing设为true 那么notification将不能滑动删除
        // notifyBuilder.setOngoing(true);
        /*
         * 从Android4.1开始，可以通过以下方法，设置notification的优先级，
         * 优先级越高的，通知排的越靠前，优先级低的，不会在手机最顶部的状态栏显示图标
         */
        cBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        /*
         * Notification.DEFAULT_ALL：铃声、闪光、震动均系统默认。
         * Notification.DEFAULT_SOUND：系统默认铃声。
         * Notification.DEFAULT_VIBRATE：系统默认震动。
         * Notification.DEFAULT_LIGHTS：系统默认闪光。
         * notifyBuilder.setDefaults(Notification.DEFAULT_ALL);
         */
        cBuilder.setDefaults(Notification.DEFAULT_ALL);
        Intent dismissedIntent = new Intent(ACTION_NOTIFITION_DISMISS);
        PendingIntent deleteIntent = PendingIntent.getBroadcast(mContext, 0, dismissedIntent, 0);
        cBuilder.setDeleteIntent(deleteIntent);
    }

    /**
     * 普通的通知
     *
     * @param intent
     * @param smallIcon
     * @param ticker
     * @param title
     * @param msg
     */
    public void normal_notification(Intent intent, int smallIcon,
                                    String ticker, String title, String msg) {

        setCompatBuilder(intent, smallIcon, ticker, title, msg);
        sent();
    }

    /**
     * 进行多项设置的通知(在小米上似乎不能设置大图标，系统默认大图标为应用图标)
     *
     * @param intent
     * @param smallIcon
     * @param ticker
     * @param LargeIcon
     * @param title
     * @param msg
     */
    public void special_notification(Intent intent, int smallIcon,
                                     String ticker, int LargeIcon, String title, String msg) {

        setCompatBuilder(intent, smallIcon, ticker, title, msg);

        // 如果不设置LargeIcon，那么系统会默认将上面的SmallIcon作为主要图标，显示在通知选项的最左侧，右下角的小图标将不再显示
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
                LargeIcon);
        cBuilder.setLargeIcon(bitmap);

        // 将Ongoing设为true 那么notification将不能滑动删除
        cBuilder.setOngoing(true);
        // 删除时
        Intent deleteIntent = new Intent(mContext, DeleteService.class);
        int deleteCode = (int) SystemClock.uptimeMillis();
        // 删除时开启一个服务
        PendingIntent deletePendingIntent = PendingIntent.getService(mContext,
                deleteCode, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        cBuilder.setDeleteIntent(deletePendingIntent);

        cBuilder.setDefaults(Notification.DEFAULT_SOUND | // 设置使用默认的声音
                Notification.DEFAULT_LIGHTS);// 设置使用默认的LED
        cBuilder.setVibrate(new long[] { 0, 100, 200, 300 });// 设置自定义的振动
        cBuilder.setAutoCancel(true);
        // builder.setSound(Uri.parse("file:///sdcard/click.mp3"));

        // 设置通知样式为收件箱样式,在通知中心中两指往外拉动，就能出线更多内容，但是很少见
        cBuilder.setNumber(3);
        cBuilder.setStyle(new NotificationCompat.InboxStyle()
                .addLine("M.Lynn 你好，我是kale").addLine("M.Lynn 已收到，保证完成任务")
                .addLine("M.Lynn 哈哈，明白了~").setSummaryText("+3 more")); // 设置在细节区域底端添加一行文本
        sent();
    }

    /**
     * 自定义视图的通知
     *
     * @param remoteViews
     * @param intent
     * @param smallIcon
     * @param ticker
     */
    public void view_notification(RemoteViews remoteViews, Intent intent,
                                  int smallIcon, String ticker) {

        setCompatBuilder(intent, smallIcon, ticker, null, null);

        notification = cBuilder.build();
        notification.contentView = remoteViews;
        // 发送该通知
        nm.notify(NOTIFICATION_ID, notification);
    }


    /**
     * 发送通知
     */
    private void sent() {
        notification = cBuilder.build();
        // 发送该通知
        nm.notify(NOTIFICATION_ID, notification);
    }

    /**
     * 根据id清除通知
     */
    public void clear() {
        // 取消通知
        nm.cancelAll();

    }

}
