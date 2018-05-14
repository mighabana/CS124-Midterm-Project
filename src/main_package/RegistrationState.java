package main_package;

import java.lang.reflect.Method;

public class RegistrationState implements State {
	
	Drawer runner;
	
	public void setContext(Drawer runner) {
		this.runner = runner;
	}

	@Override
	public String processInput(String input, GlobalCommands globalCommands) {
		
		for(Method m : GlobalCommands.class.getDeclaredMethods()) {
			
			if(m.isAnnotationPresent(RegistrationCommand.class)) {
				if(input.contains("register")) {
					Class[] inpClass = {String.class};
					Object[] inpArgs = {input.substring(9)};
					invokeMethod("register", globalCommands, inpClass, inpArgs);
					runner.setState(new RunningState());
				} else if (input.contains("quit")) {
					invokeMethod("quit", globalCommands, null, null);
					runner.setState(new RunningState());
				}
			}
		}
		
		return "";
		
	}
	
    private static Object invokeMethod(String method, Object obj, Class[] classes, Object[] args) {
        try {
            Method m = obj.getClass().getMethod(method, classes);
            m.setAccessible(true); //line is unnecessary when method is not private

            return m.invoke(obj, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

	@Override
	public String outputPrompt() {
		String output = "You may do the following actions: \"quit\", or \"register <name>\".";
		return output;
	}

}
