package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JComponent;
import main_package.Inventory;
import main_package.Item;

/**
 *
 * @author Luis Ligunas
 */
public class InventoryComponent {
    public static final String ROOT_PATH = "src/assets/";
    
    public static void paintComponent(Graphics2D g2d) {
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);

        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/pkmnfl.ttf")));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        //g2d.drawImage(playerSprite, 220, 110, null);

        g2d.setFont(new Font("Power Red and Green", Font.PLAIN, 20));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Inventory", 485, 20);
        
        int[][] coordinates = {
            {475, 75},
            {550, 75},
            {475, 152},
            {550, 152},
            {475, 229},
            {550, 229},
            {475, 306},
            {550, 306},
        };
        
        ArrayList<Item> items = Inventory.getInstance().getItems();
        
        for(int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            g2d.drawImage(item.getBi(), coordinates[i][0], coordinates[i][1], null);
        }
        
    }
}
