package university_networking_classes;

import java.io.Console;
import java.util.Arrays;

public class SignInManager {
    private final static String CHAT_PASSWORD = "pass";

    private static void signIn(Console console) throws SecurityException {
        console.flush();
        char[] pass = console.readPassword("Password: ");

        if (!Arrays.equals(pass, CHAT_PASSWORD.toCharArray())) {
            throw new SecurityException("Passwords don't match");
        }
        Arrays.fill(pass, 'x');

        console.printf("You successfully signed in\n");
    }

    public static void main(String[] args) {
        Console console = System.console();
        try {
            if (console == null) throw new IllegalCallerException("Please use a real terminal");
            signIn(console);
        } catch (IllegalCallerException | SecurityException exception) {
            System.out.println(exception.getLocalizedMessage());
        }
    }
}
