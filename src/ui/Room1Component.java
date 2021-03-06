package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import main_package.RoomComponent;
import room.Room1;

/**
 *
 * @author Luis Ligunas
 */
public class Room1Component extends RoomComponent {

    private BufferedImage screen;
    private BufferedImage playerSprite;
    private int playerX;
    private int playerY;

    private Room1 room1;
    
    public Room1Component(Room1 room1) {
        this.room1 = room1;
        try {
            screen = ImageIO.read(new File("src/assets/room1_screen.png"));
            playerSprite = ImageIO.read(new File("src/assets/character.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        screen = UI_Manager.resize(screen, 2);
        playerSprite = UI_Manager.resize(playerSprite, 2);

        playerX = 220;
        playerY = 110;
    }

    @Override
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
        g2d.setColor(Color.white);
        g2d.drawString("Inventory", 485, 20);

        g2d.setFont(new Font("Power Red and Green", Font.PLAIN, 14));
        g2d.setColor(Color.black);
        int y = 250;
        for (String line : getGameText().split("\n")) {
            g2d.drawString(line, 10, y += (g2d.getFontMetrics().getHeight() + 7));
        }
        InventoryComponent.paintComponent(g2d);
    }
}
