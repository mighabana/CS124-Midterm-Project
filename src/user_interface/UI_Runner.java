package user_interface;

import javax.swing.JFrame;

import lab5room.*;

public class UI_Runner {
	public static void main(String[] args) {
		
		JFrame frame = new Room_UI();
		frame.setSize(600, 385);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);	
	}
}
