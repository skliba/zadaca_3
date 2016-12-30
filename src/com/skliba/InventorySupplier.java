package com.skliba;

import com.skliba.dpatterns.composite.InventoryComponent;
import com.skliba.dpatterns.composite.Item;
import com.skliba.dpatterns.composite.ItemGroup;
import com.skliba.dpatterns.singleton.Dive;
import com.skliba.dpatterns.singleton.InventoryData;
import com.skliba.dpatterns.mvc.model.Diver;

import java.util.ArrayList;
import java.util.List;

public class InventorySupplier {

    private static final String WET_SUIT_KEY = "1.1.1";
    private static final String HALF_WET_SUIT_KEY = "1.1.2";
    private static final String SUIT_KEY = "1.1";
    private static final String DRY_SUIT_KEY = "1.1.3";
    private static final String UNDERSUIT_KEY = "1.2";
    private static final String HOOD_KEY = "1.4";
    private static final String GLOVES_KEY = "1.3";
    private static final String BOOTS_KEY = "1.5";
    private static final String REGULATOR_KEY = "2.1";
    private static final String UNDERWATER_BOTTLE = "2.2";
    private static final String DIVING_INSTRUMENTS = "4";
    private static final String ADDITIONAL_GEAR = "5";
    private static final String PHOTO_VIDEO_CAMERA = "6.1";
    private static final String UNDERWATER_LIGHT = "6.2";
    private static final String NIGHT_LIGHT_KEY = "5.1";
    private static final String DIVING_COMPUTER = "4.1";
    private static final String BCD = "3.1";
    private static final String IRON_BELT = "3.2";

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
        for (InventoryComponent inventoryComponent : inventoryComponents) {
            if (inventoryComponent instanceof Item) {
                supplyDiverWithItem(diver, (Item) inventoryComponent);
            } else {
                findItems(diver, ((ItemGroup) inventoryComponent).getComponents());
            }
        }

    }

    private void supplyDiverWithItem(Diver diver, Item item) {

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
        } else if (item.getCode().startsWith(BCD)) {
            findBcd(diver);
        } else if (item.getCode().startsWith(IRON_BELT)) {
            findIronBelt(diver);
        } else if (item.getCode().startsWith(DIVING_INSTRUMENTS)) {
            findComputer(diver);
        } else if (item.getCode().startsWith(ADDITIONAL_GEAR)) {
            findAdditionalGear(diver);
        } else if (item.getCode().startsWith(PHOTO_VIDEO_CAMERA)) {
            findCamera(diver);
        } else if (item.getCode().startsWith(UNDERWATER_LIGHT)) {
            findLight(diver);
        }

    }

    private void findLight(Diver diver) {
        for (Item item : itemsArrayList) {
            if (isItemAvailableAndIsForRecording(UNDERWATER_LIGHT, item)) {
                if (!diver.hasInventoryItemOfSpecificCategory(UNDERWATER_LIGHT)) {
                    diver.addInventoryItem(item);
                }
                return;
            }
        }
        diver.setDiverInventoryLevel(DiverInventoryLevel.NOT_EQUIPPED);
    }

    private void findCamera(Diver diver) {
        for (Item item : itemsArrayList) {
            if (isItemAvailableAndIsForRecording(PHOTO_VIDEO_CAMERA, item)) {
                if(!diver.hasInventoryItemOfSpecificCategory(PHOTO_VIDEO_CAMERA)) {
                    diver.addInventoryItem(item);
                }
                return;
            }
        }
        diver.setDiverInventoryLevel(DiverInventoryLevel.NOT_EQUIPPED);
    }

    private void findAdditionalGear(Diver diver) {
        for (Item item : itemsArrayList) {
            if (isUsedAtNightAndAvailable(NIGHT_LIGHT_KEY, item)) {
                if (!diver.hasSpecificInventoryItem(item)) {
                    diver.addInventoryItem(item);
                }
                return;
            }

            if (isItemInStock(ADDITIONAL_GEAR, item)) {
                if (!diver.hasSpecificInventoryItem(item)) {
                    diver.addInventoryItem(item);
                }
                return;
            }
        }
        diver.setDiverInventoryLevel(DiverInventoryLevel.NOT_EQUIPPED);
    }

    private void findComputer(Diver diver) {
        for (Item item : itemsArrayList) {
            if (isUsedAtNightAndAvailable(DIVING_COMPUTER, item)) {
                if (!diver.hasSpecificInventoryItem(item)) {
                    diver.addInventoryItem(item);
                }
                return;
            }

            if (isItemInStock(DIVING_INSTRUMENTS, item)) {
                if (!diver.hasSpecificInventoryItem(item)) {
                    diver.addInventoryItem(item);
                }
                return;
            }
        }
        diver.setDiverInventoryLevel(DiverInventoryLevel.NOT_EQUIPPED);
    }

    private void findBottle(Diver diver) {
        for (Item item : itemsArrayList) {
            if (isItemInStock(UNDERWATER_BOTTLE, item)) {
                if (!diver.hasInventoryItemOfSpecificCategory(UNDERWATER_BOTTLE)) {
                    diver.addInventoryItem(item);
                }
                return;
            }
        }
        diver.setDiverInventoryLevel(DiverInventoryLevel.NOT_EQUIPPED);
    }

    private void findIronBelt(Diver diver) {
        for (Item item : itemsArrayList) {
            if (isItemInStock(IRON_BELT, item)) {
                diver.addInventoryItem(item);
                return;
            }
        }
        diver.setDiverInventoryLevel(DiverInventoryLevel.NOT_EQUIPPED);
    }

    private void findBcd(Diver diver) {
        for (Item item : itemsArrayList) {
            if (isItemInStock(BCD, item)) {
                if (!diver.hasInventoryItemOfSpecificCategory(BCD)) {
                    diver.addInventoryItem(item);
                }
                return;
            }
        }
        diver.setDiverInventoryLevel(DiverInventoryLevel.NOT_EQUIPPED);
    }


    private void findRegulator(Diver diver) {
        for (Item item : itemsArrayList) {
            if (isItemAdequate(REGULATOR_KEY, item)) {
                if (!diver.hasInventoryItemOfSpecificCategory(REGULATOR_KEY)) {
                    diver.addInventoryItem(item);
                }
                return;
            }
        }
        diver.setDiverInventoryLevel(DiverInventoryLevel.NOT_EQUIPPED);
    }

    private void findAdequateBoots(Diver diver) {
        for (Item item : itemsArrayList) {
            if (isItemAdequate(BOOTS_KEY, item)) {
                if (!diver.hasInventoryItemOfSpecificCategory(BOOTS_KEY)) {
                    diver.addInventoryItem(item);
                }
                return;
            }
        }
        diver.setDiverInventoryLevel(DiverInventoryLevel.NOT_EQUIPPED);
    }

    private void findMatchingGloves(Diver diver) {
        for (Item item : itemsArrayList) {
            if (isItemAdequate(GLOVES_KEY, item)) {
                if (!diver.hasInventoryItemOfSpecificCategory(GLOVES_KEY)) {
                    diver.addInventoryItem(item);
                }
                return;
            }
        }
        diver.setDiverInventoryLevel(DiverInventoryLevel.NOT_EQUIPPED);
    }

    private void findMatchingHalfWetSuit(Diver diver) {
        for (Item item : itemsArrayList) {
            if (isItemAdequate(HALF_WET_SUIT_KEY, item)) {
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
                if (!diver.hasInventoryItemOfSpecificCategory(HOOD_KEY)) {
                    diver.addInventoryItem(item);
                }
                return;
            }
        }
        diver.setDiverInventoryLevel(DiverInventoryLevel.NOT_EQUIPPED);
    }

    private void findMatchingDrySuit(Diver diver) {
        for (Item item : itemsArrayList) {
            if (isItemAdequate(DRY_SUIT_KEY, item)) {
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

    private boolean isItemAvailableAndIsForRecording(String key, Item item) {
        if (item.getCode().startsWith(key) && item.getNumberOfItems() > 0 && Dive.getInstance().getNumberOfRecorders() > 0) {
            return true;
        }
        return false;
    }

    private boolean isUsedAtNightAndAvailable(String key, Item item) {
        return item.getCode().equals(key) && item.getNumberOfItems() > 0 && Dive.getInstance().isNightDive();
    }
}
