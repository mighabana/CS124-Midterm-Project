package main_package;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JFrame;

/**
 *
 * @author Luis Ligunas
 */
public class Runner {

    public static void main(String[] args) throws IOException {
        
        Drawer drawer = new Drawer();
        drawer.setSize(620, 450);
        drawer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawer.setVisible(true);	
        drawer.reset();
        
        /*Drawer drawer = new Drawer();
        System.out.println(drawer.reset());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String inp = br.readLine().trim();
            if (inp.equals("exit")) {
                break;
            }
            System.out.println(drawer.processInput(inp));
            if (GameState.getInstance().isDead()) {
                return;
            }
        }*/
    }
}
