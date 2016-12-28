package com.skliba.dpatterns.singleton;

import com.skliba.dpatterns.composite.ItemGroup;

import java.util.ArrayList;

public class InventoryData {

    private ArrayList<ItemGroup> itemGroups = new ArrayList<>();

    private static InventoryData instance;

    private InventoryData() {
    }

    public static InventoryData getInstance() {
        if (instance == null) {
            instance = new InventoryData();
        }
        return instance;
    }

    public ArrayList<ItemGroup> getItemGroups() {
        return itemGroups;
    }

    public void setItemGroups(ArrayList<ItemGroup> itemGroups) {
        this.itemGroups = itemGroups;
    }

    public void addItemGroup(ItemGroup itemGroup) {
        itemGroups.add(itemGroup);
    }

    public ItemGroup getItemGroup(int index) {
        return itemGroups.get(index);
    }
}
