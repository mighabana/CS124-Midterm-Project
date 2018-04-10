package lab5room;

import java.io.PrintWriter;
import java.io.StringWriter;
import anno.Direction;

import lab5.EnterCondition;
import lab5.GameState;
import lab5.Inventory;
import lab5.Singleton;

public class Room6 implements EnterCondition {
	
	@Direction(command = Direction.WEST)
	private Room3 room3;
	
	@Direction(command = Direction.SOUTH)
	private Room7 room7;
	private Inventory inventory;
	private GameState gamestate;
	
	public Room6(Inventory i, GameState gs) {
		inventory = i;
		gamestate = gs;
	}
	

	public String entry() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		pw.println("You find your self in a pitch black room.");
		if(inventory.isTorchTaken()) {
			pw.println("You could probably use your torch to illuminate the room!");
		}
		
		return sw.toString();
	}
	
	public String look() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		if(inventory.isTorchTaken()) {
			pw.println("You see what seems to have been an entrance on the east side of the room but is now being covered by a big rock.");
			pw.println("To the south, you see another entrance that looks a bit run down but you could probably still slip through.");
		} else {
			pw.println("It's pitch black. You can't see anything, but a faint light from the entrance that you came from");
		}
		
		return sw.toString();
	}
	
	@Override
	public boolean canEnter() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String enterMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String unableToEnterMessage() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
