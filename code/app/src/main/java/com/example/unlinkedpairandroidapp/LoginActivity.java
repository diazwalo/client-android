package com.example.unlinkedpairandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClicSubmit(View view) {
        ActivitySwitcher.switchActivity(this, HomeActivity.class, true);
    }

    public void onClicSignIn(View view) {
        ActivitySwitcher.switchActivity(this, SignInActivity.class, false);
    }

    public void onClicForgetPassword(View view) {
        //Envoie d'un mail avec le nouveau mdp
    }
}
