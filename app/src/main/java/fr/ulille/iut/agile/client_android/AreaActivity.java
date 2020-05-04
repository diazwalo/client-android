package fr.ulille.iut.agile.client_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Class permetant de saisir les données relative au calcule consomation d'eau
 */
public class AreaActivity extends AppCompatActivity {
    private String urlCompleted = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
    }

    public void onClicCalculer(View view) {
        String dimensionMur = ((EditText)(findViewById(R.id.area_input_dimension_mur))).getText().toString();
        String consoJour = ((EditText)(findViewById(R.id.area_input_conso))).getText().toString();
        String ville = ((EditText)(findViewById(R.id.area_input_ville))).getText().toString();

        if(dimensionMur != null && consoJour != null &&  ville != null) {

            urlCompleted = Connection.constructServerURL(new String[]{"calcul/conso", dimensionMur, consoJour, ville});

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
        }
    }

    private void askServerTank() throws IOException, JSONException {
        JSONObject json = JsonReader.readJsonFromUrl(urlCompleted);

        if(json != null) {
            String jsonConsoVal = json.get("conso").toString();
            if(jsonConsoVal.equals("ville incorrecte") || jsonConsoVal.equals("parametres incorrectes")) {
                ToastPrinter.printToast(this, jsonConsoVal);
            }else {
                double valConso = Double.parseDouble(jsonConsoVal);
                System.out.println("VAL CONSO : " +valConso);
                ((TextView)findViewById(R.id.resultat_area)).setText(getResources().getString(R.string.label_res) + " " + DecimalTruncator.truncateDecimal(valConso, 2).toString() + " Litres");
            }
        }else {
            ((TextView)findViewById(R.id.resultat_tank)).setText(getResources().getString(R.string.label_res));
            ToastPrinter.printToast(this, "Error Connection...");
        }
    }
}
