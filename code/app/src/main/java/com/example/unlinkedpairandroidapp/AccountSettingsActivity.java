package com.example.unlinkedpairandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class AccountSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
    }

    public void onClickResetPassword(View view) {
        ActivitySwitcher.switchActivity(this, ResetPasswordActivity.class, false);
    }

    public void onClicCloseAccount(View view) {
        ActivitySwitcher.switchActivity(this, CloseAccountActivity.class, false);
    }
}
