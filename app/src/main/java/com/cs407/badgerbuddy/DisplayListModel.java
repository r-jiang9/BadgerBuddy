package com.cs407.badgerbuddy;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class DisplayListModel {
    String listName; //name of floor plan list
    ArrayList<String> elements; //list of floor plan elements

    //empty constructor
    public DisplayListModel(){

    }

    //constructor
    public DisplayListModel(String listName){
        this.listName = listName;
        this.elements = new ArrayList<>();
    }

    public void setListName(String listName){
        this.listName = listName;
    }

    public void addElement(String address){
            elements.add(address);
    }

    public void removeElement(String address){
            elements.remove(address);
    }

    public String getName(){
        return listName;
    }

    @NonNull
    @Override
    public String toString() {
        return listName;
    }
}
