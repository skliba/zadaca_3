package com.skliba.parsers;

import com.skliba.dpatterns.composite.Item;
import com.skliba.dpatterns.composite.ItemGroup;
import com.skliba.dpatterns.singleton.InventoryData;

import java.util.ArrayList;

public class GearParser extends Parser {

    private static final String SEPARATOR = ";";

    private static final int DATA_PER_ROW = 8;

    private static final int SMALL_DATA_PER_ROW = 2;

    @Override
    public void parseData(String line) {
        String[] gearData = line.split(SEPARATOR);

        if (gearData.length != DATA_PER_ROW && gearData.length != SMALL_DATA_PER_ROW) {
            return;
        }

        if (gearData.length == SMALL_DATA_PER_ROW) {
            ItemGroup ig = new ItemGroup(gearData[0], gearData[1]);
            if (InventoryData.getInstance().getItemGroups().size() < 0) {
                ItemGroup lastInsertedItemGroup = InventoryData.getInstance().getItemGroup(InventoryData.getInstance().getItemGroups().size() - 1);

                if (lastInsertedItemGroup != null && ig.getCode().contains(lastInsertedItemGroup.getCode())) {
                    lastInsertedItemGroup.addItem(ig);
                } else {
                    InventoryData.getInstance().addItemGroup(ig);
                }
            } else {
                InventoryData.getInstance().addItemGroup(ig);
            }

        } else {
            Item item = new Item(gearData[0], gearData[1], gearData[2],
                    gearData[3], gearData[4], gearData[5], gearData[6], Integer.parseInt(gearData[7]));

            ArrayList<ItemGroup> itemGroups = InventoryData.getInstance().getItemGroups();

            for (ItemGroup ig : itemGroups) {

                if (item.getCode().contains(ig.getCode())) {

                    for (int i = ig.getComponents().size() - 1; i >= 0; i--) {

                        if (i >= 0 && ig.getComponents().get(i) instanceof ItemGroup
                                && item.getCode().contains(((ItemGroup) ig.getComponents().get(i)).getCode())) {
                            ig.addItem(item);
                        }
                    }
                }
            }
        }
    }

    @Override
    void parseComplete() {
        System.out.println("Im done");
    }
}
