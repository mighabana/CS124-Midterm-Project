package anno;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Direction {
	// public final static String NORTH = "(?i)((^(n|u)).*)|(.*(north|up).*)",
	// SOUTH = "(?i)((^(s|d)).*)|(.*(south|down).*)",
	// EAST = "(?i)((^(e|r)).*)|(.*(east|right).*)",
	// WEST = "(?i)((^(w|l)).*)|(.*(west|left).*)";
	public final static String NORTH = "(?i)(.*(north|up).*)", SOUTH = "(?i)(.*(south|down).*)",
			EAST = "(?i)(.*(east|right).*)", WEST = "(?i)(.*(west|left).*)";

	public String command();
}
