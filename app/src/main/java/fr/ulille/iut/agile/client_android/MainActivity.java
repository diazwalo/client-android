package fr.ulille.iut.agile.client_android;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Looper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import static android.os.Environment.getExternalStorageDirectory;

/**
 * Class affichant l'icon de GreenWater redirigeant vers la page de login ou
 * d'accueil
 */
public class MainActivity extends AppCompatActivity {
    String urlCompleted = null;

    private static final Logger LOGGER = Logger.getLogger(TankActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAlreadyLogin();
    }

    private void checkAlreadyLogin() {
        File root = new File(getExternalStorageDirectory(), "IsConnected");
        if (root.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(root))) {
                String login = br.readLine();
                String password = br.readLine();
                br.close();
                this.VerifyLoginPassword(login, password);
            } catch (Exception e) {
                LOGGER.severe(e.getMessage());
            }
        } else {
            ActivitySwitcher.switchActivity(this, LoginActivity.class, true);
        }
    }

    private void VerifyLoginPassword(String login, String password) {
        if (login != null && password != null) {

            urlCompleted = Connection.constructServerURL(new String[] { "authent", login, password });

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    try {
                        askServerLogin();
                    } catch (Exception e) {
                        LOGGER.severe(e.getMessage());
                    }
                    Looper.loop();
                }
            }).start();
        }
    }

    private void askServerLogin() throws IOException, JSONException {
        JSONObject json = JsonReader.readJsonFromUrl(urlCompleted);
        if (json != null && (boolean) json.get("authent")) {
            ActivitySwitcher.switchActivity(this, HomeActivity.class, true);
        } else {
            ActivitySwitcher.switchActivity(this, LoginActivity.class, true);
        }
    }

    /*
     * public void startService() { Intent serviceIntent = new Intent(this,
     * AsyncService.class); ContextCompat.startForegroundService(this,
     * serviceIntent); }
     * 
     * public void stopService() { Intent serviceIntent = new Intent(this,
     * AsyncService.class); stopService(serviceIntent); }
     * 
     * private final void createNotification() { NotificationManager
     * notificationManager = (NotificationManager)
     * getSystemService(Context.NOTIFICATION_SERVICE); final String
     * NOTIFICATION_CHANNEL_ID = "my_channel_id_42"; if (Build.VERSION.SDK_INT >=
     * Build.VERSION_CODES.O) { NotificationChannel notificationChannel = new
     * NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications",
     * NotificationManager.IMPORTANCE_DEFAULT); // Configure the notification
     * channel. notificationChannel.setDescription("Channel description");
     * notificationChannel.enableLights(true);
     * notificationChannel.setLightColor(Color.RED);
     * notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
     * notificationChannel.enableVibration(true);
     * notificationManager.createNotificationChannel(notificationChannel); }
     * NotificationCompat.Builder notificationBuilder = new
     * NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
     * notificationBuilder.setAutoCancel(true)
     * .setDefaults(Notification.DEFAULT_ALL) .setWhen(System.currentTimeMillis())
     * .setSmallIcon(R.drawable.logo_green_water_crop) .setTicker("GreenWater") //
     * .setPriority(Notification.PRIORITY_MAX) .setContentTitle("GreenWater")
     * .setContentText("sah");
     */
    // notificationManager.notify(/*notification id*/42,
    // notificationBuilder.build());
    // }
}