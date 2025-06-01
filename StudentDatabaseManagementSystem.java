import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class StudentDatabaseManagementSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static String currentAdminId;
    
    // Constants
    private static final Map<String, Double> CLASS_FEES = Map.of(
        "1", 1000.0, "2", 2000.0, "3", 3000.0, "4", 4000.0, "5", 5000.0,
        "6", 6000.0, "7", 7000.0, "8", 8000.0, "9", 9000.0, "10", 10000.0
    );
    
    private static final Map<String, String> COUNTRY_CODES = Map.of(
        "BD", "Bangladesh (+880)", "US", "United States (+1)", "CA", "Canada (+1)",
        "DE", "Germany (+49)", "UK", "United Kingdom (+44)"
    );

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
        
        System.out.print("\t\t\t\tPrepared By    :   Akib Hasan Niloy\n\n");
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

    private static void adminLogin() {
        final int maxTries = 3;
        int attempts = 0;
        
        do {
            try {
                ErrorHandler.clearScreen();
                System.out.println("\n\n\n\n\n\n\n\n\n\n");
                System.out.print("\t\t\t\t\tUsername: ");
                String username = scanner.nextLine();
                System.out.print("\t\t\t\t\tPassword: ");
                String password = scanner.nextLine();

                // Check for super admin
                if ("superadmin".equals(username) && "super@123".equals(password)) {
                    currentAdminId = "SUPERADMIN";
                    superAdminMenu();
                    return;
                }
                
                // Check regular admins
                boolean validAdmin = false;
                File adminsFile = new File("admins.ser");
                if (adminsFile.exists()) {
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("admins.ser"))) {
                        while (true) {
                            try {
                                Map<String, String> admin = (Map<String, String>) ois.readObject();
                                if (admin.get("id").equals(username) && admin.get("password").equals(password)) {
                                    currentAdminId = admin.get("id");
                                    validAdmin = true;
                                    break;
                                }
                            } catch (EOFException e) {
                                break;
                            }
                        }
                    }
                }
                
                if (validAdmin) {
                    System.out.println("\n\n\t\t\t\tAccess granted!\n");
                    pressEnterToContinue();
                    adminMenu();
                    return;
                }
                
                if (++attempts >= maxTries) {
                    System.out.println("\n\t\t\t\tMaximum attempts reached!");
                    pressEnterToContinue();
                    login();
                    return;
                }
                
                System.out.printf("\n\t\t\t\tAttempts remaining: %d\n", maxTries - attempts);
                pressEnterToContinue();
            } catch (Exception e) {
                ErrorHandler.handleError(e.getMessage());
            }
        } while (true);
    }
    
    // ================== ADMIN FUNCTIONALITY ==================
    private static void adminMenu() {
        try {
            ErrorHandler.clearScreen();
            title();
            
            System.out.print("\t\t\t\t1. Enter new Records\n\n");
            System.out.print("\t\t\t\t2. Modify Records\n\n");
            System.out.print("\t\t\t\t3. Delete Records\n\n");
            System.out.print("\t\t\t\t4. Search Records\n\n");
            System.out.print("\t\t\t\t5. View All Records\n\n");
            System.out.print("\t\t\t\t6. Make Payment\n\n");
            System.out.print("\t\t\t\t7. Reset Database\n\n");
            System.out.print("\t\t\t\t8. Back to Login\n\n");
            System.out.print("\t\t\t\t9. Exit\n\n");
            System.out.print("\t\t\t\tChoose option [1-9]: ");
            
            int choice = InputValidator.validateMenuChoice(scanner.nextLine(), 1, 9);
            
            switch(choice) {
                case 1: addRecord(); break;
                case 2: modifyRecord(); break;
                case 3: deleteRecord(); break;
                case 4: searchRecords(1); break;
                case 5: viewAllRecords(); break;
                case 6: makePayment(); break;
                case 7: resetDatabase(); break;
                case 8: login(); break;
                case 9: exitProgram(); break;
            }
        } catch (Exception e) {
            ErrorHandler.handleError(e.getMessage());
            adminMenu();
        }
    }
    
    private static void superAdminMenu() {
        try {
            ErrorHandler.clearScreen();
            title();
            
            System.out.print("\t\t\t\t1. Add New Admin\n\n");
            System.out.print("\t\t\t\t2. View All Admins\n\n");
            System.out.print("\t\t\t\t3. Back to Main Menu\n\n");
            System.out.print("\t\t\t\t4. Exit\n\n");
            System.out.print("\t\t\t\tChoose option [1-4]: ");
            
            int choice = InputValidator.validateMenuChoice(scanner.nextLine(), 1, 4);
            
            switch(choice) {
                case 1: addNewAdmin(); break;
                case 2: viewAllAdmins(); break;
                case 3: adminMenu(); break;
                case 4: exitProgram(); break;
            }
        } catch (Exception e) {
            ErrorHandler.handleError(e.getMessage());
            superAdminMenu();
        }
    }
    
    private static void addNewAdmin() {
        try {
            ErrorHandler.clearScreen();
            title();
            
            System.out.print("\n\t\t\t\tEnter Admin ID: ");
            String adminId = InputValidator.validateStringInput(scanner.nextLine(), "Admin ID");
            
            // Check for duplicate admin IDs
            File adminsFile = new File("admins.ser");
            if (adminsFile.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(adminsFile))) {
                    while (true) {
                        try {
                            Map<String, String> existingAdmin = (Map<String, String>) ois.readObject();
                            if (existingAdmin.get("id").equals(adminId)) {
                                throw new Exception("Admin ID already exists!");
                            }
                        } catch (EOFException e) {
                            break;
                        }
                    }
                }
            }
            
            System.out.print("\t\t\t\tEnter Admin Name: ");
            String adminName = InputValidator.validateStringInput(scanner.nextLine(), "Admin Name");
            
            System.out.print("\t\t\t\tSet Password: ");
            String password = InputValidator.validateStringInput(scanner.nextLine(), "Password");
            
            Map<String, String> admin = new HashMap<>();
            admin.put("id", adminId);
            admin.put("name", adminName);
            admin.put("password", password);
            
            // Read existing admins
            List<Map<String, String>> admins = new ArrayList<>();
            if (adminsFile.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(adminsFile))) {
                    while (true) {
                        try {
                            admins.add((Map<String, String>) ois.readObject());
                        } catch (EOFException e) {
                            break;
                        }
                    }
                }
            }
            
            // Add new admin and write back
            admins.add(admin);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(adminsFile))) {
                for (Map<String, String> a : admins) {
                    oos.writeObject(a);
                }
            }
            
            System.out.println("\n\t\t\t\tAdmin added successfully!");
            pressEnterToContinue();
            superAdminMenu();
        } catch (Exception e) {
            ErrorHandler.handleError(e.getMessage());
            addNewAdmin();
        }
    }
    
    private static void viewAllAdmins() {
        try {
            ErrorHandler.clearScreen();
            title();
            
            File adminsFile = new File("admins.ser");
            if (!adminsFile.exists()) {
                throw new Exception("No admins found!");
            }
            
            System.out.println("\n\t\t\t\tALL ADMIN RECORDS:");
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(adminsFile))) {
                while (true) {
                    try {
                        Map<String, String> admin = (Map<String, String>) ois.readObject();
                        System.out.println("\n\t\t\t\tAdmin ID: " + admin.get("id"));
                        System.out.println("\t\t\t\tAdmin Name: " + admin.get("name"));
                        System.out.println("\t\t\t\t-----------------------------------");
                    } catch (EOFException e) {
                        break;
                    }
                }
            }
            
            pressEnterToContinue();
            superAdminMenu();
        } catch (Exception e) {
            ErrorHandler.handleError(e.getMessage());
            superAdminMenu();
        }
    }
    
    private static void addRecord() {
        try {
            Information student = new Information();
            ErrorHandler.clearScreen();
            title();
            
            System.out.println("\n\t\t\t\tEnter Student Information:\n");
            
            System.out.print("\t\t\t\tID: ");
            student.setID(InputValidator.validateStringInput(scanner.nextLine(), "ID"));
            
            System.out.print("\t\t\t\tFull Name: ");
            student.setName(InputValidator.validateStringInput(scanner.nextLine(), "Name"));
            
            System.out.print("\t\t\t\tClass (1-10): ");
            String studentClass = scanner.nextLine();
            if (!CLASS_FEES.containsKey(studentClass)) {
                throw new Exception("Class must be between 1-10");
            }
            student.setCls(studentClass);
            student.setTuitionFee(CLASS_FEES.get(studentClass));
            
            System.out.print("\t\t\t\tBranch: ");
            student.setBranch(scanner.nextLine());
            
            System.out.print("\t\t\t\tAddress: ");
            student.setAddress(scanner.nextLine());
            
            System.out.print("\t\t\t\tEmail: ");
            student.setEmail(InputValidator.validateEmail(scanner.nextLine()));
            
            System.out.print("\t\t\t\tRoll No: ");
            student.setRollno(scanner.nextLine());
            
            System.out.println("\t\t\t\tSelect Country Code:");
            COUNTRY_CODES.forEach((code, name) -> 
                System.out.printf("\t\t\t\t%s - %s\n", code, name));
            System.out.print("\t\t\t\tEnter Country Code: ");
            String countryCode = scanner.nextLine().toUpperCase();
            if (!COUNTRY_CODES.containsKey(countryCode)) {
                throw new Exception("Invalid country code");
            }
            student.setCountryCode(countryCode);
            
            System.out.print("\t\t\t\tPhone No: ");
            String phone = scanner.nextLine();
            student.setPhoneno(InputValidator.validatePhoneNumber(phone, countryCode));
            
            System.out.print("\t\t\t\tAmount Paid: ");
            double amountPaid = Double.parseDouble(scanner.nextLine());
            student.setAmountPaid(amountPaid);
            
            double tuitionFee = student.getTuitionFee();
            if (amountPaid >= tuitionFee) {
                student.setPaymentStatus("Paid");
            } else {
                student.setPaymentStatus("Partial (" + (tuitionFee - amountPaid) + " due)");
            }
            
            student.setAddedByAdmin(currentAdminId);
            
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
            
            System.out.print("\n\t\t\t\tEnter student ID to modify: ");
            String id = InputValidator.validateStringInput(scanner.nextLine(), "ID");
            
            List<Information> records = FileOperations.readAllRecords();
            boolean found = false;
            
            for (Information record : records) {
                if (record.getID().equalsIgnoreCase(id)) {
                    System.out.println("\n\t\t\t\tCurrent Record:");
                    displayRecord(record);
                    
                    System.out.println("\n\t\t\t\tEnter new information:");
                    
                    System.out.print("\t\t\t\tNew Class (1-10): ");
                    String studentClass = scanner.nextLine();
                    if (!studentClass.isEmpty()) {
                        if (!CLASS_FEES.containsKey(studentClass)) {
                            throw new Exception("Class must be between 1-10");
                        }
                        record.setCls(studentClass);
                        record.setTuitionFee(CLASS_FEES.get(studentClass));
                    }
                    
                    System.out.print("\t\t\t\tNew Branch: ");
                    String branch = scanner.nextLine();
                    if (!branch.isEmpty()) record.setBranch(branch);
                    
                    System.out.print("\t\t\t\tNew Address: ");
                    String address = scanner.nextLine();
                    if (!address.isEmpty()) record.setAddress(address);
                    
                    System.out.print("\t\t\t\tNew Email: ");
                    String email = scanner.nextLine();
                    if (!email.isEmpty()) record.setEmail(InputValidator.validateEmail(email));
                    
                    System.out.print("\t\t\t\tNew Phone: ");
                    String phone = scanner.nextLine();
                    if (!phone.isEmpty()) record.setPhoneno(InputValidator.validatePhoneNumber(phone, record.getCountryCode()));
                    
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
            
            System.out.print("\n\t\t\t\tEnter student ID to delete: ");
            String id = InputValidator.validateStringInput(scanner.nextLine(), "ID");
            
            List<Information> records = FileOperations.readAllRecords();
            List<Information> updatedRecords = new ArrayList<>();
            boolean found = false;
            
            for (Information record : records) {
                if (!record.getID().equalsIgnoreCase(id)) {
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
    
    private static void makePayment() {
        try {
            ErrorHandler.clearScreen();
            title();
            
            System.out.print("\n\t\t\t\tEnter Student ID: ");
            String studentId = scanner.nextLine();
            
            List<Information> records = FileOperations.readAllRecords();
            boolean found = false;
            
            for (Information record : records) {
                if (record.getID().equals(studentId)) {
                    double tuitionFee = record.getTuitionFee();
                    double amountPaid = record.getAmountPaid();
                    String paymentStatus = record.getPaymentStatus();
                    
                    System.out.printf("\n\t\t\t\tCurrent Tuition Fee: %.2f TK\n", tuitionFee);
                    System.out.printf("\t\t\t\tAmount Paid: %.2f TK\n", amountPaid);
                    System.out.printf("\t\t\t\tStatus: %s\n", paymentStatus);
                    
                    System.out.print("\t\t\t\tEnter Payment Amount: ");
                    double payment = Double.parseDouble(scanner.nextLine());
                    
                    record.setAmountPaid(amountPaid + payment);
                    if (record.getAmountPaid() >= tuitionFee) {
                        record.setPaymentStatus("Paid");
                    } else {
                        record.setPaymentStatus(String.format("Partial (%.2f due)", 
                            tuitionFee - record.getAmountPaid()));
                    }
                    
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                throw new Exception("Student not found!");
            }
            
            FileOperations.writeAllRecords(records);
            System.out.println("\n\t\t\t\tPayment recorded successfully!");
            pressEnterToContinue();
            adminMenu();
        } catch (Exception e) {
            ErrorHandler.handleError(e.getMessage());
            makePayment();
        }
    }
    
    private static void resetDatabase() {
        try {
            ErrorHandler.clearScreen();
            title();
            System.out.print("\n\t\t\t\tWARNING: This will delete ALL records!\n");
            System.out.print("\t\t\t\tAre you sure? (y/n): ");
            String confirm = scanner.nextLine();
            
            if (confirm.equalsIgnoreCase("y")) {
                FileOperations.resetDatabase();
                System.out.println("\n\t\t\t\tDatabase reset successfully!");
            } else {
                System.out.println("\n\t\t\t\tOperation cancelled");
            }
            pressEnterToContinue();
            adminMenu();
        } catch (Exception e) {
            ErrorHandler.handleError("Failed to reset database: " + e.getMessage());
            adminMenu();
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
                System.out.print("\t\t\t\t\tStudent ID: ");
                String username = scanner.nextLine();
                System.out.print("\t\t\t\t\tPassword (use ID if none set): ");
                String password = scanner.nextLine();

                List<Information> students = FileOperations.readAllRecords();
                for (Information student : students) {
                    if (student.getID().equals(username) && 
                        (password.equals(student.getID()) || password.equals(student.getPhoneno()))) {
                        System.out.println("\n\n\t\t\t\tAccess granted!\n");
                        pressEnterToContinue();
                        studentMenu(student);
                        return;
                    }
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
    
    private static void studentMenu(Information student) {
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
                case 1: 
                    displayRecord(student);
                    pressEnterToContinue();
                    studentMenu(student);
                    break;
                case 2: requestUpdate(student); break;
                case 3: login(); break;
                case 4: exitProgram(); break;
            }
        } catch (Exception e) {
            ErrorHandler.handleError(e.getMessage());
            studentMenu(student);
        }
    }
    
    private static void requestUpdate(Information student) {
        try {
            ErrorHandler.clearScreen();
            title();
            
            System.out.println("\n\t\t\t\tCurrent Information:");
            displayRecord(student);
            
            System.out.println("\n\t\t\t\tEnter fields to update (leave blank to skip):");
            
            System.out.print("\t\t\t\tNew Address: ");
            String address = scanner.nextLine();
            if (!address.isEmpty()) student.setAddress(address);
            
            System.out.print("\t\t\t\tNew Email: ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) student.setEmail(InputValidator.validateEmail(email));
            
            System.out.print("\t\t\t\tNew Phone: ");
            String phone = scanner.nextLine();
            if (!phone.isEmpty()) student.setPhoneno(InputValidator.validatePhoneNumber(phone, student.getCountryCode()));
            
            List<Information> records = FileOperations.readAllRecords();
            for (int i = 0; i < records.size(); i++) {
                if (records.get(i).getID().equals(student.getID())) {
                    records.set(i, student);
                    break;
                }
            }
            
            FileOperations.writeAllRecords(records);
            System.out.println("\n\t\t\t\tUpdate request submitted!");
            pressEnterToContinue();
            studentMenu(student);
        } catch (Exception e) {
            ErrorHandler.handleError(e.getMessage());
            requestUpdate(student);
        }
    }
    
    // ================== SHARED FUNCTIONALITY ==================
    private static void searchRecords(int userType) {
        try {
            ErrorHandler.clearScreen();
            title();
            
            System.out.print("\n\t\t\t\tEnter student ID: ");
            String id = InputValidator.validateStringInput(scanner.nextLine(), "ID");
            
            List<Information> records = FileOperations.readAllRecords();
            boolean found = false;
            
            for (Information record : records) {
                if (record.getID().equalsIgnoreCase(id)) {
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
            else login();
        } catch (Exception e) {
            ErrorHandler.handleError(e.getMessage());
            if (userType == 1) adminMenu();
            else login();
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
        System.out.println("\n\t\t\t\tID: " + record.getID());
        System.out.println("\t\t\t\tName: " + record.getName());
        System.out.println("\t\t\t\tClass: " + record.getCls());
        System.out.println("\t\t\t\tBranch: " + record.getBranch());
        System.out.println("\t\t\t\tAddress: " + record.getAddress());
        System.out.println("\t\t\t\tEmail: " + record.getEmail());
        System.out.println("\t\t\t\tRoll No: " + record.getRollno());
        System.out.println("\t\t\t\tPhone: " + COUNTRY_CODES.get(record.getCountryCode()) + " " + record.getPhoneno());
        System.out.println("\t\t\t\tTuition Fee: " + record.getTuitionFee());
        System.out.println("\t\t\t\tAmount Paid: " + record.getAmountPaid());
        System.out.println("\t\t\t\tPayment Status: " + record.getPaymentStatus());
        if (record.getAddedByAdmin() != null) {
            System.out.println("\t\t\t\tAdded By: " + record.getAddedByAdmin());
        }
    }
    
    private static void exitProgram() {
        ErrorHandler.clearScreen();
        System.out.println("\n\n\tLogging out...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        login();
    }
}