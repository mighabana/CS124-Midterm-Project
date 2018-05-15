package main_package;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class CareTaker {

    private static CareTaker careTaker;
    private GameState state;
    private String username;
    public static final String USERNAME_KEY = "username";
    public static final String CUR_ROOM_KEY = "currRoom";
    public static final String IS_DEAD_KEY = "isDead";
    public static final String IS_BABY_DEAD_KEY = "isBabyDead";
    public static final String IS_SWORD_TAKEN_KEY = "isSwordTaken";
    public static final String IS_TORCH_TAKEN_KEY = "isTorchTaken";
    public static final String IS_SWORD_ENCHANTED_KEY = "isSwordEnchanted";
    public static final String IS_TORCH_USED_KEY = "isTorchUsed";
    public static final String IS_MONSTER_KILLED_KEY = "isMonsterKilled";
    public static final String IS_ROOM_3_WORD_FOUND_KEY = "isRoom3WordFound";
    public static final String IS_ROOM_4_WORD_FOUND_KEY = "isRoom4WordFound";
    public static final String IS_ROOM_9_WORD_FOUND_KEY = "isRoom9WordFound";
    public static final String LOCAL_STATES_KEY = "localStates";

    private CareTaker() {

    }

    public static CareTaker getInstance() {
        if (careTaker == null) {
            careTaker = new CareTaker();
        }

        return careTaker;
    }

    public void saveState() {
        state = GameState.getInstance();
        BufferedWriter bw = null;
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(new File("Lab5-savefile.txt")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String line = "";
        StringBuilder output = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        sb.append(USERNAME_KEY + ":" + username + ",");
        sb.append(CUR_ROOM_KEY + ":" + state.getCurrRoom().getClass().getName() + ",");
        sb.append(IS_DEAD_KEY + ":" + state.isDead() + ",");
        sb.append(IS_BABY_DEAD_KEY + ":" + state.isBabyIsDead() + ",");
        sb.append(IS_SWORD_TAKEN_KEY + ":" + state.isSwordTaken() + ",");
        sb.append(IS_TORCH_TAKEN_KEY + ":" + state.isTorchTaken() + ",");
        sb.append(IS_SWORD_ENCHANTED_KEY + ":" + state.isSwordEnchanted() + ",");
        sb.append(IS_TORCH_USED_KEY + ":" + state.isTorchUsed() + ",");
        sb.append(IS_MONSTER_KILLED_KEY + ":" + state.isMonsterKilled() + ",");
        sb.append(IS_ROOM_3_WORD_FOUND_KEY + ":" + state.isRoom3WordFound() + ",");
        sb.append(IS_ROOM_4_WORD_FOUND_KEY + ":" + state.isRoom4WordFound() + ",");
        sb.append(IS_ROOM_9_WORD_FOUND_KEY + ":" + state.isRoom9WordFound() + ",");
        sb.append(LOCAL_STATES_KEY + ":");

        boolean[] localStates = state.getLocalStates();

        if (localStates != null) {
            for (int i = 0; i < localStates.length; i++) {

                if (i == localStates.length - 1) {
                    sb.append(localStates[i] + "\n");
                } else {
                    sb.append(localStates[i] + ";");
                }
            }
        } else {
        		sb.append("\n");
        }
        
        boolean update = false;
        try {
            line = reader.readLine();
            while (line != null) {
                if (line.indexOf(USERNAME_KEY + ":" + username) == 0) {
                    update = true;

                    line =  sb.toString();
                    System.out.println(line);
                    output.append(line + "\n");
                } else {
                    System.out.println(line);
                    output.append(line + "\n");
                }

                line = reader.readLine();
            }

            if (!update) {
                output.append(sb.toString() + "\n");
            }

            reader.close();
            
            bw = new BufferedWriter(new FileWriter(new File("Lab5-savefile.txt")));
            bw.write(output.toString());
            bw.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public HashMap<String, String[]> getStates() {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(new File("Lab5-savefile.txt")));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String line = "";
        HashMap<String, String[]> map = new HashMap<String, String[]>();

        try {
            line = reader.readLine();
            while (line != null) {
            		System.out.println("1");
                if (line.indexOf(USERNAME_KEY + ":" + username) == 0) {
                    String[] output = line.split(",");
                    for (int i = 0; i < output.length; i++) {
                        String[] temp1 = output[i].split(":");
                        String[] temp2 = null;
                        if(temp1.length > 1) {
                        		temp2 = new String[] {temp1[1]};
                        		if (temp1[1].contains(";")) {
                            		temp2 = temp1[1].split(";");
                        		}
                        }
                        map.put(temp1[0], temp2);
                    }
                    break;
                }
                line = reader.readLine();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return map;
    }

    public void setUsername(String name) {
        username = name;
    }

    public String getUsername() {
        return username;
    }

}
