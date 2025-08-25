package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Item;

import java.util.ArrayList;
import java.util.List;

public class CatalogDAO {
    public static List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        // Placeholder items; in a real implementation this would query the database
        items.add(new Item(1, "Sunflower", "Flowers", 150.0, "Bright sunflower", new byte[0]));
        items.add(new Item(2, "Daisy", "Flowers", 100.0, "White daisy", new byte[0]));
        items.add(new Item(3, "Lilly", "Flowers", 80.0, "Lovely lily", new byte[0]));
        return items;
    }
}