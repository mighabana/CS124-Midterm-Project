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

public class Room8 extends JComponent implements EnterCondition {
	
	@Direction(command = Direction.NORTH)
	private Room9 room9;
	
	private Inventory inventory = Inventory.getInstance();
	private GameState gamestate = GameState.getInstance();
	private String gameText = "";
	private Room_UI roomui;
	private BufferedImage screen, playerSprite;
	private boolean magicDiscovered;
	private int playerX, playerY;
	
	public Room8(Room_UI roomui) {
		this.roomui = roomui;
		
		try {
			screen = ImageIO.read(new File("src/assets/room8_screen.png"));
			playerSprite = ImageIO.read(new File("src/assets/character.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		screen = resize(screen,2);
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
	
	public String entry() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		pw.println("After falling you find yourself in another room.");
		pw.println("In the room you see a small fountain pouring down with a mysterious red liquid.");
		pw.println("In the northern end of the room you also see a wooden door");
		
		return sw.toString();
	}
	
	@Command(command = "enchantSword")
	public String look() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		if(inventory.isSwordTaken()) {
			pw.println("You take a closer look at the small fountain and you sense some magic eminating from it.");
			pw.println("You could probably dip your sword in it to enchant it!");
			pw.println("'enchantSword' to enchant your sword with magical properties");
			
			magicDiscovered = true;
		} else {
			pw.println("The fountain looks interesting I wonder what you could do with it?");
		}
		
		return sw.toString();
	}
	
	@Command(command = "enchantSword")
	public String enchantSword() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		if(magicDiscovered) {
			pw.println("You dip your sword into the magic fountain.");
			pw.println("It is now imbued with a magical power and it changes color! WOW!");
			inventory.setSword(false);
			inventory.setEnchantedSword(true);
		} else {
			pw.println("Huh?!?! What are you trying to do?");
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
