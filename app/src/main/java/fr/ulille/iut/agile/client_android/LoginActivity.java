package fr.ulille.iut.agile.client_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClicSubmit(View view) {
        String login = ((EditText)(findViewById(R.id.login))).getText().toString();
        String password = ((EditText)(findViewById(R.id.password))).getText().toString();

        if(login != null &&  password != null) {
            Connection.askServer(this, new String[]{"authent", login, password});

            wrongLoginAction(Connection.responseString);
        }

        /*Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();*/
    }

    protected void wrongLoginAction(String message) {
        Context context = getApplicationContext();
        CharSequence text = ""+R.string.login_failed;
        if(message != null) {
            text = "" + message;
        }
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }
}
