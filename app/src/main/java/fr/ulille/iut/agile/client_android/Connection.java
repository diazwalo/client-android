package fr.ulille.iut.agile.client_android;

public class Connection {
    private static final String URL = "https://groupe4.azae.eu/api/v1/";

    private Connection() {
        throw new IllegalStateException("Do not use this constructor");
    }

    public static String constructServerURL(String[] parameters) {
        StringBuilder urlCompletedBuilder = new StringBuilder(Connection.URL);

        for (int idx = 0; idx < parameters.length; idx++) {
            urlCompletedBuilder.append("/").append(parameters[idx]);
        }

        return urlCompletedBuilder.toString();
    }
}
