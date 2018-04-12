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

/**
 *
 * @author Luis Ligunas
 */
public class Room8 extends Room {

    @Direction(command = Direction.NORTH, name = "North")
    private Room9 room9;

    private Drawer runner;

    private boolean magicDiscovered, bombUsed;

    public Room8(Drawer runner) {
        this.runner = runner;
    }

    public String entry() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        GameState.getInstance().setCurrRoom(this);

        pw.println("After falling you find yourself in another room.");
        pw.println("In the room you see a small fountain pouring down with a mysterious red liquid.");
        pw.println("In the northern end of the room you also see a wooden door");

        return sw.toString();
    }

    @Command(command = "look", name = "look")
    public String look() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        if (Inventory.getInstance().contains(Item.SWORD)) {
            pw.println("You take a closer look at the small fountain and you sense some magic eminating from it.");
            pw.println("You could probably dip your sword in it to enchant it!");
            pw.println("'enchantSword' to enchant your sword with magical properties");
            
            addItemToRoom(Item.ENCHANTED_SWORD, new Item(Item.ENCHANTED_SWORD, InventoryComponent.ROOT_PATH + "enchanted_sword.png"));
            magicDiscovered = true;
        } else {
            pw.println("The fountain looks interesting I wonder what you could do with it?");
        }

        return sw.toString();
    }

    @Command(command = "enchantSword", name = "enchantSword")
    public String enchantSword() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        if (magicDiscovered) {
            pw.println("You dip your sword into the magic fountain.");
            pw.println("It is now imbued with a magical power and it changes color! WOW!");
            Inventory.getInstance().removeItem(Item.SWORD);
            takeItem(Item.ENCHANTED_SWORD);
        } else {
            pw.println("Huh?!?! What are you trying to do?");
        }

        return sw.toString();
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
        
        Inventory inventory = Inventory.getInstance();
        Item item = inventory.getItem(itemName);
        
        if(item == null)
            return Inventory.ITEM_NOT_IN_INVENTORY_STRING;
        
        if(itemName.equals(Item.BOMB)) {
            if(bombUsed)
                return "You did that already";
            else {
                bombUsed = true;
                return "The walls to the north crumbled.";
            }
        }
        
        return useItem(itemName);
    }
    
    @Command(command="help", name="help")
    @Override
    public String help() {
        ArrayList<String> directions = getDirectionsList();
        //remove things from commands and directions that are not allowed by state
        
        if(!bombUsed)
            directions.remove("North");
        
        String out = Room.formatDirections(directions) + "\n" + getFormattedCommands() + "\n" + getFormattedItems();
        
        return out;
    }
}
