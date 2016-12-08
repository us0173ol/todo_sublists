package com.company;

/**
 * Created by admin on 12/8/16.
 */
public class Item implements Comparable<Item> {

    String task;
    int priority;

    Item(String task) {
        this.task = task;

        //The containing list object will set, and manage the priority of this item
    }

    //So that the a list of Items can be sorted in priority order, lowest at the start
    @Override
    public int compareTo(Item otherItem) {
        return otherItem.priority - this.priority;
    }

    //todo a to string method

    @Override
    public String toString() {
        return task; // todo priority etc.
    }
}
