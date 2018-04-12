package main_package;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import ui.UI_Manager;

/**
 *
 * @author Luis Ligunas
 */
public class Item {
    public static final String SWORD = "sword",
                                TORCH = "torch",
                                BOMB = "bomb",
                                ENCHANTED_SWORD = "enchanted_sword",
                                WORD1 = "ala",
                                WORD2 = "ka",
                                WORD3 = "zam",
                                KEY = "key";
    
    private String name, path; //don't have a setter
    private BufferedImage bi;
    
    public Item(String name, String path) {
        this.name = name;
        this.path = path;
        try {
            bi = ImageIO.read(new File(path));
            if(name.equals("ala") || name.equals("ka") || name.equals("zam")) {
            		bi = UI_Manager.resize(bi, 0.1254902);
            } else {
            		bi = UI_Manager.resize(bi, 2);
            }
            UI_Manager.resize(bi, 2);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public BufferedImage getBi() {
        return bi;
    }
    
    
}
