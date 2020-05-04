package fr.ulille.iut.agile.client_android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardActivity extends AppCompatActivity {
    private static Double temp = null;
    private static String description = null;
    private static String icone = null;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        updateGeoAndWeather();
    }

    public void onClicVider(View view) {
        ToastPrinter.printToast(this, "Not implemented yet...");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateGeoAndWeather() {
        GeoLocalisation geoLocalisation = new GeoLocalisation(this);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E dd MMMM yyyy HH:mm", new Locale("fr", "FR"));
        TextView tvDate = (TextView) findViewById(R.id.dashboard_label_date);
        tvDate.setText(simpleDateFormat.format(new Date()));
        TextView tvVille = (TextView) findViewById(R.id.dashboard_label_ville);
        tvVille.setText(geoLocalisation.getCity() + "," + geoLocalisation.getCountry());
        findWeather(geoLocalisation.getCity() + "," + geoLocalisation.getCountry());

        if(icone != null) {
            Picasso.with(this).load("http://openweathermap.org/img/wn/" + icone + "@2x.png").into(((ImageView) findViewById(R.id.dashboard_image_meteo)));
        }
        System.out.println("Icone : " + icone);
    }

    public void findWeather(String paysVille) {
        String urlJsonv2 = "http://api.openweathermap.org/data/2.5/forecast?q="+paysVille+"&appid=9d629e64ec22ad1b004eb79cc0ec3895&cnt=5&mode=json&units=metric&lang=fr"; // Un "cnt" = 3 heures
        //String urlJson = "https://api.openweathermap.org/data/2.5/weather?q="+paysVille+"&appid=9d629e64ec22ad1b004eb79cc0ec3895&units=metric&lang=fr";

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    askMeteo(urlJsonv2);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                Looper.loop();
            }
        }).start();
    }

    private void askMeteo(String urlCompleted) throws IOException, JSONException {
        JSONObject json = JsonReader.readJsonFromUrl(urlCompleted);
        if(json != null) {
            try {
                JSONArray arrayOfDays = json.getJSONArray("list");
                JSONObject dayOne = arrayOfDays.getJSONObject(0);
                JSONObject meteo = dayOne.getJSONObject("main");
                DashboardActivity.temp = meteo.getDouble("temp");
                JSONArray array = dayOne.getJSONArray("weather");
                JSONObject object = array.getJSONObject(0);
                DashboardActivity.description = object.getString("description");
                DashboardActivity.icone = object.getString("icon");
                //tvTemperature.setText("Température: " + Math.round(temp) + "°C");
                //tvMeteo.setText("Météo: " + description);
            }catch(JSONException e) {
                Logger.getLogger("global").log(Level.WARNING, e.getMessage());
            }
        }else {
            ToastPrinter.printToast(this, ("Error Network Connection"));
        }
    }
}
