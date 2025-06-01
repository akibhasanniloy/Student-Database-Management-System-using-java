import java.util.Scanner;

public class ErrorHandler {
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void handleFatalError(String message, Exception e) {
        clearScreen();
        System.out.println("\n\n\t!!! SYSTEM ERROR !!!");
        System.out.println("\t" + message);
        System.out.println("\tError details: " + e.getMessage());
        System.out.println("\n\tThe program will now exit.");
        System.out.println("\nPress Enter to exit...");
        scanner.nextLine();
        System.exit(1);
    }
    
    public static void handleError(String message) {
        System.out.println("\n\tERROR: " + message);
        System.out.print("\tPress Enter to continue...");
        scanner.nextLine();
    }
    
    public static void clearScreen() {
        try {
            final String os = System.getProperty("os.name").toLowerCase();
            
            if (os.contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls")
                    .inheritIO()
                    .start()
                    .waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Could not clear screen: " + e.getMessage());
        }
    }
    
    public static void showWarning(String message) {
        System.out.println("\n\tWARNING: " + message);
        System.out.print("\tPress Enter to continue...");
        scanner.nextLine();
    }
    
    public static void showSuccess(String message) {
        System.out.println("\n\tSUCCESS: " + message);
        System.out.print("\tPress Enter to continue...");
        scanner.nextLine();
    }
    
    public static boolean confirmAction(String message) {
        System.out.print("\n\t" + message + " (y/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }
}