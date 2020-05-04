package fr.ulille.iut.agile.client_android;

import android.content.Context;
import android.widget.Toast;

/**
 * Affiche le message (dans le context passé en param) sous la forme de Toast
 */
public class ToastPrinter {
    public static void printToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
