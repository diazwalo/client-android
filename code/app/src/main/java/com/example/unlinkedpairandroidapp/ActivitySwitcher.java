package com.example.unlinkedpairandroidapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Class permetant de changer d'activity en terminant ou non l'activité précedante
 */
public class ActivitySwitcher {

    private ActivitySwitcher() {
        throw new IllegalStateException("Do not use this constructor");
    }

    public static void switchActivity(Context context, Class classTargeted, boolean finishPreviousActivity) {
        Intent intent=new Intent(context, classTargeted);
        context.startActivity(intent);
        if(finishPreviousActivity) {
            ((Activity)context).finish();
        }
    }
}
