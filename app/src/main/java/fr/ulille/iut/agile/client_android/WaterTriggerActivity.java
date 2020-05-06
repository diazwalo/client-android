package fr.ulille.iut.agile.client_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * Class qui permet à l'utilisateur de choisir plus finement de combien il veut vider la cuve
 */
public class WaterTriggerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_trigger);
    }
}
