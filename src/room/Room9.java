package room;

import java.io.PrintWriter;
import java.io.StringWriter;
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
public class Room9 extends Room {

    @Direction(command = Direction.WEST, name = "West")
    private Room5 room5;

    private Drawer runner;

    private boolean monsterKilled, isChestOpened;

    public Room9(Drawer runner) {
        this.runner = runner;
    }

    public String entry() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        GameState.getInstance().setCurrRoom(this);

        pw.println("You find yourself in a smelly and mysterious room.");
        pw.println("Ahead of you you see a gholish monster that looks ready to strike.");
        pw.println("You also see a golden chest nearby, and what looks like a doorway behind the monster");

        return sw.toString();
    }

    @Command(command = "look", name = "look")
    public String look() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        if (!monsterKilled) {
            pw.println("You might want to deal with the monster first!");
        } else {
            pw.println("You can open the chest in peace now.");
            pw.println("You notice that the door upahead is locked.");
        }

        return sw.toString();
    }

    @Command(command = "killMonster", name = "killMonster")
    public String killMonster() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        if (Inventory.getInstance().contains(Item.ENCHANTED_SWORD)) {
            pw.println("You strike at the monster with your enchanted sword.");
            pw.println("With one fell swing of your sword the monster disintigrates and dies");
            pw.println("From the ashes of the monster you see a key");
            addItemToRoom(Item.KEY, new Item(Item.KEY, InventoryComponent.ROOT_PATH + "key.png"));
            takeItem(Item.KEY);
            monsterKilled = true;
        } else {
            pw.println("You strike at the monster with your sword but nothing happens.");
            pw.println("It strikes back with ferocity and kills you.");
            GameState.getInstance().setDead(true);
        }
        return sw.toString();
    }

    @Command(command = "openChest", name = "openChest")
    public String openChest() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        
        if (monsterKilled) {
            pw.println("You open the chest and inside it you find a scroll.");
            pw.println("The remaining word says 'Zam'");
            isChestOpened = true;
            GameState.getInstance().setRoom9WordFound(true);
            addItemToRoom(Item.WORD3, new Item(Item.WORD3, InventoryComponent.ROOT_PATH + "scroll3.png"));
            takeItem(Item.WORD3);
        } else {
            pw.println("You try to run for the chest and open it.");
            pw.println("But the monster attacks you before you can get to the chest.");
            GameState.getInstance().setDead(true);
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
        
        return useItem(itemName);
    }
    
    @Command(command="help", name="help")
    @Override
    public String help() {
        return super.help();
    }

    public boolean isMonsterKilled() {
        return monsterKilled;
    }

    public boolean isIsChestOpened() {
        return isChestOpened;
    }
}
