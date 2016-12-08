package com.company;

import javax.swing.*;
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


    GenericToDoListPanel mainToDoListPanel;

    private Controller controller;   //notify this when lists change

    List mainList;
    ArrayList<List> sublists;

    ToDoGUI(Controller controller, List mainList, ArrayList<List> sublists) {

        this.controller = controller;

        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setMainList(mainList);
        setSublists(sublists);

        configureComponents();
        addListeners();

        displayAllLists();

        setVisible(true);

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
                Item item = new Item(newItemTask.getText());
                mainList.add(item);
                controller.listUpdated(mainList);
                displayAllLists();
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

              //  GenericToDoListPanel listPanel = (GenericToDoListPanel)sublistTabbedPane.getComponentAt(tab);
              //  listPanel.updateList();

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
