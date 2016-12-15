package com.company;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by miked on 12/13/2016.
 */
public class DB {


    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";        //Configure the driver needed
    private static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/ToDoLists";     //Connection string – where's the database?
    private static final String USER = "mikey";   //TODO replace with your username
    private static final String PASSWORD = "mikedodge";   //TODO replace with your password
    private static String TABLE_NAME = "";
    private static final String TASK_COL = "Task";
    private static final String PRIORITY_COL = "Priority";


    DB() {

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Can't instantiate driver class; check you have drives and classpath configured correctly?");
            cnfe.printStackTrace();
            System.exit(-1);  //No driver? Need to fix before anything else will work. So quit the program
        }
    }

    void delete(String tableName,Item item){

        TABLE_NAME = tableName;
        try(Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD)){

            String deleteSQLTemplate = "DELETE FROM %s WHERE %s = ? AND %s = ?";
            String deleteSQL = String.format(deleteSQLTemplate, TABLE_NAME, PRIORITY_COL, TASK_COL);
            System.out.println("The SQL for the prepared statement is " + deleteSQL);
            PreparedStatement deletePreparedStatement = conn.prepareStatement(deleteSQL);
            deletePreparedStatement.setString(2, item.task);
            deletePreparedStatement.setInt(1, item.priority);


            System.out.println(deletePreparedStatement.toString());

            deletePreparedStatement.execute();

            deletePreparedStatement.close();
            conn.close();
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
    }

    void createTable(String tableName) {

        TABLE_NAME = tableName;

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            //You should have already created a database via terminal/command prompt

            //Create a table in the database, if it does not exist already
            //Can use String formatting to build this type of String from constants coded in your program
            //Don't do this with variables with data from the user!! That's what ParameterisedStatements are, and that's for queries, updates etc. , not creating tables.
            // You shouldn't make database schemas from user input anyway.
            String createTableSQLTemplate = "CREATE TABLE IF NOT EXISTS %s (%s INTEGER, %s VARCHAR (100))";
            String createTableSQL = String.format(createTableSQLTemplate, TABLE_NAME, PRIORITY_COL, TASK_COL);

            statement.executeUpdate(createTableSQL);
            System.out.println("Created new table " + tableName);

            statement.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }


    void addItem(String tablename, Item item)  {
            TABLE_NAME = tablename;
        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD)) {

            String addItemSQL = "INSERT INTO " + TABLE_NAME + " VALUES ( ? , ? ) " ;
            PreparedStatement addItemPS = conn.prepareStatement(addItemSQL);
            addItemPS.setString(2, item.getTask());
            addItemPS.setInt(1, item.getPriority());


            addItemPS.execute();

            System.out.println("Added: " + item);

            addItemPS.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }

    }


    ArrayList<Item> fetchAllRecords() {
        //TABLE_NAME = tablename;
        ArrayList<Item> allRecords = new ArrayList();

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            String selectAllSQL = "SELECT * FROM " + TABLE_NAME;
            ResultSet rsAll = statement.executeQuery(selectAllSQL);

            while (rsAll.next()) {
                int priority = rsAll.getInt(PRIORITY_COL);
                String task = rsAll.getString(TASK_COL);
                Item item = new Item(task);
                allRecords.add(priority, item);
            }

            rsAll.close();
            statement.close();
            conn.close();

            return allRecords;    //If there's no data, this will be empty

        } catch (SQLException se) {
            se.printStackTrace();
            return null;  //since we have to return something.
        }
    }
}
