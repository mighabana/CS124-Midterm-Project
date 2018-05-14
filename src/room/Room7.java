package room;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import main_package.Command;
import main_package.Direction;
import main_package.GameState;
import main_package.Item;
import main_package.LocalState;
import main_package.Room;
import main_package.Drawer;
import main_package.Inventory;
import ui.InventoryComponent;

/**
 *
 * @author Luis Ligunas
 */
public class Room7 extends Room {

    @Direction(command = Direction.NORTH, name = "North")
    private Room6 room6;
    @Direction(command = Direction.EAST, name = "East")
    private Room8 room8;
    
    private Drawer runner;

    @LocalState(name = "lookedAround")
    private boolean lookedAround;
    @LocalState(name = "isChestOpen")
    private boolean isChestOpen;

    public Room7(Drawer runner) {
        this.runner = runner;
    }

    public String entry() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        GameState.getInstance().setCurrRoom(this);

        pw.println("You find yourself in a run down room.");

        return sw.toString();
    }

    @Command(command = "look", name = "look")
    public String look() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        pw.println("You see a chest in the middle of the room. You can walk up to it and 'openChest'.");
        lookedAround = true;

        return sw.toString();
    }

    @Command(command = "openChest", name = "openChest")
    public String openChest() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        if (!isChestOpen) {
            pw.println("You go up to the chest in the middle of the room and you pry it open.");
            pw.println("Inside the chest you see a set of explosives that you could probably use to blow something up!");
            addItemToRoom(Item.BOMB, new Item(Item.BOMB, InventoryComponent.ROOT_PATH + "bomb.png"));
            takeItem(Item.BOMB);
            isChestOpen = true;
        } else {
            pw.println("You can't open an opened chest.");
        }

        return sw.toString();
    }
    
    @Command(command="help", name="help")
    @Override
    public String help() {
        ArrayList<String> commands = getCommandsList();
        //remove things from commands and directions that are not allowed by state
        
        if(!lookedAround)
            commands.remove("openChest");
        
        String out = getFormattedDirections() + "\n" + Room.formatCommands(commands) + "\n" + getFormattedItems();
        
        return out;
    }
    
    @Command(command=Room.TAKE_ITEM_COMMAND, name=Room.TAKE_ITEM_NAME)
    public String takeItem() {
        String itemString = runner.getLastInput();
        itemString = itemString.substring(itemString.indexOf("take") + 5);
        
        return takeItem(itemString);
    }
    
    @Command(command = Room.USE_ITEM_COMMAND, name = Room.USE_ITEM_NAME)
    public String useItem() {
        String itemName = runner.getLastInput();
        itemName = itemName.substring(itemName.indexOf("use") + 4);
        
        return useItem(itemName);
    }

    public boolean isIsChestOpen() {
        return isChestOpen;
    }
    
    @Override
    public void updateStates(String[] localStates) {
    		lookedAround = Boolean.parseBoolean(localStates[0]);
    		isChestOpen = Boolean.parseBoolean(localStates[1]);
    }
    
    public boolean[] getStates() {
    		boolean[] output = {lookedAround, isChestOpen};
    		return output;
    }
    
}
