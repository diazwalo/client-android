package fr.ulille.iut.agile.client_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Class verifiant la saisie du login et du mot de passe de l'utilisateur
 * Soit il est redirigé vers l'accueil
 * Soit on lui affiche qu'il n'a pas reussi à s'authentifier
 */
public class LoginActivity extends AppCompatActivity {
    private String urlCompleted = null;
    private static final Logger LOGGER = Logger.getLogger(LoginActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClicSubmit(View view) {
        String login = ((EditText)(findViewById(R.id.login))).getText().toString();
        String password = ((EditText)(findViewById(R.id.password))).getText().toString();

        if(login != null &&  password != null) {

            urlCompleted = Connection.constructServerURL(new String[]{"authent", login, password});

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    try {
                        askServerLogin();
                    }catch (Exception e) {
                        LOGGER.severe(e.getMessage());
                    }
                    Looper.loop();
                }
            }).start();
        }
    }

    private void askServerLogin() throws IOException, JSONException {
        JSONObject json = JsonReader.readJsonFromUrl(urlCompleted);
        if(json != null && (boolean)json.get("authent")) {
            ActivitySwitcher.switchActivity(this, HomeActivity.class, true);
        }else {
            ToastPrinter.printToast(this, getResources().getString(R.string.login_failed));
        }
    }
}
