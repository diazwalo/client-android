package fr.ulille.iut.agile.client_android;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeoLocalisation {
    protected static final String[] NEEDED_PERMISSION = new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE};
    private Location gpsLoc = null;
    private Location networkLoc = null;
    private Location finalLoc = null;
    private double latitude = 0;
    private double longitude = 0;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String knownName;
    public GeoLocalisation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(context, NEEDED_PERMISSION[0]) == PackageManager.PERMISSION_GRANTED
                && (ContextCompat.checkSelfPermission(context, NEEDED_PERMISSION[1]) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, NEEDED_PERMISSION[2]) == PackageManager.PERMISSION_GRANTED)) {
            try {
                gpsLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                networkLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            } catch (Exception e) {
                Logger.getLogger("global").log(Level.WARNING, e.getMessage());
            }
        }
        if (gpsLoc != null) {
            finalLoc = gpsLoc;
            latitude = finalLoc.getLatitude();
            longitude = finalLoc.getLongitude();
        } else if (networkLoc != null) {
            finalLoc = networkLoc;
            latitude = finalLoc.getLatitude();
            longitude = finalLoc.getLongitude();
        } else {
            // Lille,France
            latitude = 50.6333;
            longitude = 3.0667;
        }
        try {
            Geocoder geocoder = new Geocoder(context.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                address = addresses.get(0).getAddressLine(0);
                city = addresses.get(0).getLocality();
                state = addresses.get(0).getAdminArea();
                country = addresses.get(0).getCountryName();
                postalCode = addresses.get(0).getPostalCode();
                knownName = addresses.get(0).getFeatureName();
            }
        } catch (IOException e) {
            Logger.getLogger("global").log(Level.WARNING, e.getMessage());
        }
    }
    public Location getGpsLoc() {
        return gpsLoc;
    }
    public Location getNetworkLoc() {
        return networkLoc;
    }
    public Location getFinalLoc() {
        return finalLoc;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public String getAddress() {
        return address;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public String getCountry() {
        return country;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public String getKnownName() {
        return knownName;
    }
}