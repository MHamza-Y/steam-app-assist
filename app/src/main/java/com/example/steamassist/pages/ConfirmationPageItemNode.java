package com.example.steamassist.pages;

import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import java.lang.reflect.Method;
import java.util.Collection;

public class ConfirmationPageItemNode {
    public AccessibilityNodeInfo item;
    AccessibilityNodeInfo itemTitleNode;
    AccessibilityNodeInfo itemPriceNode;
    AccessibilityNodeInfo itemImageNode;
    AccessibilityNodeInfo itemCheckNode;

    public ConfirmationPageItemNode(AccessibilityNodeInfo itemNode) {
        item = itemNode;
        itemImageNode = itemNode.getChild(0).getChild(0);
        itemCheckNode = itemNode.getChild(1).getChild(0);
        itemTitleNode = itemNode.getChild(2);
        itemPriceNode = itemNode.getChild(3);
    }
    public AccessibilityNodeInfo getItemImageNode() {
        return itemImageNode;
    }

    public String getItemPrice() {
        return (String) itemPriceNode.getText();
    }

    public String getItemTitle() {
        return (String) itemTitleNode.getText();
    }

    public AccessibilityNodeInfo getItemCheckNode() {
        return itemCheckNode;
    }
    public void checkNode() {
        if(itemCheckNode==null) {
            Log.i("Check Button","no button found");
        }
        else if(itemCheckNode.isChecked()==false) {
            Log.i("Check Button","check");
            itemCheckNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }
    public void uncheckNode() {
        if(itemCheckNode==null) {
            Log.i("Check Button","no button found");
        }
        else if(itemCheckNode.isChecked()==true) {
            Log.i("Check Button","uncheck");
            itemCheckNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }
}
