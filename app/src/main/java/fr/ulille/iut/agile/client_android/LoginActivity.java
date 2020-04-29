package fr.ulille.iut.agile.client_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private String urlCompleted = null;
    private boolean authentSuccess = false;

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
                    try {
                        JSONObject json = JsonReader.readJsonFromUrl(urlCompleted);
                        if(json != null && (boolean)json.get("authent")) {
                            authentSuccess = true;
                            Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            wrongLoginAction(null);
        }
    }

    protected void wrongLoginAction(String message) {
        Toast.makeText(this, "Invalid Login or Password", Toast.LENGTH_SHORT).show();
    }
}
