package com.skliba;

import com.skliba.dpatterns.composite.InventoryComponent;
import com.skliba.dpatterns.composite.Item;
import com.skliba.dpatterns.composite.ItemGroup;
import com.skliba.dpatterns.singleton.Dive;
import com.skliba.dpatterns.singleton.InventoryData;
import com.skliba.dpatterns.visitor.Diver;

import java.util.List;

public class InventorySupplier {

    public void supplyDiverWithInventory(Diver diver) {
        List<ItemGroup> itemGroups = InventoryData.getInstance().getItemGroups();
        for (ItemGroup itemGroup: itemGroups) {
            if (diver.getDiverInventoryLevel() == DiverInventoryLevel.NOT_EQUIPPED) {
                break;
            }
            findItems(diver, itemGroup.getComponents());
        }
    }

    private void findItems(Diver diver, List<InventoryComponent> inventoryComponents) {
        boolean isMinimalEquipmentSupplied = false;
        for (InventoryComponent inventoryComponent: inventoryComponents) {
            if (inventoryComponent instanceof Item) {
                if (supplyDiverWithItem(diver, (Item)inventoryComponent)) {
                    isMinimalEquipmentSupplied = true;
                    if (diver.isMinimalGear()) {
                        break;
                    }
                    diver.removeExtraInventoryItem();
                    break;
                }
            } else {
                findItems(diver, ((ItemGroup)inventoryComponents).getComponents());
            }
        }
    }

    private boolean supplyDiverWithItem(Diver diver, Item item) {
        boolean isNightDive = Dive.getInstance().isNightDive();
        int waterTemperature = Dive.getInstance().getWaterTemperature();
        int numberOfRecorders = Dive.getInstance().getNumberOfRecorders();
        if (!item.getAllowedTemperature().equals("#") && Integer.parseInt(item.getAllowedTemperature()) > waterTemperature) {
            return false;
        }
        if (item.getHood().equals("+")) {
            
        }
    }
}
