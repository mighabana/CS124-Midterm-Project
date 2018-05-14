package main_package;


public class GameState {
    private static GameState gameState;
    
    private String username;
    private Object currRoom;
    private boolean dead, babyIsDead, swordTaken, torchTaken, swordEnchanted, torchUsed, monsterKilled;
    private boolean room3WordFound, room4WordFound, room9WordFound;
    private boolean[] localStates;
    
    //private GameState() 
    
    public static GameState getInstance() {
        if(gameState == null)
            gameState = new GameState();
        return gameState;
    }

    public Object getCurrRoom() {
        return currRoom;
    }

    public void setCurrRoom(Object currRoom) {
        this.currRoom = currRoom;
    }
    
    public boolean allWordsFound() {
        return room3WordFound && room4WordFound && room9WordFound;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setBabyIsDead(boolean babyIsDead) {
        this.babyIsDead = babyIsDead;
    }

    public boolean isBabyIsDead() {
        return babyIsDead;
    }

    public void setRoom3WordFound(boolean room3WordFound) {
        this.room3WordFound = room3WordFound;
    }
   

    public boolean isRoom3WordFound() {
		return room3WordFound;
	}


	public void setRoom4WordFound(boolean room4WordFound) {
        this.room4WordFound = room4WordFound;
    }
	
	public boolean isRoom4WordFound() {
		return room4WordFound;
	}

    public boolean isSwordTaken() {
        return swordTaken;
    }

    public void setSwordTaken(boolean swordTaken) {
        this.swordTaken = swordTaken;
    }

    public boolean isTorchTaken() {
        return torchTaken;
    }

    public void setTorchTaken(boolean torchTaken) {
        this.torchTaken = torchTaken;
    }

    public boolean isSwordEnchanted() {
        return swordEnchanted;
    }

    public void setSwordEnchanted(boolean swordEnchanted) {
        this.swordEnchanted = swordEnchanted;
    }

    public boolean isRoom9WordFound() {
        return room9WordFound;
    }

    public void setRoom9WordFound(boolean room9WordFound) {
        this.room9WordFound = room9WordFound;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean[] getLocalStates() {
		return localStates;
	}

	public void setLocalStates(boolean[] localStates) {
		this.localStates = localStates;
	}

	public boolean isTorchUsed() {
		return torchUsed;
	}

	public void setTorchUsed(boolean torchUsed) {
		this.torchUsed = torchUsed;
	}

	public boolean isMonsterKilled() {
		return monsterKilled;
	}

	public void setMonsterKilled(boolean monsterKilled) {
		this.monsterKilled = monsterKilled;
	}
    
    
}
