package com.example.steamassist.pages;

import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfirmationPage {
    List<ConfirmationPageItemNode> items;
    String ITEMS_NODE_START_STRING = "conf";
    AccessibilityNodeInfo rootNode;

    public ConfirmationPage(AccessibilityNodeInfo rootNode) {
        this.rootNode = rootNode;
        items = new ArrayList<>();
        extractItemsRootNodes(rootNode)
                .forEach(item -> items.add(new ConfirmationPageItemNode(item))
                );
    }

    private List<AccessibilityNodeInfo> extractItemsRootNodes(AccessibilityNodeInfo currNode) {
        List<AccessibilityNodeInfo> itemsRootNodes = new ArrayList<>();
        if(currNode!=null) {
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
            if(value) {
                item.checkNode();
            } else {
                item.uncheckNode();
            }
        });
    }
    void printChildNodes(AccessibilityNodeInfo nodeInfo,int l) {
        Log.i(""+l,nodeInfo.toString());
        for(int i=0;i<nodeInfo.getChildCount();i++) {

            printChildNodes(nodeInfo.getChild(i),l+1);
        }
    }

}
