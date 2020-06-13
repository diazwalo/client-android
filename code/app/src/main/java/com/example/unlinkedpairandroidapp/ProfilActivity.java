package com.example.unlinkedpairandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class ProfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
    }

    public void onClicccountSettings(View view) {
        ActivitySwitcher.switchActivity(this, AccountSettingsActivity.class, false);
    }
}
