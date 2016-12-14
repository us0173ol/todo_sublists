package com.company;

/**
 * Created by admin on 12/8/16.
 */
public class Item implements Comparable<Item> {

    String task;
    int priority;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    Item(String task) {
        this.task = task;

        //The containing list object will set, and manage the priority of this item
    }

    public void adjustPriority(int alpha){
        this.priority += alpha;
    }
    //So that the a list of Items can be sorted in priority order, lowest at the start
    @Override
    public int compareTo(Item otherItem) {
        return this.priority - otherItem.priority;
    }

    //todo a to string method

    @Override
    public String toString() {
        return priority + "  " + task; // todo priority etc.
    }

    public String getTask() {
        return task;
    }
}
