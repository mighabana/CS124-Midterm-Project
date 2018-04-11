package lab5room;

import java.io.PrintWriter;
import java.io.StringWriter;

import anno.Direction;
import anno.Command;
import lab5.EnterCondition;
import lab5.GameState;
import lab5.Inventory;
import user_interface.Room_UI;

public class Room7 implements EnterCondition {
	
	private boolean isChestOpen = false;
	
	@Direction(command = Direction.NORTH)
	private Room6 room6;
	private Inventory inventory;
	private GameState gamestate;
	private Room_UI roomui;
	
	public Room7(Room_UI roomui) {
		this.roomui = roomui;
	}
	
	public String entry() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		pw.println("You find yourself in a run down room.");
		
		return sw.toString();
	}
	
	@Command(command = "look")
	public String look() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		pw.println("You see a chest in the middle of the room. You can walk up to it and 'openChest'.");
		
		return sw.toString();
	}
	
	@Command(command = "openChest")
	public String openChest() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		if(!isChestOpen) {
			pw.println("You go up to the chest in the middle of the room and you pry it open.");
			pw.println("Inside the chest you see a set of explosives that you could probably use to blow something up!");
			isChestOpen = true;
		} else {
			
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
