package fr.ulille.iut.agile.client_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

/**
 * Class qui sert à rediriger vers d'autre Activity lorsque l'utilisateur appuie sur un bouton
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void onClickTank(View view) {
        ActivitySwitcher.switchActivity(this, TankActivity.class, false);
    }

    public void onClickArea(View view) {
        ActivitySwitcher.switchActivity(this, AreaActivity.class, false);
    }

    public void onClickDashboard(View view) {
        ActivitySwitcher.switchActivity(this, DashboardActivity.class, false);
    }
}
