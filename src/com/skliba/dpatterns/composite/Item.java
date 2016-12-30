package com.skliba.dpatterns.composite;

public class Item extends InventoryComponent {

    private String serial;
    private String temperature;
    private String name;
    private String hood;
    private String underSuit;
    private String nightSuit;
    private String recording;
    private int numberOfItems;

    public Item(String serial, String name, String temperature, String hood, String underSuit, String nightSuit,
                String recording, int numberOfItems) {
        this.serial = serial;
        this.name = name;
        this.temperature = temperature;
        this.hood = hood;
        this.underSuit = underSuit;
        this.nightSuit = nightSuit;
        this.recording = recording;
        this.numberOfItems = numberOfItems;
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
    public String getAllowedTemperature() {
        return temperature;
    }

    @Override
    public String getHood() {
        return hood;
    }

    @Override
    public String getUnderSuit() {
        return underSuit;
    }

    @Override
    public String getNightSuit() {
        return nightSuit;
    }

    @Override
    public String recording() {
        return recording;
    }

    @Override
    public int getNumberOfItems() {
        return numberOfItems;
    }

    @Override
    public String toString() {
        System.out.println(getName() + " " + getAllowedTemperature() + " " + getHood() + " ");
        return "";
    }

    public void reduceNumberOfItems() {
        numberOfItems--;
    }
}
