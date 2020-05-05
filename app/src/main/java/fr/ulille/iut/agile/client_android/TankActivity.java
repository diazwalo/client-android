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
import java.util.logging.Logger;

/**
 * Class permetant de saisir les données relative au calcule de la taille de la cuve
 */
public class TankActivity extends AppCompatActivity {
    private String urlCompleted = null;
    private static final Logger LOGGER = Logger.getLogger(TankActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tank);
    }

    public void onClicCalculer(View view) {
        String consoJour = ((EditText)(findViewById(R.id.tank_input_conso))).getText().toString();
        String ville = ((EditText)(findViewById(R.id.tank_input_ville))).getText().toString();

        if(consoJour != null &&  ville != null) {

            urlCompleted = Connection.constructServerURL(new String[]{"calcul/stockage", consoJour, ville});

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    try {
                        askServerTank();
                    }catch (Exception e) {
                        LOGGER.severe(e.getMessage());
                    }
                    Looper.loop();
                }
            }).start();
        }
    }

    private void askServerTank() throws IOException, JSONException {
        JSONObject json = JsonReader.readJsonFromUrl(urlCompleted);

        if(json != null) {
            String jsonStokageVal = json.get("stockage").toString();
            if(jsonStokageVal.equals("ville incorrecte") || jsonStokageVal.equals("parametres incorrectes")) {
                ((TextView)findViewById(R.id.resultat_tank)).setText(getResources().getString(R.string.label_res));
                ToastPrinter.printToast(this, jsonStokageVal);
            }else {
                double valStockage = Double.parseDouble(jsonStokageVal);
                ((TextView)findViewById(R.id.resultat_tank)).setText(getResources().getString(R.string.label_res) + " " + DecimalTruncator.truncateDecimal(valStockage, 2).toString() + " m3");
            }
        }else {
            ((TextView)findViewById(R.id.resultat_tank)).setText(getResources().getString(R.string.label_res));
            ToastPrinter.printToast(this, "Error Connection...");
        }
    }
}
