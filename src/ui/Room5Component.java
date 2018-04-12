package ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main_package.RoomComponent;
import room.Room5;

/**
 *
 * @author Luis Ligunas
 */
public class Room5Component extends RoomComponent {

    private BufferedImage screen1, playerSprite, mamaTroll;
    private int playerX, playerY;
    
    private Room5 room5;

    public Room5Component(Room5 room5) {
        this.room5 = room5;
        try {
            screen1 = ImageIO.read(new File("src/assets/room6_screen.png"));
            playerSprite = ImageIO.read(new File("src/assets/character.png"));
            mamaTroll = ImageIO.read(new File("src/assets/mama_troll.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        screen1 = UI_Manager.resize(screen1, 2);
        playerSprite = UI_Manager.resize(playerSprite, 2);
        mamaTroll = UI_Manager.resize(mamaTroll, 3);
        
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

        g2d.drawImage(screen1, 0, 0, null);
        g2d.drawImage(playerSprite, playerX, playerY, null);
        g2d.drawImage(mamaTroll, 220, 50, null);

        g2d.setFont(new Font("Power Red and Green", Font.PLAIN, 20));
        g2d.setColor(Color.WHITE);
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
