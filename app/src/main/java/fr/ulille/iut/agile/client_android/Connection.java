package fr.ulille.iut.agile.client_android;

public class Connection {
    private static final String URL = "https://groupe4.azae.eu/api/v1/";

    private Connection() {
        throw new IllegalStateException("Do not use this constructor");
    }

    public static String constructServerURL(String[] parameters) {
        String urlCompleted = Connection.URL;

        for (int idx = 0; idx < parameters.length; idx++) {
            urlCompleted += "/" + parameters[idx];
        }

        return urlCompleted;
    }
}
