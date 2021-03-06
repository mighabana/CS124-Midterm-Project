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
import main_package.RoomComponent;
import room.Room9;

/**
 *
 * @author Luis Ligunas
 */
public class Room9Component extends RoomComponent {

    private BufferedImage screen, playerSprite, monster, chest, openedChest;
    private int playerX, playerY;

    private Room9 room9;
    
    public Room9Component(Room9 room9) {
        this.room9 = room9;
        try {
            screen = ImageIO.read(new File("src/assets/room6_screen.png"));
            playerSprite = ImageIO.read(new File("src/assets/character.png"));
            monster = ImageIO.read(new File("src/assets/monster1.png"));
            chest = ImageIO.read(new File("src/assets/gold_chest.png"));
            openedChest = ImageIO.read(new File("src/assets/opened_gold_chest.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        screen = UI_Manager.resize(screen, 2);
        playerSprite = UI_Manager.resize(playerSprite, 2);
        monster = UI_Manager.resize(monster, 2);
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

        g2d.drawImage(screen, 0, 0, null);
        g2d.drawImage(playerSprite, playerX, playerY, null);

        if (!room9.isMonsterKilled()) {
            g2d.drawImage(monster, 280, 110, null);
        }

        if (room9.isIsChestOpened()) {
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
        for (String line : getGameText().split("\n")) {
            g2d.drawString(line, 10, y += (g2d.getFontMetrics().getHeight() + 7));
        }
        
        InventoryComponent.paintComponent(g2d);
    }

}
