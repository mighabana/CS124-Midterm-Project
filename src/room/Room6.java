package room;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import main_package.Command;
import main_package.Direction;
import main_package.GameState;
import main_package.Inventory;
import main_package.Item;
import main_package.Room;
import main_package.Drawer;
import ui.InventoryComponent;

public class Room6 extends Room {

    @Direction(command = Direction.WEST, name = "West")
    private Room3 room3;
    @Direction(command = Direction.SOUTH, name = "South")
    private Room7 room7;

    private Drawer runner;

    public Room6(Drawer runner) {
        this.runner = runner;
    }

    public String entry() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        GameState gameState = GameState.getInstance();
        gameState.setCurrRoom(this);
        
        pw.println("You find your self in a pitch black room.");
        if (Inventory.getInstance().contains(Item.TORCH)) {
            pw.println("You could probably use your torch to illuminate the room!");
        }

        return sw.toString();
    }
    
    @Command(command = "look", name = "look")
    public String look() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        
        if (GameState.getInstance().isTorchUsed()) {
            pw.println("You see what seems to have been an entrance on the east side of the room but is now being covered by a big rock.");
            pw.println("To the south, you see another entrance that looks a bit run down but you could probably still slip through.");
        } else {
            pw.println("It's pitch black. You can't see anything, but a faint light from the entrance that you came from");
        }

        return sw.toString();
    }

    @Command(command = "help", name = "help")
    @Override
    public String help() {
        ArrayList<String> directions = getDirectionsList();
        //remove things from commands and directions that are not allowed by state
        
        if(!GameState.getInstance().isTorchUsed())
            directions.remove("South");
        
        String out = Room.formatDirections(directions) + "\n" + getFormattedCommands() + "\n" + getFormattedItems();
        
        return out;
    }
    
    @Command(command = Room.USE_ITEM_COMMAND, name = Room.USE_ITEM_NAME)
    public String useItem() {
        String itemName = runner.getLastInput();
        itemName = itemName.substring(itemName.indexOf("use") + 4);
        
        Inventory inventory = Inventory.getInstance();
        Item item = inventory.getItem(itemName);
        
        if(item == null)
            return Inventory.ITEM_NOT_IN_INVENTORY_STRING;
        
        if(itemName.equals(Item.TORCH)) {
            GameState.getInstance().setTorchUsed(true);
            return "Your torch turns on and you can see the room better.";
        }
        
        return Inventory.ITEM_CANNOT_BE_USED_STRING;
    };
    
    @Command(command=Room.TAKE_ITEM_COMMAND, name=Room.TAKE_ITEM_NAME)
    public String takeItem() {
        String itemString = runner.getLastInput();
        itemString = itemString.substring(itemString.indexOf("take") + 5);
        
        return takeItem(itemString);
    }
}
