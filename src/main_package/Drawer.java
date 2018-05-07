package main_package;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import static main_package.EntryInterceptor.entry;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import room.*;
import ui.*;

public class Drawer extends JFrame {

    private HashMap<Class, Object> logicMap;
    private HashMap<Class, Object> uiMap;

    private JTextField textField;
    
    public static Drawer drawer;
    private String lastInput;

    public String reset() {
        textField = new JTextField();
        setLayout(new BorderLayout());
        
        final String packageName = "room";
        FastClasspathScanner scanner = new FastClasspathScanner(packageName);
        ScanResult result = scanner.scan();
        List<String> allClasses = result.getNamesOfAllStandardClasses();
        drawer = this;
        logicMap = new HashMap<>();
        uiMap = new HashMap<>();

        for (String s : allClasses) {
            if (!s.split("\\.")[0].equals(packageName)) {
                continue;
            }

            try {
                Class clazz = Class.forName(s);
                if (clazz.isAnnotationPresent(EntryIntercepted.class)) {

                    ByteBuddy byteBuddy = new ByteBuddy();

                    DynamicType.Builder<Object> builder = byteBuddy.subclass(clazz)
                            .implement(EnterCondition.class);

                    //builder = builder.method(ElementMatchers.named("entry")).intercept(MethodDelegation.to(new EntryInterceptor(this)));
                    builder = builder.method(ElementMatchers.named("entry")).intercept(MethodDelegation.to(EntryInterceptor.class));

                    DynamicType.Unloaded<Object> unloadedClass = builder.make();
                    DynamicType.Loaded<?> loaded = unloadedClass.load(getClass().getClassLoader());
                    Class<?> dynamicType = loaded.getLoaded();

                    Constructor constructor = dynamicType.getConstructor(Drawer.class);
                    Object instance = constructor.newInstance(this);

                    logicMap.put(clazz, instance);

                } else {
                    Constructor constructor = clazz.getConstructor(Drawer.class);
                    Object instance = constructor.newInstance(this);

                    logicMap.put(clazz, instance);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        //map now contains all classes
        
        uiMap.put(Room1.class, new Room1Component((Room1) logicMap.get(Room1.class)));
        uiMap.put(Room2.class, new Room2Component((Room2) logicMap.get(Room2.class)));
        uiMap.put(Room3.class, new Room3Component((Room3) logicMap.get(Room3.class)));
        uiMap.put(Room4.class, new Room4Component((Room4) logicMap.get(Room4.class)));
        uiMap.put(Room5.class, new Room5Component((Room5) logicMap.get(Room5.class)));
        uiMap.put(Room6.class, new Room6Component((Room6) logicMap.get(Room6.class)));
        uiMap.put(Room7.class, new Room7Component((Room7) logicMap.get(Room7.class)));
        uiMap.put(Room8.class, new Room8Component((Room8) logicMap.get(Room8.class)));
        uiMap.put(Room9.class, new Room9Component((Room9) logicMap.get(Room9.class)));
        uiMap.put(Room10.class, new Room10Component((Room10) logicMap.get(Room10.class)));
        
        for(Map.Entry<Class, Object> entry : uiMap.entrySet()) {
            //if(entry.getValue())
        }
        
        GameState gameState = GameState.getInstance();
        gameState.setCurrRoom(logicMap.get(Room1.class));
        
        String out = (String) invokeEntry(gameState.getCurrRoom());
        
        invokeSetText(uiMap.get(gameState.getCurrRoom().getClass()), (String) invokeEntry(gameState.getCurrRoom()));
        paint();
        return out;
    }

    public String processInput(String inp) {
        if(GameState.getInstance().isDead()) {
            reset();
            return null;
        }
        lastInput = inp;
        GameState gameState = GameState.getInstance();
        Object currRoom = gameState.getCurrRoom();
        Class currClass = currRoom.getClass();

        if (currClass.getSuperclass() != Room.class) {
            currClass = currClass.getSuperclass();
        }

        //look for the directions
        Field[] fields = currClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Direction.class)) {

                String command = field.getAnnotation(Direction.class).command();

                if (inp.matches(command)) {
                    Object room = logicMap.get(field.getType());
                    String prompt = (String) invokeEntry(room);
                    
                    Class roomClass = room.getClass();
                    if(roomClass.getSuperclass() != Room.class)
                        roomClass = roomClass.getSuperclass();
                    
                    if(prompt.indexOf("error") != 0) {
                        //success
                        this.remove((Component) uiMap.get(currRoom.getClass()));
                        this.add((Component) uiMap.get(roomClass));
                        invokeSetText(uiMap.get(roomClass), prompt);
                    }
                    else {
                        invokeSetText(uiMap.get(currClass), prompt);
                    }
                    
                    repaint();
                    return prompt;
                }
            }
        }

        //look for the commands
        Method[] methods = currClass.getDeclaredMethods();
        for (Method method : methods) {

            if (method.isAnnotationPresent(Command.class)) {
                String command = method.getAnnotation(Command.class).command();

                if (inp.matches(command)) {
                    String outString;
                    try {
                        outString = (String) method.invoke(currRoom);
                        
                        if(currClass.getSuperclass() != Room.class)
                            currClass = currClass.getSuperclass();
                        
                        invokeSetText(uiMap.get(currClass), outString);
                        return outString;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    break;
                }
            }
        }
        
        invokeSetText(uiMap.get(GameState.getInstance().getCurrRoom().getClass()), "That did nothing. Type 'help' to see what you can do.");
        
        return "That did nothing. Type 'help' to see what you can do.";
    }

    public void paint() {
        add((Component) uiMap.get(GameState.getInstance().getCurrRoom().getClass()));
        textField.addKeyListener(new TextFieldListener());
        this.add(textField, BorderLayout.PAGE_END);
        this.validate();
    }

    public Object getRoom(Class clazz) {
        return logicMap.get(clazz);
    }

    public String getLastInput() {
        return lastInput;
    }

    private void invokeSetText(Object obj, String text) {
        RoomComponent rc = (RoomComponent) obj;
        rc.setGameText(text);
        repaint();
        validate();
    }
    
    private static Object invokeEntry(Object obj) {
        return invokeMethod("entry", obj, null, null);
    }

    private static Object invokeMethod(String method, Object obj, Class[] classes, Object[] args) {
        try {
            Method m = obj.getClass().getDeclaredMethod(method, classes);
            m.setAccessible(true); //line is unnecessary when method is not private

            return m.invoke(obj, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public class TextFieldListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if(GameState.getInstance().isDead())
                    System.exit(0);
                processInput(textField.getText());
                textField.setText("");
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub

        }
        
    }
}
