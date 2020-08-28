package com.example.steamassist.service;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityEventSource;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

public class SteamAppAccessibilityService extends AccessibilityService {

    private void exploreChild(AccessibilityNodeInfo info,int depth) {
        Log.d("A"+depth, ""+info.getClassName().toString());
        if(info.getClassName().equals("android.widget.CheckBox")) {
            info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
        for (int i=0;i<info.getChildCount();i++) {
            exploreChild(info.getChild(i),depth+1);

        }
    }
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Log.i("event",accessibilityEvent.toString());
        //Log.i("id",accessibilityEvent.getSource().);
        AccessibilityNodeInfoCompat rootNodeInfo = AccessibilityNodeInfoCompat.wrap(getRootInActiveWindow());
        AccessibilityNodeInfo nodeInfo = accessibilityEvent.getSource();

        if(accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_ANNOUNCEMENT) {
            Log.i("event text", (String) accessibilityEvent.getContentDescription());
            AccessibilityNodeInfo info=getRootInActiveWindow();

            exploreChild(info,0);





            //Log.d("A1",accessibilityEvent.getSource().toString());
            //accessibilityEvent.getSource().getChild(0).getChild(0).getChild().performAction(AccessibilityNodeInfo.ACTION_CLICK);
            /*for (int i=0;i<accessibilityEvent.getSource().getChild(0).getChild(0).getChildCount();i++) {
                Log.d("A2", ""+accessibilityEvent.getSource().getChild(0).getChild(0).getChild(i).toString());

            }*/
        }
        //Log.i( "A1",rootNodeInfo.getViewIdResourceName());

        //Log.i("accessibility event","event occurred");
        //List<AccessibilityNodeInfoCompat> nodeInfoCompatList = rootNodeInfo.
    }


    @Override
    public void onInterrupt() {

    }
}
