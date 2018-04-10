package lab5;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Inventory {

	private static Inventory inventory;
	private boolean[] bool;
	private ArrayList<BufferedImage> inventoryItems = new ArrayList<BufferedImage>(7);
	
	public Inventory() {
		bool = new boolean[7];
		try {
			inventoryItems.add(ImageIO.read(new File("src/assets/scroll1.png")));
			inventoryItems.add(ImageIO.read(new File("src/assets/scroll2.png")));
			inventoryItems.add(ImageIO.read(new File("src/assets/scroll3.png")));
			inventoryItems.add(ImageIO.read(new File("src/assets/torch.png")));
			inventoryItems.add(ImageIO.read(new File("src/assets/sword.png")));
			inventoryItems.add(ImageIO.read(new File("src/assets/enchanted_sword.png")));
			inventoryItems.add(ImageIO.read(new File("src/assets/bomb.png")));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < inventoryItems.size(); i++) {
			if(i < 3 && inventoryItems.get(i) != null) {
				inventoryItems.set(i, resize(inventoryItems.get(i), 0.1254902));
			} else {
				inventoryItems.set(i, resize(inventoryItems.get(i), 2));
			}
		}
	}
	
	public ArrayList<BufferedImage> getInventoryItems() {
		return inventoryItems;
	}
	
	public boolean[] getBool() {
		return bool;
	}
			
	public boolean allScrollsFound() {
		return (bool[0] && bool[1] && bool[2]);
	}
	
	public boolean isScroll1Taken() {
		return bool[0];
	}

	public void setScroll1(boolean scroll1) {
		this.bool[0] = scroll1;
	}

	public boolean isScroll2Taken() {
		return bool[1];
	}

	public void setScroll2(boolean scroll2) {
		this.bool[1] = scroll2;
	}

	public boolean isScroll3Taken() {
		return bool[2];
	}

	public void setScroll3(boolean scroll3) {
		this.bool[2] = scroll3;
	}

	public boolean isTorchTaken() {
		return bool[3];
	}

	public void setTorch(boolean torch) {
		this.bool[3] = torch;
	}

	public boolean isSwordTaken() {
		return bool[4];
	}

	public void setSword(boolean sword) {
		this.bool[4] = sword;
	}

	public boolean isEnchantedSwordTaken() {
		return bool[5];
	}

	public void setEnchantedSword(boolean enchantedSword) {
		this.bool[5] = enchantedSword;
	}

	public boolean isBombTaken() {
		return bool[6];
	}

	public void setBomb(boolean bomb) {
		this.bool[6] = bomb;
	}
	
	public BufferedImage resize(BufferedImage img, double ratio) {
		int newH = (int) (img.getHeight()*ratio);
		int newW = (int) (img.getWidth()*ratio);
		
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		
		return resized;
	}
	
	public static Inventory getInstance() {
		if(inventory == null)
			inventory = new Inventory();
		
		return inventory;
	}

}
