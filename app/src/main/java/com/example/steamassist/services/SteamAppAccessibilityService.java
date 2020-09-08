package com.example.steamassist.services;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.example.steamassist.pages.ConfirmationPage;
import org.json.JSONArray;


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
    public JSONArray group_items() {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        ConfirmationPage confirmationPage = new ConfirmationPage(rootNode);
        return confirmationPage.groupItems();
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
