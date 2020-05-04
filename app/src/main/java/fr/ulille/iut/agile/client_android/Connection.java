package fr.ulille.iut.agile.client_android;

public class Connection {
    private final static String url = "https://groupe4.azae.eu/api/v1/";
    public static String urlCompleted = null;

    public static String constructServerURL(String[] parameters) {
        urlCompleted = Connection.url;

        for (int idx = 0; idx < parameters.length; idx++) {
            urlCompleted += "/" + parameters[idx];
        }

        return urlCompleted;
    }
}
