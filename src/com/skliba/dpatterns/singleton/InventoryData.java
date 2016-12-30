package com.skliba.dpatterns.singleton;

import com.skliba.dpatterns.composite.InventoryComponent;
import com.skliba.dpatterns.composite.Item;
import com.skliba.dpatterns.composite.ItemGroup;

import java.util.ArrayList;
import java.util.List;

public class InventoryData {

    private ArrayList<ItemGroup> itemGroups = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
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

    public List<Item> getItems() {
        return items;
    }

    public void findAllAvailableItems() {
        for (ItemGroup itemGroup : itemGroups) {
            findItems(itemGroup.getComponents());
        }
    }

    private void findItems(ArrayList<InventoryComponent> itemGroup) {
        for (InventoryComponent inventoryComponent : itemGroup) {
            if (inventoryComponent instanceof Item) {
                items.add((Item) inventoryComponent);
            } else {
                findItems(((ItemGroup) inventoryComponent).getComponents());
            }
        }
    }
}
