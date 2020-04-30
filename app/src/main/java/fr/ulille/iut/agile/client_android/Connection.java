package fr.ulille.iut.agile.client_android;

import android.content.Context;

public class Connection {
    private final static String url = "http://10.0.2.2:8080/api/v1";
    public static String urlCompleted = null;

    public static String askServer(String[] parameters) {
        urlCompleted = Connection.url;

        for (int idx = 0; idx < parameters.length; idx++) {
            urlCompleted += "/" + parameters[idx];
        }

        return urlCompleted;
    }
}
