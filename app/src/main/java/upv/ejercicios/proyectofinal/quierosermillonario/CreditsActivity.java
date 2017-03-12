package upv.ejercicios.proyectofinal.quierosermillonario;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by migui on 0012.
 */

public class CreditsActivity extends ActionBarActivity {

    private void displayAppVersionInfo() {
        PackageInfo pInfo ;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException ex) {
            return;
        }

        if (pInfo != null) {
            int verCode = pInfo.versionCode;
            String versionName = pInfo.versionName;

            TextView txtAppVersion = ( TextView) findViewById(R.id.txt_app_version);
            txtAppVersion.setText(Integer.valueOf(verCode).toString() + " - " + versionName);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        displayAppVersionInfo();

    }
}
