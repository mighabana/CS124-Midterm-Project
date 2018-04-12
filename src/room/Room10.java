package room;

import main_package.Command;
import main_package.Direction;
import main_package.GameState;
import main_package.Room;
import main_package.Drawer;

/**
 *
 * @author Luis Ligunas
 */
public class Room10 extends Room {
    
    @Direction(command=Direction.EAST, name="East")
    private Room1 room1;
    
    private Drawer runner;

    public Room10(Drawer runner) {
        this.runner = runner;
    }

    public String entry() {
        GameState.getInstance().setCurrRoom(this);

        return "You enter a weird looking room with an old man standing in the middle of it.";

    }

    @Command(command = "look", name = "look")
    public String look() {

        return "You could probably go up and 'talk' to the old man?";
    }

    @Command(command = "talk", name = "talk")
    public String talk() {

        String nonsense = "Hi Im a random old man and I wish you luck in your journey";

        return cipher(nonsense);
    }

    public String cipher(String text) {
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        text = text.toUpperCase();

        String out = "";

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            String s = ch + "";
            if (alpha.indexOf(ch) >= 0) {
                System.out.println("" + ch);
                int num = alpha.indexOf(ch) + 1;
                num %= alpha.length();
                out += alpha.charAt(num) + "";
            } else {
                out += " ";
            }
        }

        out += "\n\n (HINT: CEASAR)";

        return out;
    }

    @Command(command = "help", name = "help")
    @Override
    public String help() {
        return super.help();
    }

    @Command(command = Room.TAKE_ITEM_COMMAND, name = Room.TAKE_ITEM_NAME)
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
