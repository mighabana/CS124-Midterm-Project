package ui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Luis Ligunas
 */
public class UI_Manager {

    public static BufferedImage resize(BufferedImage img, double ratio) {
        int newH = (int) (img.getHeight() * ratio);
        int newW = (int) (img.getWidth() * ratio);

        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return resized;
    }
}
