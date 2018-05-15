package state;

import main_package.Drawer;
import main_package.GlobalCommands;

public interface State {
	public void setContext(Drawer runner);
	public String processInput(String input, GlobalCommands globalCommands);
	public String outputPrompt();
}
