package main_package;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import net.bytebuddy.implementation.bind.annotation.*;

public class EntryInterceptor {
    
    private static Drawer runner;
    
    public EntryInterceptor(Drawer runner) {
        EntryInterceptor.runner = runner;
    }
    
    public static String entry(@Origin Method method, @SuperCall Callable<?> zuper, @Super Object zuperClass) throws Exception {
        String[] split = method.toString().split(" ");
        split = split[split.length - 1].split("\\.");
        
        String canonical = "";
        for(int i = 0; i < split.length - 1; i++) {
            if(i != 0)
                canonical += ".";
            canonical += split[i];
        }
        String methodName = split[split.length - 1];
        
        Class clazz = Class.forName(canonical);
        
        if(methodName.equals("entry()")) {
            //EnterCondition ec = (EnterCondition) drawer.getRoom(clazz);
            EnterCondition ec = (EnterCondition) Drawer.drawer.getRoom(clazz);
            
            if(ec.canEnter())
                return ec.enterMessage() + "\n\n" + (String) zuper.call();
            
            return "error: " + ec.unableToEnterMessage();
        }
        
        return "Lol what I didn't call this: " + method;
    }
}
