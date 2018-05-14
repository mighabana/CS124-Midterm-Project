package main_package;

public class RunningState implements State {
	
	Drawer runner;
	
	public void setContext(Drawer runner) {
		this.runner = runner;
	}

	@Override
	public String processInput(String input, GlobalCommands globalCommands) {
		
		return input;
	}

	@Override
	public String outputPrompt() {
		return null;
	}

}
