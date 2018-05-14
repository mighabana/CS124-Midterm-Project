package main_package;

public interface State {
	public void setContext(Drawer runner);
	public String processInput(String input, GlobalCommands globalCommands);
	public String outputPrompt();
}
