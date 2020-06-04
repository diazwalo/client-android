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
}
