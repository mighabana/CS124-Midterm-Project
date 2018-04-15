package ui;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main_package.Inventory;
import main_package.Item;
import main_package.RoomComponent;
import room.Room7;

/**
 *
 * @author Luis Ligunas
 */
public class Room7Component extends RoomComponent {

    private BufferedImage screen1, screen2, playerSprite, chest, openedChest;
    private int playerX, playerY;
    
    private Room7 room7;
    
    public Room7Component(Room7 room7) {
        this.room7 = room7;
        
        try {
            screen1 = ImageIO.read(new File("src/assets/room6_screen.png"));
            screen2 = ImageIO.read(new File("src/assets/room7_screen.png"));
            playerSprite = ImageIO.read(new File("src/assets/character.png"));
            chest = ImageIO.read(new File("src/assets/gold_chest.png"));
            openedChest = ImageIO.read(new File("src/assets/opened_gold_chest.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        screen1 = UI_Manager.resize(screen1, 2);
        screen2 = UI_Manager.resize(screen2, 2);
        playerSprite = UI_Manager.resize(playerSprite, 2);
        chest = UI_Manager.resize(chest, 2);
        openedChest = UI_Manager.resize(openedChest, 2);
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

        if (Inventory.getInstance().contains(Item.BOMB)) {
            g2d.drawImage(screen2, 0, 0, null);
        } else {
            g2d.drawImage(screen1, 0, 0, null);
            if (!room7.isIsChestOpen()) {
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
        for (String line : getGameText().split("\n")) {
            g2d.drawString(line, 10, y += (g2d.getFontMetrics().getHeight() + 7));
        }
        InventoryComponent.paintComponent(g2d);
    }
}
