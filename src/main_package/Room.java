package main_package;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    
    public static final String USE_ITEM_COMMAND = "use\\s+.*", USE_ITEM_NAME = "use <item>",
                                TAKE_ITEM_COMMAND = "(?i)(.*take\\s+[^\\s]+)", TAKE_ITEM_NAME = "take <item>";
    
    
    private HashMap<String, Item> items;
    
    public Room() {
        items = new HashMap<>();
    }
    
    public void addItemToRoom(String key, Item item) {
        items.put(key, item);
    }
    
    public String takeItem(String itemName) {
        Item item = items.remove(itemName);
        
        if(item == null)
            return Inventory.ITEM_NOT_IN_ROOM_STRING;
        
        Inventory inventory = Inventory.getInstance();
        inventory.addItem(itemName, item);
        
        return Inventory.ITEM_SUCCESSFULLY_ADDED_STRING;
    };
    
    public String useItem(String itemName) {
        Inventory inventory = Inventory.getInstance();
        Item item = inventory.getItem(itemName);
        
        if(item == null)
            return Inventory.ITEM_NOT_IN_INVENTORY_STRING;
        
        //specific effects here
        
        return Inventory.ITEM_CANNOT_BE_USED_STRING;
    };
    
    public String help() {
        String out = getFormattedDirections() + "\n" + getFormattedCommands() + "\n" + getFormattedItems();
        
        return out;
    };
    
    public ArrayList<String> getDirectionsList() {
        ArrayList<String> directions = new ArrayList<>();
        
        Class clazz = getClass();
        if(clazz.getSuperclass() != Room.class)
            clazz = clazz.getSuperclass();
        
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields)
            if (field.isAnnotationPresent(Direction.class))
                directions.add(field.getAnnotation(Direction.class).name());
        
        return directions;
    }
    
    public ArrayList<String> getCommandsList() {
        ArrayList<String> commands = new ArrayList<>();
        
        Class clazz = getClass();
        if(clazz.getSuperclass() != Room.class)
            clazz = clazz.getSuperclass();
        
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods)
            if (method.isAnnotationPresent(Command.class))
                commands.add(method.getAnnotation(Command.class).name());
        
        return commands;
    }
    
    public ArrayList<String> getItemsList() {
        ArrayList<String> list = new ArrayList<>();
        for(Object s : items.keySet().toArray())
            list.add((String) s);
        return list;
    }
    
    public String getFormattedDirections() {
        return Room.formatDirections(getDirectionsList());
    }
    
    public String getFormattedCommands() {
        return Room.formatCommands(getCommandsList());
    }
    
    public String getFormattedItems() {
        return Room.formatItems(getItemsList());
    }
    
    public static String formatDirections(ArrayList<String> directions) {
        String out = "";
        if(!directions.isEmpty()) {
            out += "You can go: \"" + directions.get(0) + "\"";
            
            if(directions.size() > 2) {
                for(int i = 1; i < directions.size() - 1; i++)
                    out += ", \"" + directions.get(i) + "\"";
            }
            if(directions.size() > 1)
                out += " and \"" + directions.get(directions.size() - 1) + "\"";
        }
        if(out.isEmpty())
            return "You may not move in any direction.";
            
        return out;
    }
    
    public static String formatCommands(ArrayList<String> commands) {
        String out = "";
        
        if(!commands.isEmpty()) {
            out += "You may do the following actions: \"" + commands.get(0) + "\"";
            if(commands.size() > 2) {
                for(int i = 1; i < commands.size() - 1; i++)
                    out += ", \"" + commands.get(i) + "\"";
            }
            if(commands.size() > 1)
                out += " and \"" + commands.get(commands.size() - 1) + "\"";
        }
        if(out.isEmpty())
            return "You may not do any action.";
        return out;
    }
    
    public static String formatItems(ArrayList<String> list) {
        String out = "";
        if(!list.isEmpty()) {
            out += "You may take: \"" + list.get(0) + "\"";
            
            if(list.size() > 2) {
                for(int i = 1; i < list.size() - 1; i++)
                    out += ", \"" + list.get(i) + "\"";
            }
            if(list.size() > 1)
                out += " and \"" + list.get(list.size() - 1) + "\"";
        }
        if(out.isEmpty())
            return "There are no items to take.";
        return out;
    }
    
}
