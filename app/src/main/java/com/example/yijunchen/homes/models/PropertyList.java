package com.example.yijunchen.homes.models;


import java.util.ArrayList;

/**
 * Created by zhangwenpurdue on 6/24/2017.
 */

public class PropertyList extends ArrayList<Property> {
    private static PropertyList ourInstance = null;

    public synchronized static PropertyList getInstance() {

        if (ourInstance == null) {
            synchronized (PropertyList.class) {
                if (ourInstance == null) {
                    ourInstance = new PropertyList();
                }
            }
        }
        return ourInstance;
    }

    private PropertyList() {
    }
}
