package main_package;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    
    public static final String ITEM_NOT_IN_INVENTORY_STRING = "That item is not in the inventory.",
                                ITEM_CANNOT_BE_USED_STRING = "That item did nothing.",
                                ITEM_NOT_IN_ROOM_STRING = "That item is not in this room.",
                                ITEM_SUCCESSFULLY_ADDED_STRING = "The item was successfully added into the inventory.";
    
    private static Inventory inventory;
    private HashMap<String, Item> map;
    
    private Inventory() {
        map = new HashMap<>();
    }
    
    public static Inventory getInstance() {
        if(inventory == null)
            inventory = new Inventory();
        return inventory;
    }
    
    public boolean contains(String objectName) {
        return map.containsKey(objectName);
    }
    
    public Item getItem(String objectName) {
        return map.get(objectName);
    }
    
    public void addItem(String key, Item item) {
        map.put(key, item);
    }
    
    public void removeItem(String key) {
        map.remove(key);
    }
    
    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<Item>();
        for(Map.Entry<String, Item> entry : map.entrySet())
            items.add(entry.getValue());
        
        return items;
    }
}
