package lab5;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import net.bytebuddy.implementation.bind.annotation.*;

public class EntryInterceptor {

	public static String entry(@Origin Method method, @SuperCall Callable<?> zuper, @Super Object zuperClass)
			throws Exception {
		String[] split = method.toString().split(" ");
		// String wholeName = split[split.length - 1];
		split = split[split.length - 1].split("\\.");

		String canonical = "";
		for (int i = 0; i < split.length - 1; i++) {
			if (i != 0)
				canonical += ".";
			canonical += split[i];
		}
		String methodName = split[split.length - 1];

		Class clazz = Class.forName(canonical);
		EnterCondition ec = null;
		if (methodName.equals("entry()")) {
			ec = (EnterCondition) Runner.runner.getRoom(clazz);

			if (ec.canEnter()) {
				System.out.println(ec.enterMessage());
				return (String) zuper.call();
			}
			System.out.println(ec.unableToEnterMessage());
			return null;
		}

		return "Lol what I didn't call this: " + method;
	}
}
