package com.skliba.dpatterns.composite;

public abstract class InventoryComponent {

    public String getCode() {
        throw new UnsupportedOperationException();
    }

    protected String getName() {
        throw new UnsupportedOperationException();
    }

    protected String getAllowedTemperature() {
        throw new UnsupportedOperationException();
    }

    protected String getHood() {
        throw new UnsupportedOperationException();
    }

    protected String getUnderSuit() {
        throw new UnsupportedOperationException();
    }

    protected String getNightSuit() {
        throw new UnsupportedOperationException();
    }

    protected String recording() {
        throw new UnsupportedOperationException();
    }

    protected int getNumberOfItems() {
        throw new UnsupportedOperationException();
    }

    protected void addItem(InventoryComponent item) {
        throw new UnsupportedOperationException();
    }

    protected void removeItem(int index) {
        throw new UnsupportedOperationException();
    }

    protected InventoryComponent getItem(int index) {
        throw new UnsupportedOperationException();
    }
}
