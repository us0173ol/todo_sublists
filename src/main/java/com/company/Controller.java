package com.company;

import java.util.ArrayList;

/**
 * Created by clara on 12/8/16. Main class, manages lists, and sublists
 */
public class Controller {

    static DB db;


    public static void main(String[] args) throws Exception{

        Controller controller = new Controller();   //make an instance of this class. If you call all methods from main, they all have to be static
        controller.startApp();

    }

    void startApp() {
        db = new DB();
        db.createTable("main");

        //todo when reading from DB, this is where you'd fetch data from DB and send it to gui.


        //todo instead create some test data and send to GUI.
        List mainList;
        ArrayList<List> sublists = new ArrayList<List>();   //todo is this the best way to manage the sublists?

//        Item test2 = new Item("gym");


        Item test3 = new Item("Push ups");
        Item test4 = new Item("Pull ups");

        mainList = new List("main", true);   // name of list, is mainlist or not
//        mainList.add(test2);

        List gymList = new List("gym", false);
//        gymList.add(test3);
//        gymList.add(test4);
////
//        sublists.add(gymList);

        ToDoGUI gui = new ToDoGUI(this, mainList, sublists);    //send a reference to this object to the GUI. Then the GUI can save this reference, and then has a place to send requests to


        gui.setMainList(mainList);
        gui.setSublists(sublists);

    }


    public void listUpdated(List updatedList) {

        db.createTable(updatedList.name);
        //todo update the database - use updatedList.name and updatedList.isMainList to figure out what part of the DB to update
    }
    void addTaskToDatabase(List list,Item item){ db.addItem(list.name, item);}
    void delete(String tableName, Item item){ db.delete(tableName,item);}
}
