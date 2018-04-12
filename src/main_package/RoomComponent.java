package main_package;

import javax.swing.JComponent;

/**
 *
 * @author Luis Ligunas
 */
public class RoomComponent extends JComponent {

    private String gameText;

    public RoomComponent() {
        gameText = "";
    }
    
    public void setGameText(String text) {
        gameText = text;
        repaint();
    }

    public String getGameText() {
        return gameText;
    }
    
    
}
