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
        int currentPriority = item.getPriority();
        item.setPriority(newPriority);
        if(newPriority < currentPriority) {

            for( int i = newPriority-1; i < currentPriority-1; i++){
                    //items.get(i).setPriority(item.getPriority()+1);
                items.get(i).adjustPriority(1);
                System.out.println("ITCP: " + item);
                System.out.println("Newpriority: " + newPriority);

            }
            }
        if(newPriority > currentPriority){
            for(int i = currentPriority; i < newPriority; i++){
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
