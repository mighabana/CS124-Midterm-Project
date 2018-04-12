package room;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import main_package.Command;
import main_package.Direction;
import main_package.GameState;
import main_package.Item;
import main_package.Room;
import main_package.Drawer;
import ui.InventoryComponent;

/**
 *
 * @author Luis Ligunas
 */
public class Room3 extends Room{
    
    @Direction(command = Direction.WEST, name = "West")
    private Room2 room2;
    @Direction(command = Direction.SOUTH, name = "South")
    private Room4 room4;
    @Direction(command = Direction.NORTH, name = "North")
    private Room5 room5;
    @Direction(command = Direction.EAST, name = "East")
    private Room6 room6;
    
    private Drawer runner;
    
    private boolean chestFound;
    
    public Room3(Drawer runner) {
        this.runner = runner;
    }
    
    public String entry() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        GameState.getInstance().setCurrRoom(this);
        
        pw.println("You enter a large cavern and hear deep laboured breathing.");
        pw.println("In the center of the chamber is small baby dragon sleeping on a big pile of gold coins.");
        pw.println(help());

        if (GameState.getInstance().allWordsFound())
            pw.println("You may now access secret Room5");
        
        return sw.toString();
    }
    
    @Command(command = "attack", name = "attack")
    public String attack() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        GameState gameState = GameState.getInstance();
        
        if (gameState.isSwordTaken()) {
            gameState.setBabyIsDead(true);
            pw.println("You charge the baby dragon with your bright shiny sword.");
            pw.println("You cleave its head clean off.");
            pw.println("You can 'look' around.");
        } else {
            pw.println("You charge the baby dragon and try to take in on with your bare hands.");
            pw.println("Its wakes and bites your head clean off... The End");
            gameState.setDead(true);
        }

        return sw.toString();
    }
    
    @Command(command = "look", name = "look")
    public String look() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        boolean babyDead = GameState.getInstance().isBabyIsDead();
        
        if (!chestFound) {
            chestFound = true;

            if (!babyDead) {
                pw.println("You quietly avoid the baby dragon and make your way to the other side of the chamber and find a chest.");
                pw.println("You can 'openChest'");
            } else {
                pw.println("You make your way to the other side of the chamber and find a chest.");
                pw.println("You can 'openChest'");
            }
        } else if (!babyDead) {
            pw.println("Other than the sleeping baby dragon.  There is nothing of interest.");
        } else {
            pw.println("There is nothing of interest.");
        }

        return sw.toString();
    }
    
    @Command(command = "openChest", name = "openChest")
    public String openChest() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        
        GameState gameState = GameState.getInstance();
        
        if (chestFound) {
            gameState.setRoom3WordFound(true);
            
            addItemToRoom(Item.WORD1, new Item(Item.WORD1, InventoryComponent.ROOT_PATH + "scroll1.png"));
            takeItem(Item.WORD1);
            
            pw.println("Inside is a book.  A page is ear-marked and the word 'Ala' written in blood.");

            if (gameState.allWordsFound())
                pw.println("You may now access secret Room5");

        } else {
            pw.println("What chest?");
        }

        return sw.toString();
    }
    
    @Command(command = "help", name = "help")
    @Override
    public String help() {
        ArrayList<String> commands = getCommandsList();
        //remove things from commands and directions that are not allowed by state
        
        if (!chestFound)
            commands.remove("openChest");
        if(GameState.getInstance().isBabyIsDead())
            commands.remove("attack");
        
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
}
