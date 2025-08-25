package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import java.util.List;

public class CatalogItemsEvent {
    private final List<Item> items;

    public CatalogItemsEvent(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }
}