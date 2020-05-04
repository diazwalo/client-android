package fr.ulille.iut.agile.client_android;

public class Connection {
    private final static String URL = "https://groupe4.azae.eu/api/v1/";

    public static String constructServerURL(String[] parameters) {
        String urlCompleted = Connection.URL;

        for (int idx = 0; idx < parameters.length; idx++) {
            urlCompleted += "/" + parameters[idx];
        }

        return urlCompleted;
    }
}
