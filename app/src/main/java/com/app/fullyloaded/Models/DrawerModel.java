package com.app.fullyloaded.Models;

public class DrawerModel {

    private String  ID, DrawerItem;

    public DrawerModel(String ID, String DrawerItem) {
        this.ID = ID;
        this.DrawerItem = DrawerItem;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDrawerItem() {
        return DrawerItem;
    }

    public void setDrawerItem(String drawerItem) {
        DrawerItem = drawerItem;
    }
}
