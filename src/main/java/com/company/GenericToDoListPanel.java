package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by clara on 12/8/16. Represents any one list - the main list, or one of the sublists.
 * This wasn't built in the GUI designer.
 */
public class GenericToDoListPanel extends JPanel {

    private JList<Item> todoItemsList;
    private JComboBox changePriorityComboBox;

    private List list;

    ToDoGUI todoGUI;    //the main GUI window - something to notify when the list updates

    DefaultListModel<Item> itemsModel;

    // todo need to ensure that names need to be unique, they are used to figure out what updated. Alternatively, replace with a unique id

    GenericToDoListPanel(final List theList, ToDoGUI gui) {

        todoGUI = gui;
        this.list = theList;

        setLayout(new BorderLayout());  //arrange components in a vertical line, change if you prefer a different layout

        todoItemsList = new JList<Item>();
        JScrollPane scrollList = new JScrollPane(todoItemsList);

        changePriorityComboBox = new JComboBox();

        add(changePriorityComboBox, BorderLayout.NORTH);
        add(scrollList, BorderLayout.CENTER);

        itemsModel = new DefaultListModel<Item>();
        todoItemsList.setModel(itemsModel);

        updateList();
        updateComboBox();

//        int highestPriority = list.getMaxPriority();
//        for (int p = 1; p <= highestPriority; p++) {
//            changePriorityComboBox.addItem(p);
//        }


        changePriorityComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (changePriorityComboBox.getSelectedIndex() != -1 && todoItemsList.getSelectedIndex() != -1) {
                    Item itemToChange = todoItemsList.getSelectedValue();
                    int newPriority = changePriorityComboBox.getSelectedIndex() + 1;
                    System.out.println(itemToChange);
                    System.out.println(newPriority);
                    list.changePriority(itemToChange, newPriority);
                    updateComboBox();
                } else //if (changePriorityComboBox.getSelectedIndex() != -1 && list.) {
                {
                    System.out.println("");
                }

                //todo add listener to combobox, change priority of items, notify todoGUI so can update everything - lists held in GUI and in DB
                todoGUI.listUpdated(list);


            }
        });

    }
    public void getItemName(){

    }

    public void updateList() {

        //refresh JList

        itemsModel.clear();

        for (Item item : list.getAllItems()) {
            itemsModel.addElement(item);

        }

//        for(int x = 1; x<= list.getMaxPriority(); x++){
//            changePriorityComboBox.removeItem(x);
//            changePriorityComboBox.addItem(x);
//        }
        //todo update combobox


    }
    void updateComboBox(){

        for(int i = 1; i <= list.getMaxPriority(); i++) {
                changePriorityComboBox.removeItem(i);
                changePriorityComboBox.addItem(i);

            }
        }
    }


