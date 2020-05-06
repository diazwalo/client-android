package fr.ulille.iut.agile.client_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static android.os.Environment.getExternalStorageDirectory;

/**
 * Class verifiant la saisie du login et du mot de passe de l'utilisateur Soit
 * il est redirigé vers l'accueil Soit on lui affiche qu'il n'a pas reussi à
 * s'authentifier
 */
public class LoginActivity extends AppCompatActivity {
    private String urlCompleted = null;

    private TextView tv_login = null;
    private TextView tv_password = null;
    private CheckBox cb_RememberMe = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        this.tv_login = (EditText) (findViewById(R.id.login));
        this.tv_password = (EditText) (findViewById(R.id.password));
        this.cb_RememberMe = (CheckBox) (findViewById(R.id.rememberMe));
    }

    public void onClicSubmit(View view) {
        String login = (this.tv_login).getText().toString();
        String password = (this.tv_password).getText().toString();

        this.VerifyLoginPassword(login, password);
    }

    private void VerifyLoginPassword(String login, String password) {
        if (login != null && password != null) {

            urlCompleted = Connection.constructServerURL(new String[] { "authent", login, password });

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    try {
                        askServerLogin();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Looper.loop();
                }
            }).start();
        }
    }

    private void askServerLogin() throws IOException, JSONException {
        JSONObject json = JsonReader.readJsonFromUrl(urlCompleted);
        if (json != null && (boolean) json.get("authent")) {
            if (this.cb_RememberMe != null && this.cb_RememberMe.isChecked()) {
                rememberMe();
            }
            ActivitySwitcher.switchActivity(this, HomeActivity.class, true);
        } else {
            ToastPrinter.printToast(this, getResources().getString(R.string.login_failed));
        }
    }

    private void rememberMe() {
        File root = new File(getExternalStorageDirectory(), "IsConnected");
        if (!root.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(root))) {
                bw.write(tv_login.getText().toString());
                bw.newLine();
                bw.write(tv_password.getText().toString());
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
