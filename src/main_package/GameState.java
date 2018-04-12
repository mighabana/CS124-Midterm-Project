package main_package;


public class GameState {
    private static GameState gameState;
    private Object currRoom;
    
    private boolean dead, babyIsDead, swordTaken, torchTaken, swordEnchanted;
    private boolean room3WordFound, room4WordFound, room9WordFound;
    
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
        return true;
        //return room3WordFound && room4WordFound && room9WordFound;
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

    public void setRoom4WordFound(boolean room4WordFound) {
        this.room4WordFound = room4WordFound;
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
    
    
}
