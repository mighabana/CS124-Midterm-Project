package state;

import java.awt.Component;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import main_package.Command;
import main_package.Direction;
import main_package.Drawer;
import main_package.GameState;
import main_package.GlobalCommands;
import main_package.Room;

public class RunningState implements State {

    Drawer runner;

    public void setContext(Drawer runner) {
        this.runner = runner;
    }

    @Override
    public String processInput(String input, GlobalCommands globalCommands) {
        if (GameState.getInstance().isDead()) {
            runner.reset();
            return null;
        }

        GameState gameState = GameState.getInstance();
        Object currRoom = gameState.getCurrRoom();
        Class currClass = currRoom.getClass();

        if (currClass.getSuperclass() != Room.class) {
            currClass = currClass.getSuperclass();
        }

        HashMap<Class, Object> logicMap = runner.getLogicMap();
        HashMap<Class, Object> uiMap = runner.getUIMap();

        //look for the directions
        Field[] fields = currClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Direction.class)) {

                String command = field.getAnnotation(Direction.class).command();

                if (input.matches(command)) {
                    Object room = logicMap.get(field.getType());
                    String prompt = (String) runner.invokeEntry(room);

                    Class roomClass = room.getClass();
                    if (roomClass.getSuperclass() != Room.class) {
                        roomClass = roomClass.getSuperclass();
                    }

                    if (prompt.indexOf("error") != 0) {
                        //success
                        runner.remove((Component) uiMap.get(currRoom.getClass()));
                        runner.add((Component) uiMap.get(roomClass));
                        runner.invokeSetText(uiMap.get(roomClass), prompt);

                        boolean[] tempStates = (boolean[]) runner.invokeMethod("getStates", room, null, null);

                        gameState.setLocalStates(tempStates);
                    } else {
                        runner.invokeSetText(uiMap.get(currClass), prompt);
                    }

                    runner.repaint();
                    return prompt;
                }
            }
        }

        //look for global commands
        for (Method method : GlobalCommands.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                String command = method.getAnnotation(Command.class).command();

                if (input.matches(command)) {
                    runner.invokeMethod(command, globalCommands, null, null);
                    return "";
                }
            }
        }

        //look for the commands
        Method[] methods = currClass.getDeclaredMethods();
        for (Method method : methods) {

            if (method.isAnnotationPresent(Command.class)) {
                String command = method.getAnnotation(Command.class).command();

                if (input.matches(command)) {
                    String outString;
                    try {
                        outString = (String) method.invoke(currRoom);

                        if (currClass.getSuperclass() != Room.class) {
                            currClass = currClass.getSuperclass();
                        }

                        runner.invokeSetText(uiMap.get(currClass), outString);

                        boolean[] tempStates = (boolean[]) runner.invokeMethod("getStates", logicMap.get(currClass), null, null);

                        gameState.setLocalStates(tempStates);

                        return outString;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    break;
                }
            }
        }

        // BUG: return within the above checks does not exit the method which makes the code below execute replacing the ui text
        runner.invokeSetText(uiMap.get(GameState.getInstance().getCurrRoom().getClass()), outputPrompt());

        return outputPrompt();
    }

    @Override
    public String outputPrompt() {
        return "That did nothing. Type 'help' to see what you can do.";
    }

}
