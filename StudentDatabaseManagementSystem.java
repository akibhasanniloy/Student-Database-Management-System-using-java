import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StudentDatabaseManagementSystem {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        try {
            ErrorHandler.clearScreen();
            mainPage();
            login();
        } catch (Exception e) {
            ErrorHandler.handleFatalError("A critical error occurred", e);
        }
    }
    
    // ================== UTILITY METHODS ==================
    private static void date() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        System.out.println("\n\n\n\t\t\t\t\t   Date: " + dtf.format(now));
    }
    
    private static void title() {
        System.out.println("\n\n\n\t\t\t\t    ------------------------------------");
        System.out.println("\t\t\t\t    |STUDENT DATABASE MANAGEMENT SYSTEM|");
        System.out.println("\t\t\t\t    ------------------------------------\n\n");
    }
    
    private static void pressEnterToContinue() {
        System.out.print("\n\t\t\t\tPress Enter to continue...");
        scanner.nextLine();
    }
    
    // ================== CORE FUNCTIONALITY ==================
    private static void mainPage() {
        ErrorHandler.clearScreen();
        date();
        title();
        
        System.out.print("\t\t\t\tPrepared By    :   Unsung Tori\n\n");
        System.out.print("\t\t\t\tProject Name   :   Student Database Management System\n\n");
        System.out.print("\t\t\t\t    Press Enter to continue......");
        scanner.nextLine();
        
        System.out.print("\n\n\t\t\t\t Loading");
        for (int i = 0; i < 25; i++) {
            try {
                Thread.sleep(100);
                System.out.print(".");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    private static void login() {
        try {
            ErrorHandler.clearScreen();
            date();
            title();
            
            System.out.print("\t\t\t\t\t1. Admin Panel.\n");
            System.out.print("\t\t\t\t\t2. Student Profile.\n\n");
            System.out.print("\t\t\t\t\tChoose option [1/2] : ");
            
            int choice = InputValidator.validateMenuChoice(scanner.nextLine(), 1, 2);
            
            switch(choice) {
                case 1: adminLogin(); break;
                case 2: studentLogin(); break;
            }
        } catch (Exception e) {
            ErrorHandler.handleError(e.getMessage());
            login();
        }
    }
    
    // ================== ADMIN FUNCTIONALITY ==================
    private static void adminLogin() {
        final int maxTries = 3;
        int attempts = 0;
        
        do {
            try {
                ErrorHandler.clearScreen();
                System.out.println("\n\n\n\n\n\n\n\n\n\n");
                System.out.print("\t\t\t\t\tAdmin Username: ");
                String username = scanner.nextLine();
                System.out.print("\t\t\t\t\tAdmin Password: ");
                String password = scanner.nextLine();

                if ("admin".equals(username) && "admin@124".equals(password)) {
                    System.out.println("\n\n\t\t\t\tAccess granted!\n");
                    pressEnterToContinue();
                    adminMenu();
                    return;
                }
                
                if (++attempts >= maxTries) {
                    System.out.println("\n\t\t\t\tMaximum attempts reached!");
                    pressEnterToContinue();
                    System.exit(0);
                }
                
                System.out.printf("\n\t\t\t\tAttempts remaining: %d\n", maxTries - attempts);
                pressEnterToContinue();
            } catch (Exception e) {
                ErrorHandler.handleError(e.getMessage());
            }
        } while (true);
    }
    
    private static void adminMenu() {
        try {
            ErrorHandler.clearScreen();
            title();
            
            System.out.print("\t\t\t\t1. Enter new Records\n\n");
            System.out.print("\t\t\t\t2. Modify Records\n\n");
            System.out.print("\t\t\t\t3. Delete Records\n\n");
            System.out.print("\t\t\t\t4. Search Records\n\n");
            System.out.print("\t\t\t\t5. View All Records\n\n");
            System.out.print("\t\t\t\t6. Back to Login\n\n");
            System.out.print("\t\t\t\t7. Exit\n\n");
            System.out.print("\t\t\t\tChoose option [1-7]: ");
            
            int choice = InputValidator.validateMenuChoice(scanner.nextLine(), 1, 7);
            
            switch(choice) {
                case 1: addRecord(); break;
                case 2: modifyRecord(); break;
                case 3: deleteRecord(); break;
                case 4: searchRecords(1); break;
                case 5: viewAllRecords(); break;
                case 6: login(); break;
                case 7: exitProgram(); break;
            }
        } catch (Exception e) {
            ErrorHandler.handleError(e.getMessage());
            adminMenu();
        }
    }
    
    private static void addRecord() {
        try {
            Information student = new Information();
            ErrorHandler.clearScreen();
            title();
            
            System.out.println("\n\t\t\t\tEnter Student Information:\n");
            
            System.out.print("\t\t\t\tID: ");
            student.ID = InputValidator.validateStringInput(scanner.nextLine(), "ID");
            
            System.out.print("\t\t\t\tFull Name: ");
            student.name = InputValidator.validateStringInput(scanner.nextLine(), "Name");
            
            System.out.print("\t\t\t\tClass: ");
            student.cls = scanner.nextLine();
            
            System.out.print("\t\t\t\tBranch: ");
            student.branch = scanner.nextLine();
            
            System.out.print("\t\t\t\tAddress: ");
            student.address = scanner.nextLine();
            
            System.out.print("\t\t\t\tEmail: ");
            student.email = scanner.nextLine();
            
            System.out.print("\t\t\t\tRoll No: ");
            student.rollno = scanner.nextLine();
            
            System.out.print("\t\t\t\tPhone No: ");
            student.phoneno = scanner.nextLine();
            
            FileOperations.appendRecord(student);
            System.out.println("\n\t\t\t\tRecord added successfully!");
            
            System.out.print("\t\t\t\tAdd another? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                addRecord();
            } else {
                adminMenu();
            }
        } catch (Exception e) {
            ErrorHandler.handleError(e.getMessage());
            addRecord();
        }
    }
    
    private static void modifyRecord() {
        try {
            ErrorHandler.clearScreen();
            title();
            
            System.out.print("\n\t\t\t\tEnter student name to modify: ");
            String name = InputValidator.validateStringInput(scanner.nextLine(), "Name");
            
            List<Information> records = FileOperations.readAllRecords();
            boolean found = false;
            
            for (Information record : records) {
                if (record.name.equalsIgnoreCase(name)) {
                    System.out.println("\n\t\t\t\tCurrent Record:");
                    displayRecord(record);
                    
                    System.out.println("\n\t\t\t\tEnter new information:");
                    
                    System.out.print("\t\t\t\tNew Class: ");
                    record.cls = scanner.nextLine();
                    
                    System.out.print("\t\t\t\tNew Branch: ");
                    record.branch = scanner.nextLine();
                    
                    System.out.print("\t\t\t\tNew Address: ");
                    record.address = scanner.nextLine();
                    
                    System.out.print("\t\t\t\tNew Email: ");
                    record.email = scanner.nextLine();
                    
                    System.out.print("\t\t\t\tNew Phone: ");
                    record.phoneno = scanner.nextLine();
                    
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                throw new Exception("Student not found!");
            }
            
            FileOperations.writeAllRecords(records);
            System.out.println("\n\t\t\t\tRecord updated successfully!");
            pressEnterToContinue();
            adminMenu();
        } catch (Exception e) {
            ErrorHandler.handleError(e.getMessage());
            modifyRecord();
        }
    }
    
    private static void deleteRecord() {
        try {
            ErrorHandler.clearScreen();
            title();
            
            System.out.print("\n\t\t\t\tEnter student name to delete: ");
            String name = InputValidator.validateStringInput(scanner.nextLine(), "Name");
            
            List<Information> records = FileOperations.readAllRecords();
            List<Information> updatedRecords = new ArrayList<>();
            boolean found = false;
            
            for (Information record : records) {
                if (!record.name.equalsIgnoreCase(name)) {
                    updatedRecords.add(record);
                } else {
                    found = true;
                }
            }
            
            if (!found) {
                throw new Exception("Student not found!");
            }
            
            FileOperations.writeAllRecords(updatedRecords);
            System.out.println("\n\t\t\t\tRecord deleted successfully!");
            pressEnterToContinue();
            adminMenu();
        } catch (Exception e) {
            ErrorHandler.handleError(e.getMessage());
            deleteRecord();
        }
    }
    
    // ================== STUDENT FUNCTIONALITY ==================
    private static void studentLogin() {
        final int maxTries = 3;
        int attempts = 0;
        
        do {
            try {
                ErrorHandler.clearScreen();
                System.out.println("\n\n\n\n\n\n\n\n\n\n");
                System.out.print("\t\t\t\t\tStudent Username: ");
                String username = scanner.nextLine();
                System.out.print("\t\t\t\t\tStudent Password: ");
                String password = scanner.nextLine();

                if ("student".equals(username) && "student@124".equals(password)) {
                    System.out.println("\n\n\t\t\t\tAccess granted!\n");
                    pressEnterToContinue();
                    studentMenu();
                    return;
                }
                
                if (++attempts >= maxTries) {
                    System.out.println("\n\t\t\t\tMaximum attempts reached!");
                    pressEnterToContinue();
                    System.exit(0);
                }
                
                System.out.printf("\n\t\t\t\tAttempts remaining: %d\n", maxTries - attempts);
                pressEnterToContinue();
            } catch (Exception e) {
                ErrorHandler.handleError(e.getMessage());
            }
        } while (true);
    }
    
    private static void studentMenu() {
        try {
            ErrorHandler.clearScreen();
            title();
            
            System.out.print("\t\t\t\t1. View My Profile\n\n");
            System.out.print("\t\t\t\t2. Request Profile Update\n\n");
            System.out.print("\t\t\t\t3. Back to Login\n\n");
            System.out.print("\t\t\t\t4. Exit\n\n");
            System.out.print("\t\t\t\tChoose option [1-4]: ");
            
            int choice = InputValidator.validateMenuChoice(scanner.nextLine(), 1, 4);
            
            switch(choice) {
                case 1: searchRecords(2); break;
                case 2: requestUpdate(); break;
                case 3: login(); break;
                case 4: exitProgram(); break;
            }
        } catch (Exception e) {
            ErrorHandler.handleError(e.getMessage());
            studentMenu();
        }
    }
    
    private static void requestUpdate() {
        try {
            ErrorHandler.clearScreen();
            title();
            
            System.out.print("\n\t\t\t\tEnter your name: ");
            String name = InputValidator.validateStringInput(scanner.nextLine(), "Name");
            
            // In a real system, this would send a request to admin
            System.out.println("\n\t\t\t\tUpdate request sent to admin!");
            pressEnterToContinue();
            studentMenu();
        } catch (Exception e) {
            ErrorHandler.handleError(e.getMessage());
            requestUpdate();
        }
    }
    
    // ================== SHARED FUNCTIONALITY ==================
    private static void searchRecords(int userType) {
        try {
            ErrorHandler.clearScreen();
            title();
            
            System.out.print("\n\t\t\t\tEnter student name: ");
            String name = InputValidator.validateStringInput(scanner.nextLine(), "Name");
            
            List<Information> records = FileOperations.readAllRecords();
            boolean found = false;
            
            for (Information record : records) {
                if (record.name.equalsIgnoreCase(name)) {
                    displayRecord(record);
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                throw new Exception("Student not found!");
            }
            
            pressEnterToContinue();
            if (userType == 1) adminMenu();
            else studentMenu();
        } catch (Exception e) {
            ErrorHandler.handleError(e.getMessage());
            if (userType == 1) adminMenu();
            else studentMenu();
        }
    }
    
    private static void viewAllRecords() {
        try {
            ErrorHandler.clearScreen();
            title();
            
            List<Information> records = FileOperations.readAllRecords();
            
            if (records.isEmpty()) {
                throw new Exception("No records found!");
            }
            
            System.out.println("\n\t\t\t\tALL STUDENT RECORDS:");
            for (Information record : records) {
                displayRecord(record);
                System.out.println("\t\t\t\t-----------------------------------");
            }
            
            pressEnterToContinue();
            adminMenu();
        } catch (Exception e) {
            ErrorHandler.handleError(e.getMessage());
            adminMenu();
        }
    }
    
    private static void displayRecord(Information record) {
        System.out.println("\n\t\t\t\tID: " + record.ID);
        System.out.println("\t\t\t\tName: " + record.name);
        System.out.println("\t\t\t\tClass: " + record.cls);
        System.out.println("\t\t\t\tBranch: " + record.branch);
        System.out.println("\t\t\t\tAddress: " + record.address);
        System.out.println("\t\t\t\tEmail: " + record.email);
        System.out.println("\t\t\t\tRoll No: " + record.rollno);
        System.out.println("\t\t\t\tPhone: " + record.phoneno);
    }
    
    private static void exitProgram() {
        ErrorHandler.clearScreen();
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\t\t\t\tThis project was made by UnsungTori");
        System.out.println("\t\t\t\tTeam Member Name: Akib Hasan Niloy.");
        System.out.println("\n\t\t\t\tTHANK YOU");
        System.out.println("\n\n\n\n");
        System.exit(0);
    }
}