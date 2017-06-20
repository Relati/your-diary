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

/**
 * Created by 志锋 on 2016/12/23.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private Vibrator vibrator;
    private static String beginAlarmReceiver = "beginAlarmReceiver";

    private int times = 0;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (times >= 10) {
                handler.removeCallbacks(runnable);
                times = 0;
            } else  {
                // 想设置震动大小可以通过改变pattern来设定，如果开启时间太短，震动效果可能感觉不到
                long [] pattern = {100,400,100,400};   // 停止 开启 停止 开启
                vibrator.vibrate(pattern,-1);           //重复两次上面的pattern 如果只想震动一次，index设为-1
                handler.postDelayed(runnable, 1000);
                times++;
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(beginAlarmReceiver)) {
            Toast.makeText(context, "闹铃响了", Toast.LENGTH_LONG).show();
            //设置通知内容并在onReceive()这个函数执行时开启
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("提醒").setContentText("写日记去").setPriority(Notification.PRIORITY_DEFAULT)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_menu_send)
                    .setAutoCancel(true);

            Intent mIntent = new Intent(context,StartActivity.class);
            PendingIntent mPendingIntent = PendingIntent.getActivity(context,0,mIntent,0);
            builder.setContentIntent(mPendingIntent);

            Notification notification = builder.build();
            manager.notify(0, notification);

            vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);

            handler.post(runnable);

        }
    }



}
