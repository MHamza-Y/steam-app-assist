package com.example.steamassist.services;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.example.steamassist.pages.ConfirmationPage;


public class SteamAppAccessibilityService extends AccessibilityService {

    public static SteamAppAccessibilityService instance;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {


    }

    public void checkLists(Boolean value) {

        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        ConfirmationPage confirmationPage = new ConfirmationPage(rootNode);
        confirmationPage.itemsCheckAll(value);
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        instance = this;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        instance = null;
        return super.onUnbind(intent);
    }

    @Override
    public void onInterrupt() {

    }
}
