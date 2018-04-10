package lab5;

import lab5room.Room1;
import lab5room.Room4;
import lab5room.Room2;
import lab5room.Room3;
import anno.Command;
import anno.Direction;
import anno.EntryIntercepted;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class Runner {

	private Object currRoom;
	private HashMap<Class, Object> map;

	private Object[] wordFoundRooms;
	private boolean dead;
	private String inp;
	public static Runner runner;

	public Runner() {
		runner = this;
		map = new HashMap<>();

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
					Constructor constructor = dynamicType.getConstructor(Runner.class);
					Object instance = constructor.newInstance(this);

					map.put(clazz, instance);

				} else {
					Constructor constructor = clazz.getConstructor(Runner.class);
					Object instance = constructor.newInstance(this);

					map.put(clazz, instance);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		wordFoundRooms = new Object[] { map.get(Room2.class), map.get(Room3.class), map.get(Room4.class) };

		currRoom = map.get(Room1.class);
		System.out.println(invokeEntry(currRoom));
	}

	public void doStuff(String inp) {
		this.inp = inp;
		Class currClass = currRoom.getClass();
		boolean found = false;

		// check for direction
		if (currClass.getSuperclass() != Object.class) {
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
						currRoom = map.get(field.getType());
						System.out.println(prompt);
						found = true;
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
					} catch (Exception ex) {
					}

					break;
				}
			}
		}

		if (!found) {
			System.out.println("You're still in the same room.");
		}
	}

	public static void main(String[] args) throws Exception {
		Runner runner = new Runner();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			String inp = br.readLine().trim();
			if (inp.equals("exit")) {
				break;
			}
			runner.doStuff(inp);
			if (runner.isDead()) {
				return;
			}
		}
	}

	public boolean allWordsFound() {
		for (int i = 0; i < wordFoundRooms.length; i++) {
			if (!(boolean) invokeMethod("isWordFound", wordFoundRooms[i], null, null)) {
				return false;
			}
		}
		return true;
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

	public boolean swordTaken() {
		return ((Room2) map.get(Room2.class)).isSwordTaken();
	}

	public boolean babyDead() {
		return ((Room3) map.get(Room3.class)).isBabyDead();
	}

	public String currInp() {
		return inp;
	}

	public void setDeath(boolean dead) {
		this.dead = dead;
	}

	public boolean isDead() {
		return dead;
	}

	public Object getRoom(Class clazz) {
		return map.get(clazz);
	}
}
