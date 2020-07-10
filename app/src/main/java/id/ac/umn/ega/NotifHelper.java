package id.ac.umn.ega;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class NotifHelper extends ContextWrapper {

    private NotificationManager notificationManager;
    private static final String NOTIF_CHANNEL_ID = "id.ac.umn.ega";
    private static final String NOTIF_CHANNEL_NAME = "Marvellous";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotifHelper (Context context) {
        super(context);

        NotificationChannel notificationChannel = new NotificationChannel(NOTIF_CHANNEL_ID, NOTIF_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        notifManager().createNotificationChannel(notificationChannel);
    }

    public NotificationManager notifManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getDataNotif(String title, String body) {
        return new Notification.Builder(getApplicationContext(), NOTIF_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.marvel_notif)
                .setAutoCancel(true);
    }

}
