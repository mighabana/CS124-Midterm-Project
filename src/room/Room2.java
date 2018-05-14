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
import ui.InventoryComponent;

/**
 *
 * @author Luis Ligunas
 */
public class Room2 extends Room {

    @Direction(command = Direction.SOUTH, name = "South")
    private Room1 room1;
    @Direction(command = Direction.EAST, name = "East")
    private Room3 room3;
    
    private Drawer runner;
    
    @LocalState(name = "inPool")
    private boolean inPool;
    @LocalState(name = "graveFound")
    private boolean graveFound;
    @LocalState(name = "torchFound")
    private boolean torchFound;
    
    public Room2(Drawer runner) {
        this.runner = runner;
    }
    
    public String entry() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        
        GameState.getInstance().setCurrRoom(this);
        
        pw.println("The door leads down some steps into an underground cave system.");
        pw.println("There is a deep pool in the middle of the cave.");
        pw.println("You see something shiny at the bottom of the pool.");
        pw.println(help());

        if (GameState.getInstance().allWordsFound())
            pw.println("You may now access secret Room5");
        
        return sw.toString();
    }
    
    @Command(command = "swim", name="swim")
    public String swim() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        inPool = true;
        addItemToRoom(Item.SWORD, new Item(Item.SWORD, InventoryComponent.ROOT_PATH + "sword.png"));

        pw.println("You find a shiny sword at the bottom.");

        return sw.toString();
    }
    
    @Command(command = "look", name = "look")
    public String look() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        if (!graveFound) {
            graveFound = true;
            pw.println("You find a pile rubble.  It looks like a shallow grave.");
            pw.println("You can command to 'dig' to see what is under it.");
        } else {
            pw.println("You see nothing else of interest.");
        }

        return sw.toString();
    }
    
    @Command(command = "dig", name = "dig")
    public String dig() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        GameState gameState = GameState.getInstance();
        
        if (!graveFound) {
            pw.println("You dig into the ground and disturb the home of a poisonous snake.");
            pw.println("It bites and you die... The End");
            gameState.setDead(true);
        } else {
            pw.println("You dig up the grave and find a skeleton holding a torch.");
            torchFound = true;
            addItemToRoom(Item.TORCH, new Item(Item.TORCH, InventoryComponent.ROOT_PATH + "torch.png"));
        }

        return sw.toString();
    }
    
    @Command(command="help", name="help")
    @Override
    public String help() {
        ArrayList<String> commands = getCommandsList();
        //remove things from commands and directions that are not allowed by state
        
        if(GameState.getInstance().isSwordTaken())
            commands.remove("swim");
        if(!graveFound)
            commands.remove("dig");
        
        String out = getFormattedDirections() + "\n" + Room.formatCommands(commands) + "\n" + getFormattedItems();
        
        return out;
    }
    
    @Command(command=Room.TAKE_ITEM_COMMAND, name=Room.TAKE_ITEM_NAME)
    public String takeItem() {
        String itemString = runner.getLastInput();
        itemString = itemString.substring(itemString.indexOf("take") + 5);
        
        GameState gameState = GameState.getInstance();
        
        if(itemString.equals(Item.SWORD)) {
            if(inPool && !gameState.isSwordTaken()) {
                gameState.setSwordTaken(true);
                takeItem(itemString);
                return "You take the bright shiny sword";
            }
            return "What sword?";
        }
        if(itemString.equals(Item.TORCH)) {
            if(torchFound && !gameState.isTorchTaken()) {
                gameState.setTorchTaken(true);
                takeItem(Item.TORCH);
                return "You take the torch from the weird skeleton.";
            }
            return "What torch?";
        }
        
        return takeItem(itemString);
    }
    
    @Command(command = Room.USE_ITEM_COMMAND, name = Room.USE_ITEM_NAME)
    public String useItem() {
        String itemName = runner.getLastInput();
        itemName = itemName.substring(itemName.indexOf("use") + 4);
        
        return useItem(itemName);
    }
    
    @Override
    public void updateStates(String[] localStates) {
    		inPool = Boolean.parseBoolean(localStates[0]);
    		graveFound = Boolean.parseBoolean(localStates[1]);
    		torchFound = Boolean.parseBoolean(localStates[2]);
    }
    
    @Override
    public boolean[] getStates() {
    		boolean[] output = {inPool, graveFound, torchFound};
    		
    		return output;
    }
}
