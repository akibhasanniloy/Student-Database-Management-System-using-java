import java.util.Scanner;

class ErrorHandler {
    private static Scanner scanner = new Scanner(System.in);
    
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
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Could not clear screen: " + e.getMessage());
        }
    }
}