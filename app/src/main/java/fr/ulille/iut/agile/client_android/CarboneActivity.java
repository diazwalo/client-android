package fr.ulille.iut.agile.client_android;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Class permetant de saisir les données relative au calcule de l'économie de dioxyde de carbone et de dioxygene
 */
public class CarboneActivity extends AppCompatActivity {

    private String urlCompleted = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbone);
    }

    public void onClicCalculer(View view) {
        String surface = ((EditText)(findViewById(R.id.carbone_input_conso))).getText().toString();

        if(surface != null && !surface.isEmpty()) {

            urlCompleted = Connection.constructServerURL(new String[]{"carbon", surface});

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    try {
                        askServerTank();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    Looper.loop();
                }
            }).start();
        }else {
            ((TextView)findViewById(R.id.resultat_carbone_oxygen)).setText(getResources().getString(R.string.resultat_oxygen));
            ((TextView)findViewById(R.id.resultat_carbone_carbone)).setText(getResources().getString(R.string.resultat_carbone));
            ToastPrinter.printToast(this, getResources().getString(R.string.invalid_parameters));
        }
    }

    private void askServerTank() throws IOException, JSONException {
        JSONObject json = JsonReader.readJsonFromUrl(urlCompleted);

        if(json != null) {

            try {
                String jsonCo2Val = json.get("co2").toString();
                String jsonO2Val = json.get("oxygen").toString();

                double valCo2 = Double.parseDouble(jsonCo2Val);
                double val02 = Double.parseDouble(jsonO2Val);
                ((TextView)findViewById(R.id.resultat_carbone_oxygen)).setText(getResources().getString(R.string.resultat_oxygen) + " " + DecimalTruncator.truncateDecimal(val02, 2).toString() + " kg");
                ((TextView)findViewById(R.id.resultat_carbone_carbone)).setText(getResources().getString(R.string.resultat_carbone) + " " + DecimalTruncator.truncateDecimal(valCo2, 2).toString() + " kg");
            }catch (Exception e) {
                ((TextView)findViewById(R.id.resultat_carbone_oxygen)).setText(getResources().getString(R.string.resultat_oxygen));
                ((TextView)findViewById(R.id.resultat_carbone_carbone)).setText(getResources().getString(R.string.resultat_carbone));
                ToastPrinter.printToast(this, json.get("value").toString());
                e.printStackTrace();
            }
        }else {
            ((TextView)findViewById(R.id.resultat_carbone_oxygen)).setText(getResources().getString(R.string.label_res));
            ToastPrinter.printToast(this, "Error Connection...");
        }
    }
}