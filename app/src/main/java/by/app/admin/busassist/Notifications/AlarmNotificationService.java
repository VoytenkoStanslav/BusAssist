package by.app.admin.busassist.Notifications;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import by.app.admin.busassist.MainActivity;
import by.app.admin.busassist.R;

/**
 * Created by Admin on 10.05.2018.
 */

public class AlarmNotificationService extends IntentService {
    private NotificationManager alarmNotificationManager;

    //Notification ID for Alarm
    public static final int NOTIFICATION_ID = 1;

    //Resources res = this.getResources();

    public AlarmNotificationService() {
        super("AlarmNotificationService");
    }

    @Override
    public void onHandleIntent(Intent intent) {

        //Send notification
        sendNotification("У вас важная задача. Проверьте список задач, чтобы ничего не забыть");
    }

    //handle notification
    private void sendNotification(String msg) {
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        //get pending intent
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Log.e("LooooogNotif", "tnnnn");

        //Create notification
        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this).setContentTitle("Напоминание").setSmallIcon(R.mipmap.ic_app)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                //.setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_app))
                .setContentText(msg).setAutoCancel(true);
        alamNotificationBuilder.setContentIntent(contentIntent);

        Notification n = alamNotificationBuilder.build();

        n.defaults = Notification.DEFAULT_ALL;

        //notiy notification manager about new notification
        alarmNotificationManager.notify(NOTIFICATION_ID, n);
    }
}
