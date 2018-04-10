package lab5room;

import anno.Command;
import anno.Direction;
import lab5.EnterCondition;
import lab5.GameState;
import lab5.Inventory;
import lab5.Runner;

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

public class Room1 extends JComponent implements EnterCondition {

	@Direction(command = Direction.NORTH)
	private Room2 room2;
	private BufferedImage screen;
	private BufferedImage playerSprite;
	private Inventory inventory;
	private GameState gamestate;
	private String gameText = "";
	private int playerX;
	private int playerY;
	
	public Room1(Inventory in, GameState gs) {
		inventory = in;
		gamestate = gs;
		
		try{
			screen = ImageIO.read(new File("src/assets/room1_screen.png"));
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
        
        g2d.setFont(new Font("Power Red and Green", Font.PLAIN, 20));
        g2d.setColor(Color.white);
        g2d.drawString("Inventory", 485, 20);
        
        g2d.setFont(new Font("Power Red and Green", Font.PLAIN, 14));
        g2d.setColor(Color.black);
        g2d.drawString(gameText, 10, 250);
		
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
		pw.println("You find yourself inside a dark room.  You see a bright light towards the North.");
		pw.println("(Enter cardinal directions to move through rooms.)");

		if (inventory.allScrollsFound())
			pw.println("You may now access secret Room5");

		return sw.toString();
	}

	@Command(command = "lookAround")
	public String whatCanIDo() {
		return "You can go 'North' to room 2.";
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
