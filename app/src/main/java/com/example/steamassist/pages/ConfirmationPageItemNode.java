package com.example.steamassist.pages;

import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

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
        return ""+itemPriceNode.getText();
    }

    public String getItemTitle() {
        return ""+itemTitleNode.getText();
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

    public static JSONArray groupNodes(List<ConfirmationPageItemNode> nodes) {
        JSONArray nodeGroups = new JSONArray();
        for (ConfirmationPageItemNode node:nodes) {
            try {
                nodeGroups = addToArr(nodeGroups,node);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return nodeGroups;
    }

    static JSONArray addToArr(JSONArray nodeGroups,ConfirmationPageItemNode node) throws JSONException {
        for(int i=0;i<nodeGroups.length();i++) {
            JSONObject group = (JSONObject) nodeGroups.get(i);

            ConfirmationPageItemNode gNode = (ConfirmationPageItemNode) group.get("node");
            Boolean isMatch = gNode.getItemTitle().equals(node.getItemTitle())&&gNode.getItemPrice().equals(node.getItemPrice());
            if(isMatch) {
                group.put("quantity",(Integer) group.get("quantity")+1);
                return nodeGroups;
            }
        }
        JSONObject newGroup = new JSONObject();
        newGroup.put("node",node);
        newGroup.put("quantity",1);
        nodeGroups.put(newGroup);
        return nodeGroups;
    }
}
