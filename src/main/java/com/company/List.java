package com.company;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by clara on 12/8/16.
 */
public class List {
    //for each list there will be an arraylist of items
    ArrayList<Item> items;
    String name;
    //determines if the list is the main or sublist(s)
    boolean isMainList;
    //next item priority
    int newItemPriority;
    //List constructor,
    List(String name, boolean isMainList) {
        this.name = name;
        items = new ArrayList<Item>();
        newItemPriority = 1;
        this.isMainList = isMainList;
    }

    void add(Item item) {
        item.priority = newItemPriority;
        items.add(item);
        newItemPriority++;
    }
    /*method to change priority, determines whether the current priority is higher or lower
    * than the new priority and that determines how to shift the list*/
    void changePriority(Item item, int newPriority) {
        int currentPriority = item.getPriority();
        item.setPriority(newPriority);
        if(newPriority < currentPriority) {

            for (int i = newPriority - 1; i < currentPriority - 1; i++) {
                items.get(i).adjustPriority(1);

            }
        }
        if(newPriority > currentPriority) {
            for (int i = currentPriority; i < newPriority; i++) {
                items.get(i).adjustPriority(-1);
            }
        }
        ArrayList<Item> newItemList = new ArrayList<Item>();
        for(int x = 0; x < items.size(); x++){
            newItemList.add(items.get(x));
        }
        Collections.sort(newItemList);

        items.clear();

        for(Item i: newItemList){
            items.add(i);
        }
        Collections.sort(items);
    }

    ArrayList<Item> getAllItems() {
        return items;
    }

    int getMaxPriority()  {
        return newItemPriority - 1;  //so if there's 4 items, newItemPriority will be 5
    }

}
