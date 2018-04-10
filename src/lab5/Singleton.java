package lab5;

public class Singleton {
	
	private static Inventory inventory;
	private static GameState gamestate;
	
	public static Inventory getInventory() {
		if(inventory == null) {
			inventory = new Inventory();
		}
		
		return inventory;
	}
	
	public static GameState getGameState() {
		if(gamestate == null) {
			gamestate = new GameState();
		}
		
		return gamestate;
	}

}
