package lab5room;

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

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import anno.Command;
import anno.Direction;
import lab5.EnterCondition;
import lab5.GameState;
import lab5.Inventory;
import user_interface.Room_UI;

public class Room9 extends JComponent implements EnterCondition {

	@Direction(command = Direction.WEST)
	private Room5 room5;
	//you must use key to unlock door before you can go WEST
	
	private Inventory inventory = Inventory.getInstance();
	private GameState gamestate = GameState.getInstance();
	private String gameText = "";
	private Room_UI roomui;
	private BufferedImage screen, playerSprite, monster, chest, openedChest;
	private boolean monsterKilled;
	private boolean isChestOpened;
	private int playerX, playerY;
	
	public Room9(Room_UI roomui) {
		this.roomui = roomui;
		
		try {
			screen = ImageIO.read(new File("src/assets/room6_screen.png"));
			playerSprite = ImageIO.read(new File("src/assets/character.png"));
			monster = ImageIO.read(new File("src/assets/monster1.png"));
			chest = ImageIO.read(new File("src/assets/gold_chest.png"));
			openedChest = ImageIO.read(new File("src/assets/opened_gold_chest.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		screen = resize(screen, 2);
		playerSprite = resize(playerSprite, 2);
		monster = resize(monster, 2);
		chest = resize(chest, 2);
		openedChest = resize(openedChest, 2);
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
   
        g2d.drawImage(screen,0,0,null);
        g2d.drawImage(playerSprite, playerX, playerY, null);
        
        if(!monsterKilled) {
        		g2d.drawImage(monster, 280, 110, null);
        }
        
        if(isChestOpened) {
        		g2d.drawImage(openedChest, 250, 50, null);
        } else {
        		g2d.drawImage(chest, 250, 50, null);
        }
        
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
	
	public String entry() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		pw.println("You find yourself in a smelly and mysterious room.");
		pw.println("Ahead of you you see a gholish monster that looks ready to strike.");
		pw.println("You also see a golden chest nearby, and what looks like a doorway behind the monster");
		
		
		return sw.toString();
	}
	
	@Command(command = "look")
	public String look() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		if(!monsterKilled) {
			pw.println("You might want to deal with the monster first!");
		} else {
			pw.println("You can open the chest in peace now.");
			pw.println("You notice that the door upahead is locked.");
		}
		
		return sw.toString();
	}
	
	@Command(command = "killMonster")
	public String killMonster() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		if(inventory.isEnchantedSwordTaken()) {
			pw.println("You strike at the monster with your enchanted sword.");
			pw.println("With one fell swing of your sword the monster disintigrates and dies");
			pw.println("From the ashes of the monster you see a key");
			//take key to use to open the door in this room
		} else {
			pw.println("You strike at the monster with your sword but nothing happens.");
			pw.println("It strikes back with ferocity and kills you.");
			gamestate.setDead(true);
		}
		return sw.toString();
	}
	
	@Command(command = "openChest")
	public String openChest() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		if(monsterKilled) {
			pw.println("You open the chest and inside it you find the last scroll.");
			//take scroll
		} else {
			pw.println("You try to run for the chest and open it.");
			pw.println("But the monster attacks you before you can get to the chest.");
			gamestate.setDead(true);
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
