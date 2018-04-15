package ui;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main_package.RoomComponent;
import room.Room6;

/**
 *
 * @author Luis Ligunas
 */
public class Room6Component extends RoomComponent {

    private BufferedImage screen1, screen2, playerSprite, torch;
    private Room6 room6;

    public Room6Component(Room6 room6) {
        this.room6 = room6;
        try {
            screen1 = ImageIO.read(new File("src/assets/room5.1_screen.png"));
            screen2 = ImageIO.read(new File("src/assets/room5.2_screen.png"));
            playerSprite = ImageIO.read(new File("src/assets/character.png"));
            torch = ImageIO.read(new File("src/assets/torch.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        screen1 = UI_Manager.resize(screen1, 2);
        screen2 = UI_Manager.resize(screen2, 2);
        playerSprite = UI_Manager.resize(playerSprite, 2);
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

        if (!room6.isTorchUsed()) {
            g2d.drawImage(screen1, 0, 0, null);
        } else {
            g2d.drawImage(screen2, 0, 0, null);
        }
        g2d.drawImage(playerSprite, 220, 110, null);

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
