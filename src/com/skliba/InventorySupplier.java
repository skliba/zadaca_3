package com.skliba;

import com.skliba.dpatterns.composite.InventoryComponent;
import com.skliba.dpatterns.composite.Item;
import com.skliba.dpatterns.composite.ItemGroup;
import com.skliba.dpatterns.singleton.Dive;
import com.skliba.dpatterns.singleton.InventoryData;
import com.skliba.dpatterns.visitor.Diver;

import java.util.ArrayList;
import java.util.List;

public class InventorySupplier {

    public static final String WET_SUIT_KEY = "1.1.1";
    public static final String HALF_WET_SUIT_KEY = "1.1.2";
    public static final String SUIT_KEY = "1.1";
    public static final String DRY_SUIT_KEY = "1.1.3";
    public static final String UNDERSUIT_KEY = "1.2";
    public static final String HOOD_KEY = "1.4";
    public static final String GLOVES_KEY = "1.3";
    public static final String BOOTS_KEY = "1.5";
    public static final String REGULATOR_KEY = "2.1";
    public static final String UNDERWATER_BOTTLE = "2.2";
    private boolean requiresHood = false;
    private boolean requiresUnderSuit = false;
    private boolean suitAlreadyFound = false;

    private List<Item> itemsArrayList = new ArrayList<>();

    public void supplyDiverWithInventory(Diver diver) {
        List<ItemGroup> itemGroups = InventoryData.getInstance().getItemGroups();
        for (ItemGroup itemGroup : itemGroups) {
            fillArrayListWithItems(itemGroup.getComponents());
        }
        for (ItemGroup itemGroup : itemGroups) {
            if (diver.getDiverInventoryLevel() == DiverInventoryLevel.NOT_EQUIPPED) {
                break;
            }
            findItems(diver, itemGroup.getComponents());
        }
    }

    private void fillArrayListWithItems(ArrayList<InventoryComponent> itemGroup) {
        for (InventoryComponent inventoryComponent : itemGroup) {
            if (inventoryComponent instanceof Item) {
                itemsArrayList.add((Item) inventoryComponent);
            } else {
                fillArrayListWithItems(((ItemGroup) inventoryComponent).getComponents());
            }
        }
    }

    private void findItems(Diver diver, List<InventoryComponent> inventoryComponents) {
        boolean isMinimalEquipmentSupplied = false;
        for (InventoryComponent inventoryComponent : inventoryComponents) {
            if (inventoryComponent instanceof Item) {
                if (supplyDiverWithItem(diver, (Item) inventoryComponent)) {
                    isMinimalEquipmentSupplied = true;
                    if (diver.isMinimalGear()) {
                        break;
                    }
                    diver.removeExtraInventoryItem();
                    break;
                }
            } else {
                findItems(diver, ((ItemGroup) inventoryComponents).getComponents());
            }
        }

    }

    private boolean supplyDiverWithItem(Diver diver, Item item) {
        boolean isNightDive = Dive.getInstance().isNightDive();
        int waterTemperature = Dive.getInstance().getWaterTemperature();
        int numberOfRecorders = Dive.getInstance().getNumberOfRecorders();

        if (item.getCode().startsWith(SUIT_KEY)) {

            if (!suitAlreadyFound && Dive.getInstance().isDrySuitDive()) {

                findMatchingDrySuit(diver);

            } else if (!suitAlreadyFound && !Dive.getInstance().isDrySuitDive()) {

                if (item.getCode().startsWith(WET_SUIT_KEY)) {

                    findMatchingWetSuit(diver);

                } else if (item.getCode().startsWith(HALF_WET_SUIT_KEY)) {

                    findMatchingHalfWetSuit(diver);
                }
            }

        } else if (item.getCode().startsWith(GLOVES_KEY)) {
            findMatchingGloves(diver);
        } else if (item.getCode().startsWith(HOOD_KEY)) {
            findAdequateHood(diver);
        } else if (item.getCode().startsWith(BOOTS_KEY)) {
            findAdequateBoots(diver);
        } else if (item.getCode().startsWith(REGULATOR_KEY)) {
            findRegulator(diver);
        } else if (item.getCode().startsWith(UNDERWATER_BOTTLE)) {
            findBottle(diver);
        }

        if (!item.getAllowedTemperature().equals("#") && Integer.parseInt(item.getAllowedTemperature()) > waterTemperature) {
            return false;
        }

        if (item.getHood().equals("+")) {
            requiresHood = true;
        }

        if (isNightDive && !item.getNightSuit().equals("*")) {
            return false;
        }

        if (item.getUnderSuit().equals("+")) {
            requiresUnderSuit = true;
        }

        if (numberOfRecorders > 0 && !item.recording().equals("*")) {
            return false;
        }

    }

