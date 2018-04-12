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

import anno.Direction;
import anno.Command;
import lab5.EnterCondition;
import lab5.GameState;
import lab5.Inventory;
import user_interface.Room_UI;

public class Room7 extends JComponent implements EnterCondition {
	
	private boolean isChestOpen = false;
	
	@Direction(command = Direction.NORTH)
	private Room6 room6;
	private Inventory inventory;
	private GameState gamestate;
	private BufferedImage screen1, screen2, playerSprite, chest, openedChest;
	private Room_UI roomui;
	private Boolean chestOpened;
	private String gameText = "";
	private int playerX, playerY;
	
	public Room7(Room_UI roomui) {
		this.roomui = roomui;
		try {
			screen1 = ImageIO.read(new File("src/assets/room6_screen"));
			screen2 = ImageIO.read(new File("src/assets/room7_screen"));
			playerSprite = ImageIO.read(new File("src/assets/character.png"));
			chest = ImageIO.read(new File("src/assets/gold_chest.png"));
			openedChest = ImageIO.read(new File("src/assets/opened_gold_chest"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		screen1 = resize(screen1, 2);
		screen2 = resize(screen2, 2);
		playerSprite = resize(playerSprite, 2);
		chest = resize(chest, 2);
		openedChest = resize(openedChest, 2);
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
   
        if(inventory.isBombTaken()) {
        		g2d.drawImage(screen2, 0, 0, null);
        } else {
        		g2d.drawImage(screen1,0,0,null);
        		if(!chestOpened) {
        			g2d.drawImage(chest, 260, 110, null);
        		} else {
        			g2d.drawImage(openedChest, 260, 110, null);
        		}
        }
       
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
		
		if(!chestOpened) {
			pw.println("You go up to the chest in the middle of the room and you pry it open.");
			pw.println("Inside the chest you see a set of explosives that you could probably use to blow something up!");
			chestOpened = true;
			this.repaint();
		} else {
			pw.println("Chest is already opened??");
		}
		
		return sw.toString();
	}
	
	//taking bomb chages the screen and drops you into the 8th room

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
