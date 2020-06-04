package com.example.unlinkedpairandroidapp;

import android.content.Context;
import android.widget.Toast;

/**
 * Affiche le message (dans le context passé en param) sous la forme de Toast
 */
public class ToastPrinter {

    private ToastPrinter() {
        throw new IllegalStateException("Do not use this constructor");
    }

    public static void printToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
