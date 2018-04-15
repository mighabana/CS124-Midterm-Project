package room;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;
import main_package.Command;
import main_package.Direction;
import main_package.GameState;
import main_package.Room;
import main_package.Drawer;
import main_package.Item;
import ui.InventoryComponent;

/**
 *
 * @author Luis Ligunas
 */
public class Room4 extends Room {
    @Direction(command=Direction.NORTH, name = "North")
    private Room3 room3;
    
    private Drawer runner;
    private int x, y;
    
    public Room4(Drawer runner) {
        this.runner = runner;
    }
    
    public String entry() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        
        Random rand = new Random();
        x = rand.nextInt(998) + 2;
        y = rand.nextInt(998) + 2;
        
        GameState.getInstance().setCurrRoom(this);
        
        pw.println("You find yourself in an empty circular room.");
        pw.println("On the wall opposite, you see '" + x + " * " + y + " = _' ");
        pw.println(help());

        if (GameState.getInstance().allWordsFound())
            pw.println("You may now access secret Room5");

        return sw.toString();
    }
    
    @Command(command = "look", name = "look")
    public String look() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        pw.println("You see nothing else of interest.");

        return sw.toString();
    }
    
    @Command(command = "answer\\s+\\d+\\s*", name = "answer <number>")
    public String answer() {
        String[] split = runner.getLastInput().split(" ");
        
        int ans = Integer.parseInt(split[split.length - 1]);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        GameState gameState = GameState.getInstance();
        
        if (ans == x*y) {
            gameState.setRoom4WordFound(true);
            addItemToRoom(Item.WORD2, new Item(Item.WORD2, InventoryComponent.ROOT_PATH + "scroll2.png"));
            takeItem(Item.WORD2);
            pw.println("A low voice reverberates the word 'Ka' and fades away");
            
            if (gameState.allWordsFound())
                pw.println("You may now access secret Room5");

        } else {
            pw.println("The door closes behind you and you are trapped here forever to contemplate the value of " + x + "*" + y + "... The End");
            gameState.setDead(true);
        }

        return sw.toString();
    }
    
    @Command(command="help", name="help")
    @Override
    public String help() {
        return "On the wall opposite, you see '" + x + " * " + y + " = _' \n" + super.help();
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
