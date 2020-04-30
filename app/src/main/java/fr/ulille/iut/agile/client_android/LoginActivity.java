package fr.ulille.iut.agile.client_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    private String urlCompleted = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClicSubmit(View view) {
        String login = ((EditText)(findViewById(R.id.login))).getText().toString();
        String password = ((EditText)(findViewById(R.id.password))).getText().toString();

        if(login != null &&  password != null) {

            urlCompleted = Connection.askServer(this, new String[]{"authent", login, password});

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    try {
                        askServerLogin();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    Looper.loop();
                }
            }).start();
        }
    }

    private void askServerLogin() throws IOException, JSONException {
        JSONObject json = JsonReader.readJsonFromUrl(urlCompleted);
        if(json != null && (boolean)json.get("authent")) {
            Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }else {
            wrongLoginAction("Invalid Login or Password");
        }
    }

    protected void wrongLoginAction(String message) {
        Toast.makeText(this, "Invalid Login or Password", Toast.LENGTH_SHORT).show();
    }
}
