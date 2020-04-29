package fr.ulille.iut.agile.client_android;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Connection {
    private final static String url = "http://10.0.2.2:8080/api/v1";
    public static String urlCompleted = null;

    public static String askServer(Context context, String[] parameters) {
        urlCompleted = Connection.url;

        for (int idx = 0; idx < parameters.length; idx++) {
            urlCompleted += "/" + parameters[idx];
        }

        return urlCompleted;
    }
}
