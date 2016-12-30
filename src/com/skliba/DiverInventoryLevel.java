package com.skliba;

public enum DiverInventoryLevel {
    FULLY_EQUIPPED, PARTIALLY_EQUIPPED, NOT_EQUIPPED;

    @Override
    public String toString() {
        switch (this) {
            case FULLY_EQUIPPED:
                return "Fully equipped";
            case PARTIALLY_EQUIPPED:
                return "Partially equipped";
            case NOT_EQUIPPED:
                return "Not equipped";
            default:
                break;
        }
        return "";
    }
}
