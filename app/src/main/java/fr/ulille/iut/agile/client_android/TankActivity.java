package fr.ulille.iut.agile.client_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tank);
    }

    public void onClicCalculer(View view) {
        ((TextView)findViewById(R.id.resultat)).setText(getResources().getString(R.string.tank_label_res) + " 42 m3");
    }
}
