package lab5room;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.imageio.ImageIO;

import anno.Direction;

import lab5.EnterCondition;
import lab5.GameState;
import lab5.Inventory;
import user_interface.Room_UI;

public class Room6 implements EnterCondition {
	
	@Direction(command = Direction.WEST)
	private Room3 room3;
	
	@Direction(command = Direction.SOUTH)
	private Room7 room7;
	private Inventory inventory;
	private GameState gamestate;
	private Room_UI roomui;
	private BufferedImage screen, playerSprite;
	
	public Room6(Room_UI roomui) {
		this.roomui = roomui;
		
		try {
			screen = ImageIO.read(new File("src/assets/room1_screen.png"));
			playerSprite = ImageIO.read(new File("src/assets/character.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		screen = resize(screen, 2);
		playerSprite = resize(playerSprite, 2);
	}
	
	public BufferedImage resize(BufferedImage img, double ratio) {
		int newH = (int) (img.getHeight()*ratio);
		int newW = (int) (img.getWidth()*ratio);
		
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		
		return resized;
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
