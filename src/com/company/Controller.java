package com.company;


import java.sql.*;
import java.util.ArrayList;

/**
 * Created by clara on 12/8/16. Main class, manages lists, and sublists
 */
public class Controller {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/ToDoLists";
    static final String USER = "mikey";
    static final String PASSWORD = "mikedodge";


    public static void main(String[] args) throws Exception{

        Controller controller = new Controller();   //make an instance of this class. If you call all methods from main, they all have to be static
        controller.startApp();

    }

    void startApp() {

        try{
        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
        Statement statement = connection.createStatement();

        statement.execute("CREATE TABLE IF NOT EXISTS ToDoLists (Priority INTEGER , Task VARCHAR (50))");
        statement.execute("INSERT INTO ToDoLists VALUES (1, 'Gym')");

        statement.execute("CREATE TABLE IF NOT EXISTS SubLists (Priority INTEGER , Task VARCHAR (50))");
        statement.execute("INSERT INTO SubLists VALUES (1, 'Push Ups')");

        ResultSet rs = statement.executeQuery("SELECT * FROM ToDoLists");
        ResultSet rss = statement.executeQuery("SELECT * FROM SubLists");

        while (rs.next() && rss.next()) {
            System.out.print("Priority: " + rs.getInt(1));
            System.out.println("Task: " + rs.getString(2));
            System.out.print("Priority: " + rss.getInt(1));
            System.out.println("Task: " + rss.getString(2));
        }
        rs.close();
        rss.close();
        statement.close();
        connection.close();

        }catch (Exception e ){
            System.out.println(e);
        }
        //todo when reading from DB, this is where you'd fetch data from DB and send it to gui.


        //todo instead create some test data and send to GUI.
        List mainList;
        ArrayList<List> sublists = new ArrayList<List>();   //todo is this the best way to manage the sublists?



        Item test3 = new Item("run 5k");
        Item test4 = new Item("do pull ups");

        mainList = new List("main", true);   // name of list, is mainlist or not


        List gymList = new List("gym", false);
        gymList.add(test3);
        gymList.add(test4);

        sublists.add(gymList);

        ToDoGUI gui = new ToDoGUI(this, mainList, sublists);    //send a reference to this object to the GUI. Then the GUI can save this reference, and then has a place to send requests to


    }


    public void listUpdated(List updatedList) {
        //todo update the database - use updatedList.name and updatedList.isMainList to figure out what part of the DB to update
    }
}
