package com.company;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by clara on 12/8/16.
 */
public class List {

    ArrayList<Item> items;
    String name;

    boolean isMainList;

    int newItemPriority;

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

    void changePriority(Item item, int newPriority) {
        //todo - find item in the list of items
        //todo change its priority to newPriority
        //todo change any other items to the correct priority, to shift them up or down, as needed

        //sort list
        Collections.sort(items);
    }

    ArrayList<Item> getAllItems() {
        return items;
    }

    int getMaxPriority()  {
        return newItemPriority - 1;  //so if there's 4 items, newItemPriority will be 5
    }

}
