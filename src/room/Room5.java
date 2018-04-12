package room;

import java.io.PrintWriter;
import java.io.StringWriter;
import main_package.Command;
import main_package.Direction;
import main_package.EnterCondition;
import main_package.EntryIntercepted;
import main_package.GameState;
import main_package.Inventory;
import main_package.Item;
import main_package.Room;
import main_package.Drawer;

/**
 *
 * @author Luis Ligunas
 */
@EntryIntercepted
public class Room5 extends Room implements EnterCondition {
    @Direction(command = Direction.SOUTH, name = "South")
    private Room3 room3;
    
    private Drawer runner;
    
    public Room5(Drawer runner) {
        this.runner = runner;
    }
    
    public String entry() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        GameState gameState = GameState.getInstance();
        
        gameState.setCurrRoom(this);
        
        if (gameState.allWordsFound()) {
            pw.println("You enter a long tunnel which opens into a large chamber.");
            pw.println("You can see an opening to the outside on the other side.");
            pw.println("As you walk towards it, a large dragon head peers from the opening.");
            pw.println("'What is the passphrase?' it asks.");
            pw.println(help());
        } else {
            pw.println("You are not allowed in this room.");
            pw.println("A ball of fire turns you to ash...");
            gameState.setDead(true);
        }

        return sw.toString();
    }
    
    @Command(command = "attack", name = "attack")
    public String attack() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        GameState gameState = GameState.getInstance();
        
        if (gameState.isSwordTaken()) {
            pw.println("You charge to attack the dragon brandishing your sword.");
            pw.println("The dragon breathes fire into the chamber turning you to ash... The End.");
            gameState.setDead(true);
        } else {
            pw.println("In a flash of wisdom, you resist.");
            pw.println("Only a fool would attack such a creature with his bare hands.");
        }

        return sw.toString();
    }
    
    @Command(command = "passphrase\\s+.*", name="passphrase <String>")
    public String passphrase() {
        String[] split = runner.getLastInput().split(" ");
        String word = split[split.length - 1];

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        GameState gameState = GameState.getInstance();
        
        if ((word.equalsIgnoreCase("AlaKaZam"))) {
            if (gameState.isBabyIsDead()) {
                pw.println("That is correct.");
                pw.println("The dragon breathes fire into the chamber turning you to ash for killing her baby... The End.");
                gameState.setDead(true);
            } else {
                pw.println("That is correct.");
                pw.println("The dragon allows you to pass and you escape...");
                pw.println("Congratulations on your 10pts.");
                gameState.setDead(true);
            }
        } else {
            pw.println("That is incorrect.");
            pw.println("The dragon breathes fire into the chamber turning you to ash... The End.");
            gameState.setDead(true);
        }

        return sw.toString();
    }
    
    @Command(command = "look", name = "look")
    public String look() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.println("There is no way around the dragon.");

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
    
    @Override
    public boolean canEnter() {
        return GameState.getInstance().allWordsFound();
    }

    @Override
    public String enterMessage() {
        return "You may enter Room 5.";
    }

    @Override
    public String unableToEnterMessage() {
        return "You may NOT enter Room 5.";
    }
}
