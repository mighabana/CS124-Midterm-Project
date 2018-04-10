package lab5room;

import anno.Command;
import anno.Direction;
import anno.EntryIntercepted;
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
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class Room4 extends JComponent implements EnterCondition {

	@Direction(command = Direction.NORTH)
	private Room3 room3;
	
	private int x, y;
	private static Random rand;
	private BufferedImage screen, playerSprite, wizard;
	private int playerX, playerY;
	private String gameText;
	private Inventory inventory;
	private GameState gamestate;
	
	public Room4(Inventory i, GameState gs) {
		inventory = i;
		gamestate = gs;
		rand = new Random();
		
		try {
			screen = ImageIO.read(new File("src/assets/room1_screen.png"));
			playerSprite = ImageIO.read(new File("src/assets/character.png"));
			wizard = ImageIO.read(new File("src/assets/wizard.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		screen = resize(screen, 2);
		playerSprite = resize(playerSprite, 2);
		wizard = resize(wizard, 2);
		
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
        g2d.drawImage(wizard, 270, 110, null);
        
        g2d.setFont(new Font("Power Red and Green", Font.PLAIN, 20));
        g2d.setColor(Color.WHITE);
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

		x = rand.nextInt(998) + 2;
		y = rand.nextInt(998) + 2;

		pw.println("You find yourself in an empty circular room.  On the wall opposite, you see '" + x + " * " + y
				+ " = _' ");
		pw.println("You can say the 'answer (number)' no parenthesis");
		pw.println("You can 'look' around");
		pw.println();

		if (inventory.allScrollsFound())
			pw.println("You may now access secret Room5");

		return sw.toString();
	}

	@Command(command = "look")
	public String look() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		pw.println("You see nothing else of interest.");

		return sw.toString();
	}

	@Command(command = "answer\\s+\\d+\\s*")
	public String answer() {
		int ans = 0;

		// try {
		// Method m = this.getClass().getDeclaredMethod("answer");
		// String command = m.getAnnotation(Command.class).command();
		//
		// String[] split = command.split(" ");
		// ans = Integer.parseInt(split[split.length - 1]);
		//
		// } catch (Exception ex) {
		// System.out.println("An error occurred.");
		// System.out.println(ex);
		// }
		
		
		String[] split = runner.currInp().split(" ");

		ans = Integer.parseInt(split[split.length - 1]);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		if (ans == x * y) {
			inventory.setScroll3(true);

			pw.println("A low voice reverberates the word 'Ka' and fades away");

			if (inventory.allScrollsFound())
				pw.println("You may now access secret Room5");

		} else {
			pw.println("The door closes behind you and you are trapped here forever to contemplate the value of " + x
					+ "*" + y + "... The End");
			gamestate.setDead(true);
		}
		
		this.repaint();

		return sw.toString();
	}

	@Command(command = "lookAround")
	public String whatCanIDo() {
		String s = "You can say the 'answer (number)' no parenthesis" + "\nYou can 'look' around";
		return s;
	}

	@Override
	public boolean canEnter() {
		return true;
	}

	@Override
	public String enterMessage() {
		return "Well, I guess you can go in room4.";
	}

	@Override
	public String unableToEnterMessage() {
		return "Well, I guess you can't go in room4.";
	}
}
