package main_package;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Direction {
    public final static String NORTH = "(?i)(.*(north|up).*)",
            SOUTH = "(?i)(.*(south|down).*)",
            EAST = "(?i)(.*(east|right).*)",
            WEST = "(?i)(.*(west|left).*)";
    
    String command();
    String name();
}
