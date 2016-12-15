package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by clara on 12/8/16. Main GUI window
 */

public class ToDoGUI extends JFrame {

    private JPanel mainPanel;

    private JTextField newItemTask;
    private JButton addButton;

    private JPanel mainListPanel;          //contains the main list, a GenericToDoListPanel
    private JPanel sublistPanel;           // contains a tabbed pane of sublists, also GenericToDoListPanel objects
    private JTabbedPane sublistTabbedPane;
    private JButton addToSublistsButton;
    private JButton newListButton;


    GenericToDoListPanel mainToDoListPanel;

    private Controller controller;   //notify this when lists change

    List mainList;
    ArrayList<List> sublists;

    ToDoGUI(final Controller controller, final List mainList, final ArrayList<List> sublists) {

        this.controller = controller;

        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setMainList(mainList);
        setSublists(sublists);

        configureComponents();
        addListeners();

        displayAllLists();
        setSize(600,400);
        setVisible(true);


        if(sublistTabbedPane.getSelectedIndex() != -1) {
            List selectedList = sublists.get(sublistTabbedPane.getSelectedIndex());
            addToSublistsButton.setText("Add to\n " + selectedList.name);
        }
        addToSublistsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(newItemTask != null){
                    Item item = new Item(newItemTask.getText());
                    List listToAddTo = sublists.get(sublistTabbedPane.getSelectedIndex());
                    listToAddTo.add(item);
                    controller.listUpdated(listToAddTo);
                    displayAllLists();
                    addToSublistsButton.setText("Add to\n " + listToAddTo.name);
                    controller.addTaskToDatabase(listToAddTo,item);
                    newItemTask.setText("");
                    newItemTask.grabFocus();

                }
            }
        });
        newListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String listName;
                Item selected = mainToDoListPanel.getSelectedItem();
                if (selected != null) {
                    listName = selected.getTask();
                } else   {
                    listName = JOptionPane.showInputDialog(ToDoGUI.this, "Enter List Name", "");
                    //todo verify that the list name the user enters is not blank
                    
                    if (listName == null) {
                        System.out.println("user cancelled (?)");
                        return;
                    }
                }
                
                //String listName = JOptionPane.showInputDialog(ToDoGUI.this, "Enter List Name", "");
                List newList = new List(listName, false);
                GenericToDoListPanel newSublist = new GenericToDoListPanel(newList, ToDoGUI.this);
                sublistTabbedPane.add(newSublist);
                sublistTabbedPane.addTab(listName, newSublist);
                sublists.add(newList);
                setSublists(sublists);
                mainToDoListPanel.updateComboBox();

                
            }
        });
        sublistTabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(sublistTabbedPane.getTabCount() > 1) {
                    List selectedList = sublists.get(sublistTabbedPane.getSelectedIndex());
                    addToSublistsButton.setText("Add to\n " + selectedList.name);
                    mainToDoListPanel.updateComboBox();
                }else{
                    addToSublistsButton.setText("Add to " + mainToDoListPanel.getSelectedItem().task);
//                    String tabName = mainToDoListPanel.getSelectedItem().getTask();
//                    sublistTabbedPane.addTab(tabName,sublistPanel);
                }
            }
        });
    }

    void setMainList(List list) {
        mainList = list;
    }

    void setSublists(ArrayList<List> sublists) {
        this.sublists = sublists;
    }
    

    private void addListeners() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo validate something is entered, clear JTextField after entry added
                if(newItemTask != null) {
                    List list = new List(mainList.name, true);
                    Item item = new Item(newItemTask.getText());
                    mainList.add(item);
                    controller.listUpdated(mainList);
                    displayAllLists();
                    mainToDoListPanel.updateComboBox();
                    controller.addTaskToDatabase(list,item);
                    System.out.println("Added " + item + " to " + list);
                    newItemTask.setText("");
                    newItemTask.grabFocus();

                }else{
                    JOptionPane.showMessageDialog(ToDoGUI.this, "Please enter something");
                }

            }
        });
    }

    void listUpdated(List updatedList) {
        //is this the main list, or one of the sublists that has updated?
        //this will be called by the GenericToDoListPanel objects
        // figure out what changed, update it, tell the controller (which will update the DB) and refresh the GUI

        if (updatedList.isMainList) {
            mainList = updatedList;
        }

        else {
            //figure out which list to update

            for ( int i = 0 ; i < sublists.size() ; i++ ) {
                if (sublists.get(i).name.equals(updatedList.name)) {
                    sublists.set(i, updatedList);
                }
            }
        }

        //Notify the controller of updates
        controller.listUpdated(updatedList);

        //refresh everything
        displayAllLists();
    }

    private void displayAllLists() {

        //display main list in main list panel

        if (mainList != null)  {
            //display main list
            mainToDoListPanel.updateList();

        }

        if (sublists != null) {

            //refresh all panels

            int tabs = sublistTabbedPane.getTabCount();
            for (int tab = 0 ; tab < tabs ; tab++)  {

                GenericToDoListPanel listPanel = (GenericToDoListPanel)sublistTabbedPane.getComponentAt(tab);
                listPanel.updateList();

            }
        }

    }

    private void configureComponents() {
          //set up list models, any other component config, as needed.

        //main list panel - contains one list

        mainListPanel.setLayout(new FlowLayout());

        mainToDoListPanel = new GenericToDoListPanel(mainList, this);

        mainListPanel.add(mainToDoListPanel);

        // sublist panel - contains tabs, each tab has one list in

        for (List sublist : sublists) {

            GenericToDoListPanel subListPanel = new GenericToDoListPanel(sublist, this);
            sublistTabbedPane.addTab(sublist.name, subListPanel);


        }

        displayAllLists();

    }


}
