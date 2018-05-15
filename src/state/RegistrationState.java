package state;

import java.lang.reflect.Method;
import main_package.Drawer;
import main_package.GameState;
import main_package.GlobalCommands;
import main_package.RegistrationCommand;
import main_package.Room;

public class RegistrationState implements State {

    Drawer runner;

    public void setContext(Drawer runner) {
        this.runner = runner;
    }

    @Override
    public String processInput(String input, GlobalCommands globalCommands) {

        for (Method m : GlobalCommands.class.getDeclaredMethods()) {
            //TODO: make this 
            if (m.isAnnotationPresent(RegistrationCommand.class)) {
                if (input.indexOf("register ") == 0 && input.length() - "register".length() >= 2) {
                    Class[] inpClass = {String.class};
                    Object[] inpArgs = {input.substring(9)};
                    invokeMethod("register", globalCommands, inpClass, inpArgs);
                    //invokeMethod("load", globalCommands, null, null);
                    runner.setState(new RunningState());
                    
                    //you need to load somewhere here
                    
                    Class currRoomClass = GameState.getInstance().getCurrRoom().getClass();
                    runner.invokeSetText(runner.getUIMap().get(currRoomClass), ((Room) runner.getLogicMap().get(currRoomClass)).help());
                    return "";
                } else if (input.contains("quit")) {
                    invokeMethod("quit", globalCommands, null, null); //terminates program
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
