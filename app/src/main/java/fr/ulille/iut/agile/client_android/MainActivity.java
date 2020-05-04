package fr.ulille.iut.agile.client_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Class affichant l'icon de GreenWater pendant X secondes
 */
public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                ActivitySwitcher.switchActivity(MainActivity.this, LoginActivity.class, true);
            }
        }, SPLASH_TIME_OUT);
    }
}