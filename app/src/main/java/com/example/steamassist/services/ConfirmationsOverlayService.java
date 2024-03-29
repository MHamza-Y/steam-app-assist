package com.example.steamassist.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ToggleButton;
import androidx.annotation.Nullable;
import com.example.steamassist.R;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ConfirmationsOverlayService extends Service implements View.OnClickListener {
    public static ConfirmationsOverlayService instance;
    private WindowManager mWindowManager;
    private View mOverlayView;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mOverlayView = LayoutInflater.from(this).inflate(R.layout.overlay_layout, null);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 200;
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mOverlayView, params);

        ToggleButton checkAll  = mOverlayView.findViewById(R.id.check_all);
        ImageButton close = mOverlayView.findViewById(R.id.buttonClose);
        ImageButton groupItems = mOverlayView.findViewById(R.id.group_items);
        checkAll.setOnClickListener(this);
        groupItems.setOnClickListener(this);
        close.setOnClickListener(this);



    }
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        instance = null;
        if (mOverlayView != null) mWindowManager.removeView(mOverlayView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_all:
                //switching views
                checkAll(v);
                break;
            case R.id.group_items:
                groupNodes();
                break;
            case R.id.buttonClose:
                //closing the widget
                stopSelf();
                break;
        }

    }
    private void checkAll(View v) {
        ToggleButton checkAll = v.findViewById(R.id.check_all);
        if(SteamAppAccessibilityService.instance!=null) {
            SteamAppAccessibilityService.instance.checkLists(checkAll.isChecked());
        }
    }

    private void groupNodes() {
        JSONArray itemGroups = SteamAppAccessibilityService.instance.group_items();
        List<View> itemGroupViews = new ArrayList<>();
        for(int i=0;i<itemGroups.length();i++) {

        }
    }

}
