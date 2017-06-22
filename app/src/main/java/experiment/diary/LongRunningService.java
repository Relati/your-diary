package experiment.diary;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import java.util.Calendar;
import java.util.TimeZone;

/*
 * Created by 志锋 on 2016/12/23.
 */

/*
 * 这是一个service，主要用来运行定时提醒功能；
 * 这里采用SharedPreferences这个轻量级的内部存储方式来获取设定的时间，
 * 也就是在DiaryListActivity.java文件里存储，而在这里获取，然后使用Calendar来设定时间
 * 使用AlarmManager来进行计时
 */
public class LongRunningService extends Service {
    int mHour;
    int mMinute;
    /* 声明一个SharePreferences */
    private SharedPreferences sp;
    private String beginAlarmReceiver = "beginAlarmReceiver";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*
     * 在onStartCommand（）中实现对时间的计算并计时
     * 在时间到了之后发送一个广播，接收器接收到这个广播后会进行提醒
     * 如果没有关闭这个service且没有更改设定的时间，则会继续按照这个设定的时间在第二天进行提醒
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /* 声明AlarmManager对象manager */
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        /* SharedPreferences对象sp从UserNote中获取数据，且UserNote这个文件是私有数据只能被应用自身访问 */
        sp = getSharedPreferences("UserNote", MODE_PRIVATE);
        mHour = sp.getInt("setHour",0);
        mMinute = sp.getInt("setMinute",0);

        /*
         * 创建一个自身这个service与AlarmReceiver关联的Intent对象mIntent
         * 通过setAction里的beginAlarmReceiver从AndroidManifest.xml中找到对应的activity
         */
        Intent mIntent = new Intent(this, AlarmReceiver.class);
        mIntent.setAction(beginAlarmReceiver);

        /*
         * 使用PendingIntent在某个事件完成后执行特定的Action，在这里就是计时结束后执行mIntent里包含的Action
         * 这个文件中的PendingIntent是和AlarmManager对象结合使用的
         */
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, mIntent, 0);

        /*
         * firstTime表示开机之后到现在的运行时间(包括睡眠时间)
         * systemTime表示当前时间(自1970年1月1日0时起的毫秒数)
         */
        long firstTime = SystemClock.elapsedRealtime();
        long systemTime = System.currentTimeMillis();

        /*
         * getInstance()获取一个Calendar对象
         * 使用setTimeInMillis()，Calendar对象根据输入的毫秒数来获得具体的当前时间
         * 由于我们处于东8区，所以用使用setTimeZone()方法设置时区
         * 接着使用set方法将Calendar对象里的时-分设定为我们选择的提醒时间
         * 这样我们就得到了一个在X时X分的提醒时间，而日期则是当前日期
         */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.set(Calendar.MINUTE, mMinute);
        calendar.set(Calendar.HOUR_OF_DAY, mHour);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        /* 将选择的今天的提醒时刻从Calendar转换为毫秒 */
        long selectTime = calendar.getTimeInMillis();

        /*
         * 对我们选择的提醒时间和当前时间进行比较
         * 如果当前时间比提醒时间要大，说明设定的时间（时-分）已经在之前就过去了
         * 此时要把日期调到第二天，就要使用add方法是日期加1
         * 然后更新selectTime为新设置好的时间
         */
        if(systemTime > selectTime) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            selectTime = calendar.getTimeInMillis();
        }

        /*
         * 计算现在时间到设定时间的时间差
         * 将时间差和开机之后到现在的运行时间相加
         * 得到的这个时间表示运行到了那个时候就要进行提醒
         */
        long time = selectTime - systemTime;
        firstTime += time;

        /* 使用serRepeating方法注册一个定时器，表示周期性发送广播，这个周期是一天 */
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 24*60*60*1000, sender);

        /* 输出我们用到的几个时间的具体信息 */
        Log.i("act", "time ==== " + time + ", selectTime ===== "
                + selectTime + ", systemTime ==== " + systemTime + ", firstTime === " + firstTime);

        return super.onStartCommand(intent, flags, startId);
    }

    /* 在结束service的时候进行的处理 */
    @Override
    public void onDestroy() {
        /* 这个service自身要撤销 */
        super.onDestroy();

        /*
         * 在Service结束后关闭AlarmManager
         * 使用PendingIntent对象表示这个service和AlarmReceiver的联系
         * 使用cancel()方法取消与参数匹配的定时器，这个参数就是上面的PendingIntent对象
         */
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.cancel(pi);

        /* 停止service */
        stopSelf();
    }
}
