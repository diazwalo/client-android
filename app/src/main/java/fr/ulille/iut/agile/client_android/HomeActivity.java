package fr.ulille.iut.agile.client_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.io.File;

import static android.os.Environment.getExternalStorageDirectory;

/**
 * Class qui sert à rediriger vers d'autre Activity lorsque l'utilisateur appuie sur un bouton
 * Elle contient aussi un boutton logout pour se deconnecter
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

    public void onClickLogout(View view) {
        File root = new File(getExternalStorageDirectory(), "IsConnected");
        System.out.println("root.delet() : " +root.delete());
        ActivitySwitcher.switchActivity(this, LoginActivity.class, true);
    }

    public void onClickTank(View view) {
        ActivitySwitcher.switchActivity(this, TankActivity.class, false);
    }

    public void onClickArea(View view) {
        ActivitySwitcher.switchActivity(this, AreaActivity.class, false);
    }

    public void onClickCarbone(View view) {
        ActivitySwitcher.switchActivity(this, CarboneActivity.class, false);
    }

    public void onClickDashboard(View view) {
        ActivitySwitcher.switchActivity(this, DashboardActivity.class, false);
    }
}
