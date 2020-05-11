package fr.ulille.iut.agile.client_android;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Class Affichant les information sur la meteo, la geolocalisation
 * Elle possede un bouton qui renvoit vers une page pour vider la cuve plus finnement
 */
public class DashboardActivity extends AppCompatActivity implements LocationListener {
    private Location location = null;
    private static final int LOCATION_PERMISSION_CODE = 42;
    private LocationManager locationManager;
    private String provider;

    private String finalAddress = null;
    private Double temp = null;
    private String description = null;

    private ImageView imageMeteo = null;
    private TextView addressField;
    private TextView tvDate = null;
    private ProgressBar pbBackGraph = null;
    private static ProgressBar pbForeGraph = null;
    private static TextView tvLabelGraph = null;

    public static int percent_tank_filling= (int)(Math.random() * ((100 - 0) + 1));

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        this.addressField = findViewById(R.id.dashboard_label_ville);
        this.tvDate = (TextView) findViewById(R.id.dashboard_label_date);
        this.imageMeteo = findViewById(R.id.dashboard_image_meteo);
        this.pbBackGraph = (ProgressBar)findViewById(R.id.dashboard_progressbar_background);
        this.pbForeGraph = (ProgressBar)findViewById(R.id.dashboard_progressbar_foreground);
        this.tvLabelGraph = (TextView)findViewById(R.id.dashboard_label_graph);
        this.initPercentTankFilling();

        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_CODE);
    }

    public static void initPercentTankFilling() {
        DashboardActivity.tvLabelGraph.setText(DashboardActivity.percent_tank_filling + " %");
        DashboardActivity.pbForeGraph.setProgress(DashboardActivity.percent_tank_filling);
    }

    private boolean initLocation() {
        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        this.provider = locationManager.getBestProvider(criteria, false);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            onLocationChanged(location);
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //TODO : A REVOIR
        if(provider != null) {
            locationManager.requestLocationUpdates(provider, 400, 1, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        // You had this as int. It is advised to have Lat/Loing as double.
        //TODO : A REVOIR
        if(location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();

            Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
            StringBuilder builder = new StringBuilder();
            try {
                List<Address> address = geoCoder.getFromLocation(lat, lng, 1);

                String addressStr = address.get(0).getLocality();
                builder.append(addressStr);

                finalAddress = builder.toString(); // This is the complete address.

                addressField.setText(finalAddress); // This will display the final address.

            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateGeoAndWeather() {
        findWeather();
    }

    public void findWeather() {
        String urlJsonv2 = "http://api.openweathermap.org/data/2.5/forecast?q=" + finalAddress
                + "&appid=9d629e64ec22ad1b004eb79cc0ec3895&cnt=5&mode=json&units=metric&lang=fr"; // Un "cnt" = 3 heures

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    askMeteo(urlJsonv2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Looper.loop();
            }
        }).start();

    }

    private void askMeteo(String urlCompleted) throws IOException, JSONException {
        JSONObject json = JsonReader.readJsonFromUrl(urlCompleted);
        if (json != null) {
            try {
                JSONArray arrayOfDays = json.getJSONArray("list");
                JSONObject dayOne = arrayOfDays.getJSONObject(0);
                JSONObject meteo = dayOne.getJSONObject("main");

                // Non utilisé
                this.temp = meteo.getDouble("temp");
                JSONArray array = dayOne.getJSONArray("weather");
                JSONObject object = array.getJSONObject(0);

                // Non utilisé
                this.description = object.getString("description");

                String icone = object.getString("icon");

                Handler uiHandler = new Handler(Looper.getMainLooper());
                uiHandler.post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void run() {
                        Picasso.with(DashboardActivity.this)
                                .load("http://openweathermap.org/img/wn/" + icone + "@2x.png").into(imageMeteo);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E dd MMMM yyyy",
                                new Locale("fr", "FR"));
                        tvDate.setText(simpleDateFormat.format(new Date()));
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            ToastPrinter.printToast(this, ("Error Network Connection"));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {permission}, requestCode);
        }
        else {
            //ToastPrinter.printToast(this, "Localisation déjà accordée");
            initLocation();
            updateGeoAndWeather();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //ToastPrinter.printToast(this, "Localisation accordée");
                initLocation();
                updateGeoAndWeather();
            }
            else {
                //ToastPrinter.printToast(this, "Localisation refusée");
            }
        }
    }

    public void onClicVider(View view) {
        //ToastPrinter.printToast(this, "Not implemented yet...");
        /*DashboardActivity.percent_tank_filling -= 25;
        if(DashboardActivity.percent_tank_filling < 0) {
            DashboardActivity.percent_tank_filling = 0;
        }
        this.initPercentTankFilling();*/
        if(DashboardActivity.percent_tank_filling <= 0 ) {
            ToastPrinter.printToast(this, "Cuve déjà vide...");
        }else {
            DialogAlertor.makeDialogAlertForTankFilling(this);
        }
    }
}
