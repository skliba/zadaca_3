package com.skliba.dpatterns.composite;

import java.util.ArrayList;

public class ItemGroup extends InventoryComponent {

    private String serial;
    private String name;
    private ArrayList<InventoryComponent> components = new ArrayList<>();

    public ItemGroup(String serial, String name) {
        this.serial = serial;
        this.name = name;
    }

    @Override
    public String getCode() {
        return serial;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addItem(InventoryComponent item) {
        components.add(item);
    }

    @Override
    public void removeItem(int index) {
        components.remove(index);
    }

    @Override
    public InventoryComponent getItem(int index) {
        return components.get(index);
    }

    @Override
    public String toString() {
        System.out.println(getName() + "\n");

        for (Object inventoryInfo : components) {
            inventoryInfo.toString();
        }
        return "";
    }

    public ArrayList<InventoryComponent> getComponents() {
        return components;
    }
}