    private void findBottle(Diver diver) {
        for (Item item : itemsArrayList) {
            if (isItemInStock(UNDERWATER_BOTTLE, item)) {
                //TODO decrement number of items in stock
                diver.addInventoryItem(item);
                return;
            }
        }
        diver.setDiverInventoryLevel(DiverInventoryLevel.NOT_EQUIPPED);
    }

    private void findRegulator(Diver diver) {
        for (Item item : itemsArrayList) {
            if (isItemAdequate(REGULATOR_KEY, item)) {
                //TODO decrement number of items in stock
                diver.addInventoryItem(item);
                return;
            }
        }
        diver.setDiverInventoryLevel(DiverInventoryLevel.NOT_EQUIPPED);
    }

    private void findAdequateBoots(Diver diver) {
        for (Item item : itemsArrayList) {
            if (isItemAdequate(BOOTS_KEY, item)) {
                //TODO decrement number of items in stock
                diver.addInventoryItem(item);
                return;
            }
        }
        diver.setDiverInventoryLevel(DiverInventoryLevel.NOT_EQUIPPED);
    }

    private void findMatchingGloves(Diver diver) {
        for (Item item : itemsArrayList) {
            if (isItemAdequate(GLOVES_KEY, item)) {
                //TODO decrement number of items in stock
                diver.addInventoryItem(item);
                return;
            }
        }
        diver.setDiverInventoryLevel(DiverInventoryLevel.NOT_EQUIPPED);
    }

    private void findMatchingHalfWetSuit(Diver diver) {
        for (Item item : itemsArrayList) {
            if (isItemAdequate(HALF_WET_SUIT_KEY, item)) {
                //TODO decrement number of items in stock
                suitAlreadyFound = true;
                diver.addInventoryItem(item);
                return;
            }
        }
        diver.setDiverInventoryLevel(DiverInventoryLevel.NOT_EQUIPPED);
    }

    private void findMatchingWetSuit(Diver diver) {
        for (Item item : itemsArrayList) {
            if (isItemAdequate(WET_SUIT_KEY, item)) {
                //TODO decrement number of items in stock
                suitAlreadyFound = true;
                diver.addInventoryItem(item);
                if (item.getHood().equals("+")) {
                    findAdequateHood(diver);
                }
                return;
            }
        }
        diver.setDiverInventoryLevel(DiverInventoryLevel.NOT_EQUIPPED);
    }

    private void findAdequateHood(Diver diver) {
        for (Item item : itemsArrayList) {
            if (isItemAdequate(HOOD_KEY, item)) {
                //TODO decrement number of items in stock
                diver.addInventoryItem(item);
                return;
            }
        }
        diver.setDiverInventoryLevel(DiverInventoryLevel.NOT_EQUIPPED);
    }

    private void findMatchingDrySuit(Diver diver) {
        for (Item item : itemsArrayList) {
            if (isItemAdequate(DRY_SUIT_KEY, item)) {
                //TODO decrement number of items in stock.
                suitAlreadyFound = true;
                diver.addInventoryItem(item);
                findMatchingUnderSuit(diver);
                return;
            }
        }
        diver.setDiverInventoryLevel(DiverInventoryLevel.NOT_EQUIPPED);
    }

    private void findMatchingUnderSuit(Diver diver) {
        for (Item item : itemsArrayList) {
            if (isItemAdequate(UNDERSUIT_KEY, item)) {
                //TODO decrement number of items in stock
                diver.addInventoryItem(item);
                return;
            }
        }
        diver.setDiverInventoryLevel(DiverInventoryLevel.NOT_EQUIPPED);
    }

    private boolean isItemAdequate(String key, Item item) {
        return item.getCode().startsWith(key) && item.getNumberOfItems() > 0 &&
                Integer.parseInt(item.getAllowedTemperature()) < Dive.getInstance().getWaterTemperature();
    }

    private boolean isItemInStock(String key, Item item) {
        return item.getCode().startsWith(key) && item.getNumberOfItems() > 0;
    }
}
