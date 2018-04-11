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

public class Room3 extends JComponent implements EnterCondition {

	@Direction(command = Direction.WEST)
	private Room2 room2;
	@Direction(command = Direction.SOUTH)
	private Room4 room4;
	@Direction(command = Direction.NORTH)
	private Room5 room5;

	private boolean babyDead, chestFound;
	private BufferedImage screen, playerSprite, babyTroll, chest;
	private int playerX, playerY;
	private String gameText = "";
	private Inventory inventory = Inventory.getInstance();
	private GameState gamestate = GameState.getInstance();
	private Room_UI roomui;

	public Room3(Room_UI roomui) {
		this.roomui = roomui;
		
		try {
			screen = ImageIO.read(new File("src/assets/room1_screen.png"));
			playerSprite = ImageIO.read(new File("src/assets/character.png"));
			babyTroll = ImageIO.read(new File("src/assets/baby_troll.png"));
			chest = ImageIO.read(new File("src/assets/gold_chest.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		screen = resize(screen, 2);
		playerSprite = resize(playerSprite, 2);
		babyTroll = resize(babyTroll, 2);
		chest = resize(chest, 2);
		
		playerX = 220;
		playerY = 110;
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
        
        try {
        		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        		ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/pkmnfl.ttf")));	
        } catch (FontFormatException | IOException e) {
        		e.printStackTrace();
        }
        
        
        g2d.drawImage(screen, 0, 0, null);
        g2d.drawImage(playerSprite, playerX, playerY, null);
        g2d.drawImage(babyTroll, 270, 110, null);
		g2d.drawImage(chest, 400, 110, null);
		
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

		pw.println("You enter a large cavern and hear deep laboured breathing.");
		pw.println("In the center of the chamber is small baby dragon sleeping on a big pile of gold coins.");
		pw.println("You can 'attack' the dragon.");
		pw.println("You can 'look' around.");
		pw.println();

		if (inventory.allScrollsFound())
			pw.println("You may now access secret Room5");

		return sw.toString();
	}
	
	

	@Command(command = "attack")
	public String attack() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		if (inventory.isSwordTaken()) {
			babyDead = true;
			pw.println("You charge the baby dragon with your bright shiny sword.  You cleave its head clean off.");
			pw.println("You can 'look' around.");
		} else {
			pw.println(
					"You charge the baby dragon and try to take in on with your bare hands.  Its wakes and bites your head clean off... The End");
			gamestate.setDead(true);
		}

		return sw.toString();
	}

	@Command(command = "look")
	public String look() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		if (!chestFound) {
			chestFound = true;

			if (!babyDead) {
				pw.println(
						"You quietly avoid the baby dragon and make your way to the other side of the chamber and find a chest.");
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

	@Command(command = "openChest")
	public String openChest() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		if (chestFound) {
			inventory.setScroll2(true);
			pw.println("Inside is a book.  A page is ear-marked and the word 'Ala' written in blood.");

			if (inventory.allScrollsFound())
				pw.println("You may now access secret Room5");

		} else {
			pw.println("What chest?");
		}
		
		this.repaint();

		return sw.toString();
	}

	public boolean isBabyDead() {
		return babyDead;
	}

	@Command(command = "lookAround")
	public String whatCanIDo() {
		String s = "You can 'attack' the dragon." + "\nYou can 'look' around.";

		if (chestFound)
			s += "You can 'openChest'";

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
