package fr.ulille.iut.agile.client_android;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

/**
 * Class qui affiche un dialogue d'alerte
 */
public class DialogAlertor {
    public static void makeDialogAlertForTankFilling(Context context) {
        int delta = DashboardActivity.percent_tank_filling;
        if(delta > 25) {
            delta = 25;
        }
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        adb.setTitle("Confirmation");
        adb.setMessage("Vider la cuve de  " + delta + " % ?");
        adb.setIcon(R.drawable.logo_green_water_crop);
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                DashboardActivity.percent_tank_filling -= 25;
                if(DashboardActivity.percent_tank_filling < 0) {
                    DashboardActivity.percent_tank_filling = 0;
                }
                DashboardActivity.initPercentTankFilling();
            }
        });
        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ToastPrinter.printToast(context, "Opération annulée...");
            }
        });
        adb.show();
    }
}
