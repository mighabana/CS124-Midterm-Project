package lab5;

public class GameState {
	private boolean dead;
	private boolean babyDead;
	
	public GameState() {
		dead = false;
		babyDead = false;
	}
	
	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public boolean isBabyDead() {
		return babyDead;
	}

	public void setBabyDead(boolean babyDead) {
		this.babyDead = babyDead;
	}
}
