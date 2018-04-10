package user_interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;

import anno.Command;
import anno.Direction;
import anno.EntryIntercepted;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;
import lab5.*;
import lab5room.*;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class Room_UI extends JFrame{

	private JTextField textField;
	private HashMap<Class, Object> map;
	private Object currRoom;
	private Inventory inventory;
	private GameState gamestate;
	
	public Room_UI() {
		textField = new JTextField();
		inventory = Singleton.getInventory();
		gamestate = Singleton.getGameState();
		
		this.setLayout(new BorderLayout());
		map = new HashMap<Class, Object>();
		FastClasspathScanner scanner = new FastClasspathScanner("lab5room");
		ScanResult result = scanner.scan();
		List<String> allClasses = result.getNamesOfAllClasses();
		
		for (String s : allClasses) {
			if (s.indexOf("lab5room") != 0)
				continue;

			try {
				Class clazz = Class.forName(s);
				if (clazz.isAnnotationPresent(EntryIntercepted.class)) {

					ByteBuddy byteBuddy = new ByteBuddy();

					// CREATE A BUILDER CLASS USED TO CONFIGURE YOUR NEW DYNAMIC CLASS

					DynamicType.Builder<Object> builder = byteBuddy.subclass(clazz).implement(EnterCondition.class);
					// builder =
					// builder.method(ElementMatchers.isDeclaredBy(Object.class)).intercept(MethodDelegation.to(EntryInterceptor.class));
					builder = builder.method(ElementMatchers.named("entry"))
							.intercept(MethodDelegation.to(EntryInterceptor.class));
					// builder = builder.field(ElementMatchers.annotationType(clazz))

					// CREATE YOUR UNLOADED CLASS
					DynamicType.Unloaded<Object> unloadedClass = builder.make();

					// LOAD CLASS TO JVM
					DynamicType.Loaded<?> loaded = unloadedClass.load(getClass().getClassLoader());
					Class<?> dynamicType = loaded.getLoaded();

					// INSTANTIATE AND USE AS NORMAL
					Constructor constructor = dynamicType.getConstructor(Inventory.class, GameState.class);
					Object instance = constructor.newInstance(inventory, gamestate);

					map.put(clazz, instance);

				} else {
					Constructor constructor = clazz.getConstructor(Inventory.class, GameState.class);
					Object instance = constructor.newInstance(inventory, gamestate);

					map.put(clazz, instance);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		currRoom = map.get(Room1.class);
		invokeSetText(currRoom,invokeEntry(currRoom));
		paint();
	}
	
	public void doStuff(String inp) {
		Class currClass = currRoom.getClass();
		boolean found = false;

		// check for direction
		if (currClass.getSuperclass() != JComponent.class) {
			currClass = currClass.getSuperclass();
		}

		Field[] fields = currClass.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Direction.class)) {

				String command = field.getAnnotation(Direction.class).command();
				if (inp.matches(command)) {
					String prompt = invokeEntry(map.get(field.getType()));
					// System.out.println(map.get(field.getType()));
					if (prompt != null) {
						this.remove((Component) currRoom);
						currRoom = map.get(field.getType());
						this.add((Component) currRoom);
						System.out.println(prompt);
						invokeSetText(currRoom, prompt);
						found = true;
						this.revalidate();
					}

					break;
				}
			}
		}

		if (found) {
			return;
		}

		// check for command
		Method[] methods = currClass.getDeclaredMethods();
		for (Method method : methods) {

			if (method.isAnnotationPresent(Command.class)) {
				String command = method.getAnnotation(Command.class).command();

				if (inp.matches(command)) {
					found = true;

					String outString;
					try {
						outString = (String) method.invoke(currRoom);
						System.out.println(outString);
						invokeSetText(currRoom, outString);
					} catch (Exception ex) {
					}

					break;
				}
			}
		}

		if (!found) {
			System.out.println("You're still in the same room.");
			invokeSetText(currRoom, "You're still in the same room.");
		}
	}
	
	private void invokeSetText(Object obj, String text) {
		Method m;
		try {
			m = obj.getClass().getDeclaredMethod("setGameText", String.class);
			m.setAccessible(true);
			m.invoke(obj, text);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String invokeEntry(Object obj) {
		return (String) invokeMethod("entry", obj, null, null);
	}
	
	private Object invokeMethod(String method, Object obj, Class[] classes, Object[] args) {
		try {
			Method m = obj.getClass().getDeclaredMethod(method, classes);
			m.setAccessible(true); // line is unnecessary when method is not private
			return m.invoke(obj, args);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public void paint() {
		this.add((Component) currRoom);
		textField.addKeyListener(new TextFieldListener());
		this.add(textField, BorderLayout.PAGE_END);
		this.validate();
	}
	
	public class TextFieldListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				doStuff(textField.getText());
				textField.setText("");
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	
	}
	
}
