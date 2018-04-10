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

import javax.imageio.ImageIO;
import javax.swing.JComponent;

@EntryIntercepted
public class Room5 extends JComponent implements EnterCondition {

	@Direction(command = Direction.SOUTH)
	private Room3 room3;
	private BufferedImage screen1, screen2, playerSprite, torch;
	private int playerX, playerY;
	private String gameText = "";
	private Inventory inventory;
	private GameState gamestate;

	public Room5(Inventory i, GameState gs) {
		inventory = i;
		gamestate = gs;
		try {
			screen1 = ImageIO.read(new File("src/assets/room5.1_screen.png"));
			screen2 = ImageIO.read(new File("src/assets/room5.2_screen.png"));
			playerSprite = ImageIO.read(new File("src/assets/character.png"));
			torch = ImageIO.read(new File("src/assets/torch.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		screen1 = resize(screen1, 2);
		screen2 = resize(screen2, 2);
		playerSprite = resize(playerSprite, 2);
		torch = resize(torch, 2);
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
        		System.out.println("LOLOLOLOL");
        }
        
        
        g2d.drawImage(screen1, 0, 0, null);
        g2d.drawImage(playerSprite, playerX, playerY, null);
        
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

		if (inventory.allScrollsFound()) {
			pw.println("You enter a long tunnel which opens into a large chamber.");
			pw.println("You can see an opening to the outside on the other side.");
			pw.println("As you walk towards it, a large dragon head peers from the opening.");
			pw.println("'What is the passphrase?' it asks.");
			pw.println("You can say the 'passphrase String'");
			pw.println("You can 'attack' the dragon.");
			pw.println("You can 'look' around");
			pw.println();
		} else {
			pw.println("You are not allowed in this room.  A ball of fire turns you to ash...");
			gamestate.setDead(true);
		}

		return sw.toString();
	}

	@Command(command = "attack")
	public String attack() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		if (inventory.isSwordTaken()) {
			pw.println(
					"You charge to attack the dragon brandishing your sword.  The dragon breathes fire into the chamber turning you to ash... The End.");
			gamestate.setDead(true);
		} else {
			pw.println(
					"In a flash of wisdom, you resist.  Only a fool would attack such a creature with his bare hands.");
		}

		return sw.toString();
	}

	@Command(command = "passphrase\\s+.*")
	public String passphrase() {

		String[] split = runner.currInp().split(" ");
		String word = split[split.length - 1];

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		if ((word.equalsIgnoreCase("AlaKaZam"))) {
			if (gamestate.isBabyDead()) {
				pw.println(
						"That is correct.  The dragon breathes fire into the chamber turning you to ash for killing her baby... The End.");
				gamestate.setDead(true);
			} else {
				pw.println(
						"That is correct.  The dragon allows you to pass and you escape... Congratulations on your 10pts.");
				gamestate.setDead(true);
			}
		} else {
			pw.println("That is incorrect.  The dragon breathes fire into the chamber turning you to ash... The End.");
			gamestate.setDead(true);
		}

		return sw.toString();
	}

	@Command(command = "look")
	public String look() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		pw.println("There is no way around the dragon.");

		return sw.toString();
	}

	@Command(command = "lookAround")
	public String whatCanIDo() {
		String s = "You can say the 'passphrase String'" + "\nYou can 'attack' the dragon." + "\nYou can 'look' around";
		return s;
	}

	@Override
	public boolean canEnter() {
		return inventory.allScrollsFound();
	}

	@Override
	public String enterMessage() {
		return "Nice. Pasok.";
	}

	@Override
	public String unableToEnterMessage() {
		return "Need to find the words first, bruh.";
	}
}
