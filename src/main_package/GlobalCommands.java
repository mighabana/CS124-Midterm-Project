package main_package;

import java.util.HashMap;

public class GlobalCommands {
	
	private Drawer runner;
	
	public GlobalCommands(Drawer runner) {
		this.runner = runner;
	}
	
	@RegistrationCommand(name = "register")
	public void register(String username) {
		CareTaker ct = CareTaker.getInstance();
		ct.setUsername(username);
		
	}
	
	@RegistrationCommand(name = "quit")
	@Command(command="quit", name="quit")
	public void quit() {
		System.exit(0);
		
	}
	
	@Command(command="load", name="load")
	public void load() {
		GameState gs = GameState.getInstance();
		CareTaker ct = CareTaker.getInstance();
		HashMap<String, String[]> load = ct.getStates();
		ct.setUsername(load.get(CareTaker.USERNAME_KEY)[0]);		
		try {
			HashMap<Class, Object> map = runner.getLogicMap();
			gs.setCurrRoom(map.get(Class.forName(load.get(CareTaker.CUR_ROOM_KEY)[0])));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gs.setDead(Boolean.parseBoolean(load.get(CareTaker.IS_DEAD_KEY)[0]));
		gs.setBabyIsDead(Boolean.parseBoolean(load.get(CareTaker.IS_BABY_DEAD_KEY)[0]));
		gs.setSwordTaken(Boolean.parseBoolean(load.get(CareTaker.IS_SWORD_TAKEN_KEY)[0]));
		gs.setTorchTaken(Boolean.parseBoolean(load.get(CareTaker.IS_TORCH_TAKEN_KEY)[0]));
		gs.setSwordEnchanted(Boolean.parseBoolean(load.get(CareTaker.IS_SWORD_ENCHANTED_KEY)[0]));
		gs.setTorchUsed(Boolean.parseBoolean(load.get(CareTaker.IS_TORCH_USED_KEY)[0]));
		gs.setMonsterKilled(Boolean.parseBoolean(load.get(CareTaker.IS_MONSTER_KILLED_KEY)[0]));
		gs.setRoom3WordFound(Boolean.parseBoolean(load.get(CareTaker.IS_ROOM_3_WORD_FOUND_KEY)[0]));
		gs.setRoom4WordFound(Boolean.parseBoolean(load.get(CareTaker.IS_ROOM_4_WORD_FOUND_KEY)[0]));
		gs.setRoom9WordFound(Boolean.parseBoolean(load.get(CareTaker.IS_ROOM_9_WORD_FOUND_KEY)[0]));
		
		try {
			String[] s = load.get(CareTaker.LOCAL_STATES_KEY);
			runner.updateState(s, Class.forName(load.get(CareTaker.CUR_ROOM_KEY)[0]));
			runner.invokeSetText(runner.getUIMap().get(gs.getCurrRoom().getClass()),((Room) runner.getLogicMap().get(gs.getCurrRoom().getClass())).help());
			runner.reset();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Command(command="save", name="save")
	public void save() {
		CareTaker ct = CareTaker.getInstance();
		ct.saveState();
	}
	
	@Command(command="run", name="run")
	public void run() {
		
	}

}
