package lab5room;

import anno.Command;
import anno.Direction;
import lab5.EnterCondition;
import lab5.GameState;
import lab5.Inventory;
import lab5.Runner;
import user_interface.Room_UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class Room2 extends JComponent implements EnterCondition {

	@Direction(command = Direction.SOUTH)
	private Room1 room1;
	@Direction(command = Direction.EAST)
	private Room3 room3;

	private boolean inPool, graveFound;
	
	private BufferedImage screen, playerSprite;
	private int playerX, playerY;
	private String gameText = "";
	private Inventory inventory = Inventory.getInstance();
	private GameState gamestate = GameState.getInstance();
	private Room_UI roomui;
	
	public Room2(Room_UI roomui) {
		this.roomui = roomui;
		
		try {
			screen = ImageIO.read(new File("src/assets/room2_screen.png"));
			playerSprite = ImageIO.read(new File("src/assets/character.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		screen = resize(screen, 2);
		playerSprite = resize(playerSprite, 2);
		playerX = 220;
		playerY = 110;
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
		
        try {
    			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/pkmnfl.ttf")));	
        } catch (FontFormatException | IOException e) {
    			e.printStackTrace();
    		}
        
        g2d.drawImage(screen, 0, 0, null);
        g2d.drawImage(playerSprite, playerX, playerY, null);
        
        
        g2d.setFont(new Font("Power Red and Green", Font.PLAIN, 20));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Inventory", 485, 20);
        
        g2d.setFont(new Font("Power Red and Green", Font.PLAIN, 14));
        g2d.setColor(Color.black);
        int y = 250;
        for(String line: gameText.split("\n")) {
			g2d.drawString(line, 10, y += (g2d.getFontMetrics().getHeight() + 7));
		}
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
	
	public void setGameText(String text) {
		gameText = text;
		this.repaint();
	}

	public String entry() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		pw.println(
				"The door leads down some steps into an underground cave system. There is a deep pool in the middle of the cave.");
		pw.println("You see something shiny at the bottom of the pool.");
		pw.println("You can command to 'swim' in the pool.");
		pw.println("You can command to 'look' around.");

		if (inventory.allScrollsFound())
			pw.println("You may now access secret Room5");

		return sw.toString();
	}

	@Command(command = "swim")
	public String swim() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		inPool = true;

		pw.println("You find a shiny sword at the bottom.");
		pw.println("You can command to 'takeSword'");

		this.repaint();
		
		return sw.toString();
	}

	@Command(command = "takeSword")
	public String takeSword() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		if (inPool && !inventory.isSwordTaken()) {
			inventory.setSword(true);
			pw.println("You take the bright shiny sword.");
		} else {
			pw.println("What sword?");
		}
		
		this.repaint();

		return sw.toString();
	}

	@Command(command = "look")
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

	@Command(command = "dig")
	public String dig() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		if (!graveFound) {
			pw.println(
					"You dig into the ground and disturb the home of a poisonous snake.  It bites and you die... The End");
			gamestate.setDead(true);
		} else {
			inventory.setScroll1(true);
			pw.println(
					"You dig up the grave and find a skeleton holding a scroll.  It contains 3 words but 2 are unreadable.  The remaining word says 'Zam'");

			if (inventory.allScrollsFound())
				pw.println("You may now access secret Room5");
		}
		
		this.repaint();

		return sw.toString();
	}

	@Command(command = "lookAround")
	public String whatCanIDo() {
		String s = "You can command to 'swim' in the pool." + "\nYou can command to 'look' around.";

		if (inPool)
			s += "You can command to 'takeSword'";
		if (graveFound)
			s += "You can command to 'dig'";

		return s;
	}

	@Override
	public boolean canEnter() {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
																		// Tools | Templates.
	}

	@Override
	public String enterMessage() {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
																		// Tools | Templates.
	}

	@Override
	public String unableToEnterMessage() {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
																		// Tools | Templates.
	}
}
