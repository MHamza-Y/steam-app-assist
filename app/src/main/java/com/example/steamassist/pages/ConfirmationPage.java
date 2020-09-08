package com.example.steamassist.pages;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;
import com.example.steamassist.services.BubbleService;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ConfirmationPage {
    List<ConfirmationPageItemNode> items;
    String ITEMS_NODE_START_STRING = "conf";
    AccessibilityNodeInfo rootNode;

    public ConfirmationPage(AccessibilityNodeInfo rootNode) {
        this.rootNode = rootNode;
        items = new ArrayList<>();
        try {
            extractItemsRootNodes(rootNode)
                    .forEach(item -> {
                                if (item != null) {
                                    items.add(new ConfirmationPageItemNode(item));
                                }
                            }
                    );
        } catch (Exception e) {
            Log.e("Confirmation Items", "Not found");
            noItemsFoundToast();
        }
    }

    private List<AccessibilityNodeInfo> extractItemsRootNodes(AccessibilityNodeInfo currNode) {
        List<AccessibilityNodeInfo> itemsRootNodes = new ArrayList<>();
        if (currNode != null) {
            if (currNode.getViewIdResourceName() != null && currNode.getViewIdResourceName().startsWith(ITEMS_NODE_START_STRING)) {
                Log.i("item node", "found");
                Log.i("Node", currNode.toString());
                itemsRootNodes.add(currNode);
            } else {
                for (int i = 0; i < currNode.getChildCount(); i++) {
                    itemsRootNodes.addAll(extractItemsRootNodes(currNode.getChild(i)));
                }
            }
        }
        return itemsRootNodes;
    }

    public List<ConfirmationPageItemNode> getItems() {
        return items;
    }

    public void itemsCheckAll(Boolean value) {
        items.forEach(item -> {
            if (value) {
                item.checkNode();
            } else {
                item.uncheckNode();
            }
        });
    }

    /*void printChildNodes(AccessibilityNodeInfo nodeInfo,int l) {
        Log.i(""+l,nodeInfo.toString());
        for(int i=0;i<nodeInfo.getChildCount();i++) {

            printChildNodes(nodeInfo.getChild(i),l+1);
        }
    }*/
    public JSONArray groupItems() {
        JSONArray itemGroups = ConfirmationPageItemNode.groupNodes(items);
        if(itemGroups.length()<1) {
            noItemsFoundToast();
        }
        return itemGroups;
    }
    private void noItemsFoundToast() {
        Context context = BubbleService.instance.getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, "No confirmations found", duration);
        toast.show();

    }
}
