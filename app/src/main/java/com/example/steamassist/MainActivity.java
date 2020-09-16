package com.example.steamassist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.steamassist.services.ConfirmationsOverlayService;
import com.example.steamassist.services.SteamAppAccessibilityService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enablePermissions();
        setContentView(R.layout.activity_main);


    }

    public void clickMe(View view) {

        Log.i("Overlay Enable Button", "Starting Overlay Service");
        startService(new Intent(MainActivity.this, ConfirmationsOverlayService.class));
        finish();

    }


    public void enablePermissions() {
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 0);
        }
        if (!isAccessServiceEnabled(getApplicationContext(), SteamAppAccessibilityService.class)) {
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));

        }
    }

    public boolean isAccessServiceEnabled(Context context, Class accessibilityServiceClass) {
        String prefString = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);

        return prefString != null && prefString.contains(context.getPackageName() + "/" + accessibilityServiceClass.getName());
    }
}