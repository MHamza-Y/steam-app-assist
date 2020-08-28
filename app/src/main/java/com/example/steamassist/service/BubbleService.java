package com.example.steamassist.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.example.steamassist.R;

public class BubbleService extends Service implements View.OnClickListener {

    private WindowManager mWindowManager;
    private View mFloatingView;

    @Override
    public void onCreate() {
        super.onCreate();

        mFloatingView = LayoutInflater.from(this).inflate(R.layout.overlay_layout, null);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 100;
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);

        ImageButton checkAll  = mFloatingView.findViewById(R.id.check_all);
        ImageButton close = mFloatingView.findViewById(R.id.buttonClose);
        checkAll.setOnClickListener(this);
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
        if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_all:
                //switching views
                checkAll();
                break;

            case R.id.buttonClose:
                //closing the widget
                stopSelf();
                break;
        }

    }
    private void checkAll() {

        AccessibilityManager manager = (AccessibilityManager)this.getSystemService(getApplicationContext().ACCESSIBILITY_SERVICE);

        if(manager.isEnabled()){
            AccessibilityEvent event = AccessibilityEvent.obtain(AccessibilityEvent.TYPE_ANNOUNCEMENT);
            //event.setEventType(AccessibilityEvent.TYPE_ANNOUNCEMENT);
            event.setEnabled(true);
            event.setClassName(getClass().getName());
            event.setPackageName("com.valvesoftware.android.steam.community");
            event.setContentDescription("checkAll:true");
            event.getText().add("Check All");
            manager.sendAccessibilityEvent(event);
            Log.i("button clicked","yes");

        }
    }


}
