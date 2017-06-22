package experiment.diary;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

/*
 * Created by 志锋 on 2016/12/23.
 */
public class AlarmReceiver extends BroadcastReceiver {
    /* 振动器Vibrator */
    private Vibrator vibrator;
    private static String beginAlarmReceiver = "beginAlarmReceiver";

    /* 计时器，用来记录振动次数 */
    private int times = 0;

    /* 创建一个Handler对象用来执行我们接下来完成的Runnable对象 */
    private Handler handler = new Handler();

    /* 
     * 创建一个Runnable对象runnable来执行振动的线程
     * 在run()方法中实现具体的设计：
     * 如果振动器振动次数>=10次，使用removeCallbacks()方法停止这个runnable
     * 否则就使用postDelayed方法使振动以停止0.1s--开启0.4s--停止0.1s--开启0.4s的方式执行10次，每次间隔1s
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (times >= 10) {
                handler.removeCallbacks(runnable);
                times = 0;
            } else  {
                /*
                 * 想设置震动大小可以通过改变pattern来设定，如果开启时间太短，震动效果可能感觉不到
                 * 设置pattern为 停止0.1s 开启0.4s 停止0.1s 开启0.4s
                 * 重复两次上面的pattern 如果只想震动一次，index设为-1
                 * 使用postDelayed方法再次调用这个Runnable对象，以实现每1s实现1次的定时器操作
                 */
                long [] pattern = {100,400,100,400};
                vibrator.vibrate(pattern,-1);
                handler.postDelayed(runnable, 1000);
                times++;
            }
        }
    };

    /*
     * onReceive()方法中，如果接收到了beginAlarmReceiver这个action，说明设定的提醒时间到了，则执行：
     * 1.显示一个toast告诉用户闹铃响了
     * 2.在通知栏中通知用户
     * 3.如果点击通知栏可以打开APP
     * 4.进行一段时间的振动来提醒用户
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(beginAlarmReceiver)) {
            /*
             * 设置toast的显示内容和显示时间（Toast.LENGTH_LONG表示3.5s），并让toast显示在屏幕上（show方法）
             */
            Toast.makeText(context, "闹铃响了", Toast.LENGTH_LONG).show();

            /*
             * 设置通知内容并在onReceive()这个函数执行时开启
             * 首先要创建 NotificationManager对象，其中创建的 manager 对象负责“发出”与“取消”Notification
             * 接着使用Notification.Builder来实例化一个Builder对象，用来设置通知的各种属性
             * 例如设置通知的title为“提醒”，文本内容为“写日记去”，优先级设置为默认情况
             * 启动时间为系统时间，使用setSmallIcon()方法设置小图标样式
             * 使用setAutoCancel()方法设置是否维护，这里设置为true表示通知栏在点击后会取消
             * 然后创建一个Intent对象，并使用一个PendingIntent对象封装这个Intent对象
             * 使用setContentIntent方法来设置这个通知的意图，这里表示点击后启动StartActivity这个activity
             * 这样就设置好了通知的属性，通过Notification.Builder.build()方法得到一个Notification对象
             * 然后使用NotificationManager.notify()方法发送这个通知
             */
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("提醒")
                    .setContentText("写日记去")
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_menu_send)
                    .setAutoCancel(true);

            Intent mIntent = new Intent(context,StartActivity.class);
            PendingIntent mPendingIntent = PendingIntent.getActivity(context,0,mIntent,0);
            builder.setContentIntent(mPendingIntent);

            Notification notification = builder.build();
            manager.notify(0, notification);

            /* 创建一个Vibrator对象，要使用getSystemService方法获取 */
            vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);

            /* 使用handler的post方法执行我们设置好的runnable以此实现振动 */
            handler.post(runnable);
        }
    }
}
