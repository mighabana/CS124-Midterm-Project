package room;

import java.io.PrintWriter;
import java.io.StringWriter;
import main_package.Command;
import main_package.Direction;
import main_package.GameState;
import main_package.Room;
import main_package.Drawer;

public class Room1 extends Room {

    @Direction(command=Direction.NORTH, name="North")
    private Room2 room2;
    @Direction(command=Direction.WEST, name="West")
    private Room10 room10;
    
    private Drawer runner;
    
    public Room1(Drawer runner) {
        this.runner = runner;
    }
    
    public String entry() {
        GameState gameState = GameState.getInstance();
        gameState.setCurrRoom(this);
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.println("You find yourself inside a dark room.");
        pw.println("You see a bright light towards the North.");
        pw.println(help());
        
        if (gameState.allWordsFound())
            pw.println("(You may now access secret Room5)");
        
        return sw.toString();
    }
    
    @Command(command="help", name="help")
    @Override
    public String help() {
        return super.help();
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


