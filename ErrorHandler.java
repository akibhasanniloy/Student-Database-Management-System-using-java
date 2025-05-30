import java.util.Scanner;

public class ErrorHandler {
    private static final Scanner scanner = new Scanner(System.in);
    
    /**
     * Handles fatal errors that require program termination
     * @param message User-friendly error message
     * @param e The exception that occurred
     */
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
    
    /**
     * Handles non-fatal errors with option to continue
     * @param message User-friendly error message
     */
    public static void handleError(String message) {
        System.out.println("\n\tERROR: " + message);
        System.out.print("\tPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Clears the console screen
     */
    public static void clearScreen() {
        try {
            final String os = System.getProperty("os.name").toLowerCase();
            
            if (os.contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls")
                    .inheritIO()
                    .start()
                    .waitFor();
            } else {
                // Unix/Linux/MacOS
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Could not clear screen: " + e.getMessage());
        }
    }
    
    /**
     * Displays a warning message
     * @param message The warning message to display
     */
    public static void showWarning(String message) {
        System.out.println("\n\tWARNING: " + message);
        System.out.print("\tPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Displays a success message
     * @param message The success message to display
     */
    public static void showSuccess(String message) {
        System.out.println("\n\tSUCCESS: " + message);
        System.out.print("\tPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Prompts user to confirm an action
     * @param message The confirmation prompt
     * @return true if user confirms, false otherwise
     */
    public static boolean confirmAction(String message) {
        System.out.print("\n\t" + message + " (y/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }
}