package fr.ulille.iut.agile.client_android;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Piste pour les notifs d'arriere plan
 */
public class AsyncService extends Service {
    private static final String APPNAME = "GREENWATER";
    private static final String DESCRIPTION = "Nous vous notifirons quand votre cuve devra etre vider.";
    public static final String NOTIFICATION_CHANNEL_ID_TELLTALE = "my_channel_id_42";
    public static final String NOTIFICATION_CHANNEL_ID = "my_channel_id_43";

    private Service service = null;
    private Thread thread = null;
    private boolean run = true;
    @Override
    public void onCreate() {
        super.onCreate();
        service = this;
    }
    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(service);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID_TELLTALE, APPNAME, NotificationManager.IMPORTANCE_MIN);
            channel.setDescription(DESCRIPTION);
            notificationManager.createNotificationChannel(channel);
            NotificationChannel channel2 = new NotificationChannel(NOTIFICATION_CHANNEL_ID, APPNAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(DESCRIPTION);
            notificationManager.createNotificationChannel(channel2);
        }
        Notification notification = new NotificationCompat.Builder(service, NOTIFICATION_CHANNEL_ID_TELLTALE)
                .setTicker(APPNAME)
                .setContentTitle(APPNAME)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(DESCRIPTION))
                .setSmallIcon(R.drawable.logo_green_water_crop)
                .setDefaults(NotificationCompat.DEFAULT_ALL | Notification.FLAG_FOREGROUND_SERVICE)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(42, notification);
        //do heavy work on a background thread
        thread = new Thread() {
            @Override
            public void run() {
                /*int idx = 0;
                waitFor(10000);
                DatabaseManager databaseManager = null;
                while (run && idx++ != 10) {
                    StringBuilder contentText = new StringBuilder("Pensez à arroser vos plantes aujourd'hui !");
                    try {
                        databaseManager = new DatabaseManager(service);
                        mesPlantes = databaseManager.readPlantesNeeedWater();
                        for (Plante plante : mesPlantes) {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E dd MMMM yyyy HH:mm", new Locale("fr", "FR"));
                            contentText.append("\n - ").append(plante.getType() + "," + simpleDateFormat.format(plante.getDate()));
                            plante.getDate().setTime(plante.getDate().getTime() + (BesoinEnEau.FAIBLE.getValeur() + 1 - plante.getBesoinEnEau().getValeur()) * 20000);
                            databaseManager.updatePlante(plante);
                        }
                        if (mesPlantes != null && !mesPlantes.isEmpty()) {
                            notificationManager.notify(43, createNotification(contentText.toString()));
                        }
                    } catch (Exception e) {
                        Logger.getLogger("global").log(Level.WARNING, "Interrupted!", e);
                        Thread.currentThread().interrupt();
                    } finally {
                        if (databaseManager != null)
                            databaseManager.close();
                    }
                    waitFor(10000);
                }*/
                notificationManager.notify(43, createNotification("Nous vidons votre cuve à tel here pour tel raison."));
                new Handler(Looper.getMainLooper()).post(() -> ToastPrinter.printToast(service, "Fin des notifications"));
            }
        };
        thread.start();
        //stopSelf();
        return START_NOT_STICKY;
    }
    private Notification createNotification(String text) {
        Intent notificationIntent = new Intent(service, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(service,
                0, notificationIntent, 0);
        return new NotificationCompat.Builder(service, NOTIFICATION_CHANNEL_ID)
                .setTicker(APPNAME)
                .setContentTitle(APPNAME)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setSmallIcon(R.drawable.logo_green_water_crop)
                .setContentIntent(pendingIntent)
                .build();
    }
    private void waitFor(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Logger.getLogger("global").log(Level.WARNING, "Interrupted!", e);
            Thread.currentThread().interrupt();
        }
    }
    @Override
    public void onDestroy() {
        run = false;
        if (thread != null && !thread.isInterrupted())
            thread.interrupt();
        super.onDestroy();
    }
    @Override
    public boolean stopService(Intent name) {
        run = false;
        if (thread != null && !thread.isInterrupted())
            thread.interrupt();
        return super.stopService(name);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}