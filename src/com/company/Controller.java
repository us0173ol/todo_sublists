package com.company;


import java.util.ArrayList;

/**
 * Created by clara on 12/8/16. Main class, manages lists, and sublists
 */
public class Controller {


    public static void main(String[] args) {

        Controller controller = new Controller();   //make an instance of this class. If you call all methods from main, they all have to be static
        controller.startApp();

    }

    void startApp() {


        //todo when reading from DB, this is where you'd fetch data from DB and send it to gui.


        //todo instead create some test data and send to GUI.
        List mainList;
        ArrayList<List> sublists = new ArrayList<List>();   //todo is this the best way to manage the sublists?


        Item test1 = new Item("walk dog");
        Item test2 = new Item("buy diapers");
        Item test7 = new Item("groceries");
        Item test8 = new Item("gym");


        Item test3 = new Item("run 5k");
        Item test4 = new Item("do pull ups");

        Item test5 = new Item("do homework");
        Item test6 = new Item("write code");

        mainList = new List("main", true);   // name of list, is mainlist or not
        mainList.add(test1);
        mainList.add(test2);
        mainList.add(test7);
        mainList.add(test8);

        List gymList = new List("gym", false);
        gymList.add(test3);
        gymList.add(test4);

        List schoolList = new List("school", false);
        schoolList.add(test5);
        schoolList.add(test6);

        sublists.add(gymList);
        sublists.add(schoolList);


        ToDoGUI gui = new ToDoGUI(this, mainList, sublists);    //send a reference to this object to the GUI. Then the GUI can save this reference, and then has a place to send requests to


    }


    public void listUpdated(List updatedList) {
        //todo update the database - use updatedList.name and updatedList.isMainList to figure out what part of the DB to update
    }
}
