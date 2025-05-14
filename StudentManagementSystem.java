import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class StudentInformation implements Serializable {
    private static final long serialVersionUID = 1L;
    String ID;
    String name;
    String cls;
    String branch;
    String address;
    String email;
    String rollno;
    String phoneno;

    // Constructor
    public StudentInformation(String ID, String name, String cls, String branch, 
                            String address, String email, String rollno, String phoneno) {
        this.ID = ID;
        this.name = name;
        this.cls = cls;
        this.branch = branch;
        this.address = address;
        this.email = email;
        this.rollno = rollno;
        this.phoneno = phoneno;
    }

    // Getters and Setters
    public String getID() { return ID; }
    public String getName() { return name; }
    // ... other getters and setters ...

    @Override
    public String toString() {
        return "ID: " + ID + "\nName: " + name + "\nClass: " + cls + 
               "\nBranch: " + branch + "\nAddress: " + address + 
               "\nEmail: " + email + "\nRoll No: " + rollno + 
               "\nPhone No: " + phoneno;
    }
}

class Admin {
    private static final String DATA_FILE = "student_data.ser";
    private Scanner scanner = new Scanner(System.in);

    public void addRecord() {
        System.out.print("\033[H\033[2J");
        System.out.println("\n\t\t\t\t=== ADD NEW STUDENT RECORD ===");
        
        System.out.print("\nEnter ID: ");
        String ID = scanner.nextLine();
        
        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Class: ");
        String cls = scanner.nextLine();
        
        System.out.print("Enter Branch: ");
        String branch = scanner.nextLine();
        
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter Roll No: ");
        String rollno = scanner.nextLine();
        
        System.out.print("Enter Phone No: ");
        String phoneno = scanner.nextLine();

        StudentInformation newStudent = new StudentInformation(ID, name, cls, branch, 
                                                            address, email, rollno, phoneno);
        
        List<StudentInformation> students = loadStudents();
        students.add(newStudent);
        saveStudents(students);
        
        System.out.println("\nRecord added successfully!");
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    public void modifyRecord() {
        System.out.print("\033[H\033[2J");
        System.out.println("\n\t\t\t\t=== MODIFY STUDENT RECORD ===");
        System.out.print("\nEnter Student ID to modify: ");
        String searchID = scanner.nextLine();
        
        List<StudentInformation> students = loadStudents();
        boolean found = false;
        
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getID().equalsIgnoreCase(searchID)) {
                found = true;
                StudentInformation student = students.get(i);
                
                System.out.println("\nCurrent Record:");
                System.out.println(student.toString());
                
                System.out.println("\nEnter new details (leave blank to keep current):");
                
                System.out.print("Name (" + student.name + "): ");
                String name = scanner.nextLine();
                if (!name.isEmpty()) student.name = name;
                
                System.out.print("Class (" + student.cls + "): ");
                String cls = scanner.nextLine();
                if (!cls.isEmpty()) student.cls = cls;
                
                // Repeat for other fields...
                
                students.set(i, student);
                saveStudents(students);
                System.out.println("\nRecord updated successfully!");
                break;
            }
        }
        
        if (!found) {
            System.out.println("\nStudent with ID " + searchID + " not found!");
        }
        
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    public void deleteRecord() {
        System.out.print("\033[H\033[2J");
        System.out.println("\n\t\t\t\t=== DELETE STUDENT RECORD ===");
        System.out.print("\nEnter Student ID to delete: ");
        String searchID = scanner.nextLine();
        
        List<StudentInformation> students = loadStudents();
        boolean removed = students.removeIf(s -> s.getID().equalsIgnoreCase(searchID));
        
        if (removed) {
            saveStudents(students);
            System.out.println("\nRecord deleted successfully!");
        } else {
            System.out.println("\nStudent with ID " + searchID + " not found!");
        }
        
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    public void searchRecords() {
        System.out.print("\033[H\033[2J");
        System.out.println("\n\t\t\t\t=== SEARCH STUDENT RECORDS ===");
        System.out.println("\n1. Search by ID");
        System.out.println("2. Search by Name");
        System.out.println("3. View All Records");
        System.out.print("\nChoose option: ");
        
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            choice = 0;
        }
        
        List<StudentInformation> students = loadStudents();
        List<StudentInformation> results = new ArrayList<>();
        
        switch (choice) {
            case 1:
                System.out.print("\nEnter Student ID: ");
                String searchID = scanner.nextLine();
                for (StudentInformation s : students) {
                    if (s.getID().equalsIgnoreCase(searchID)) {
                        results.add(s);
                        break;
                    }
                }
                break;
            case 2:
                System.out.print("\nEnter Student Name: ");
                String searchName = scanner.nextLine();
                for (StudentInformation s : students) {
                    if (s.getName().toLowerCase().contains(searchName.toLowerCase())) {
                        results.add(s);
                    }
                }
                break;
            case 3:
                results.addAll(students);
                break;
            default:
                System.out.println("\nInvalid choice!");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                return;
        }
        
        displaySearchResults(results);
    }

    private void displaySearchResults(List<StudentInformation> results) {
        System.out.print("\033[H\033[2J");
        if (results.isEmpty()) {
            System.out.println("\nNo matching records found!");
        } else {
            System.out.println("\nFound " + results.size() + " record(s):");
            System.out.println("----------------------------------------");
            for (StudentInformation s : results) {
                System.out.println(s.toString());
                System.out.println("----------------------------------------");
            }
        }
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    @SuppressWarnings("unchecked")
    private List<StudentInformation> loadStudents() {
        List<StudentInformation> students = new ArrayList<>();
        File file = new File(DATA_FILE);
        
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
                students = (List<StudentInformation>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading student data: " + e.getMessage());
            }
        }
        
        return students;
    }

    private void saveStudents(List<StudentInformation> students) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving student data: " + e.getMessage());
        }
    }
}

class Student {
    private static final String DATA_FILE = "student_data.ser";
    private Scanner scanner = new Scanner(System.in);

    public void viewRecord(String username) {
        List<StudentInformation> students = loadStudents();
        boolean found = false;
        
        for (StudentInformation s : students) {
            if (s.getName().equalsIgnoreCase(username)) {
                System.out.print("\033[H\033[2J");
                System.out.println("\n\t\t\t\t=== YOUR STUDENT RECORD ===");
                System.out.println("\n" + s.toString());
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("\nRecord not found!");
        }
        
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void requestModification(String username) {
        System.out.print("\033[H\033[2J");
        System.out.println("\n\t\t\t\t=== REQUEST DATA MODIFICATION ===");
        System.out.println("\nYour modification request has been submitted to the administrator.");
        System.out.println("You will be notified once your request is processed.");
        
        // In a real system, we would save this request to a queue/file
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    @SuppressWarnings("unchecked")
    private List<StudentInformation> loadStudents() {
        List<StudentInformation> students = new ArrayList<>();
        File file = new File(DATA_FILE);
        
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
                students = (List<StudentInformation>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading student data: " + e.getMessage());
            }
        }
        
        return students;
    }
}

public class StudentManagementSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final String STUDENT_USERNAME = "student";
    private static final String STUDENT_PASSWORD = "student123";

    public static void main(String[] args) {
        System.out.print("\033[H\033[2J"); // Clear screen
        displayWelcomeScreen();
        loginScreen();
    }

    private static void displayWelcomeScreen() {
        date();
        System.out.println("\n");
        System.out.println("\t\t\t\t    ------------------------------------");
        System.out.println("\t\t\t\t    |STUDENT DATABASE MANAGEMENT SYSTEM|");
        System.out.println("\t\t\t\t    ------------------------------------");
        System.out.println("\n\n\n");
        System.out.println("\t\t\t\tPrepared By    :   Team 4");
        System.out.println("\n\n");
        System.out.println("\t\t\t\tMini Project   :   Student Database Management System");
        System.out.println("\n\n");
        System.out.println("\t\t\t\t    Press Enter to continue......");
        scanner.nextLine();
        
        System.out.println("\n\n\t\t\t\t Loading");
        for (int process = 0; process < 25; process++) {
            delay(100);
            System.out.print(".");
        }
    }

    private static void date() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("\n\n\n");
        System.out.println("\t\t\t\t\t   Date: " + currentDate.format(formatter));
    }

    private static void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void loginScreen() {
        System.out.print("\033[H\033[2J");
        date();
        System.out.println("\n");
        System.out.println("\t\t\t\t    ------------------------------------");
        System.out.println("\t\t\t\t    |STUDENT DATABASE MANAGEMENT SYSTEM|");
        System.out.println("\t\t\t\t    ------------------------------------");
        System.out.println("\n\n\n");
        System.out.println("\t\t\t\t\t1. Admin Panel.");
        System.out.println("\t\t\t\t\t2. Student Profile.");
        System.out.println("\n\t\t\t\t\tChoose option [1/2]: ");
        
        try {
            int input = Integer.parseInt(scanner.nextLine());
            switch (input) {
                case 1:
                    authenticateUser(ADMIN_USERNAME, ADMIN_PASSWORD, true);
                    break;
                case 2:
                    authenticateUser(STUDENT_USERNAME, STUDENT_PASSWORD, false);
                    break;
                default:
                    System.out.println("\t\t\t\tInvalid input! Please try again.");
                    delay(1000);
                    loginScreen();
            }
        } catch (NumberFormatException e) {
            System.out.println("\t\t\t\tPlease enter a valid number!");
            delay(1000);
            loginScreen();
        }
    }

    private static void authenticateUser(String correctUsername, String correctPassword, boolean isAdmin) {
        int attempts = 0;
        while (attempts < MAX_LOGIN_ATTEMPTS) {
            System.out.print("\033[H\033[2J");
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            System.out.print("\t\t\t\t\tUsername: ");
            String username = scanner.nextLine();
            System.out.print("\t\t\t\t\tPassword: ");
            String password = scanner.nextLine();

            if (username.equals(correctUsername) && password.equals(correctPassword)) {
                System.out.println("\n\n\t\t\t\tAccess granted!");
                delay(1000);
                if (isAdmin) {
                    adminMenu();
                } else {
                    studentMenu();
                }
                return;
            } else {
                attempts++;
                System.out.println("\n\t\t\t\tInvalid credentials. Attempts remaining: " + (MAX_LOGIN_ATTEMPTS - attempts));
                delay(1500);
            }
        }
        System.out.println("\n\t\t\t\tMaximum login attempts reached. System locked.");
        System.exit(0);
    }

    private static void adminMenu() {
        Admin admin = new Admin();
        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.println("\n\n\n");
            System.out.println("\t\t\t\t    ------------------------------------");
            System.out.println("\t\t\t\t    |      ADMINISTRATOR MENU       |");
            System.out.println("\t\t\t\t    ------------------------------------");
            System.out.println("\n\n");
            System.out.println("\t\t\t\t1. Add New Record");
            System.out.println("\t\t\t\t2. Modify Record");
            System.out.println("\t\t\t\t3. Delete Record");
            System.out.println("\t\t\t\t4. Search Records");
            System.out.println("\t\t\t\t5. Return to Login");
            System.out.println("\t\t\t\t6. Exit System");
            System.out.print("\n\t\t\t\tChoose option: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        admin.addRecord();
                        break;
                    case 2:
                        admin.modifyRecord();
                        break;
                    case 3:
                        admin.deleteRecord();
                        break;
                    case 4:
                        admin.searchRecords();
                        break;
                    case 5:
                        loginScreen();
                        break;
                    case 6:
                        exitSystem();
                        break;
                    default:
                        System.out.println("\t\t\t\tInvalid choice!");
                        delay(1000);
                }
            } catch (NumberFormatException e) {
                System.out.println("\t\t\t\tPlease enter a valid number!");
                delay(1000);
            }
        }
    }

    private static void studentMenu() {
        Student student = new Student();
        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.println("\n\n\n");
            System.out.println("\t\t\t\t    ------------------------------------");
            System.out.println("\t\t\t\t    |        STUDENT MENU          |");
            System.out.println("\t\t\t\t    ------------------------------------");
            System.out.println("\n\n");
            System.out.println("\t\t\t\t1. View My Record");
            System.out.println("\t\t\t\t2. Request Data Modification");
            System.out.println("\t\t\t\t3. Return to Login");
            System.out.println("\t\t\t\t4. Exit System");
            System.out.print("\n\t\t\t\tChoose option: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        student.viewRecord(STUDENT_USERNAME);
                        break;
                    case 2:
                        student.requestModification(STUDENT_USERNAME);
                        break;
                    case 3:
                        loginScreen();
                        break;
                    case 4:
                        exitSystem();
                        break;
                    default:
                        System.out.println("\t\t\t\tInvalid choice!");
                        delay(1000);
                }
            } catch (NumberFormatException e) {
                System.out.println("\t\t\t\tPlease enter a valid number!");
                delay(1000);
            }
        }
    }

    private static void exitSystem() {
        System.out.print("\033[H\033[2J");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\t\t\t\tThis project was made by team 4.");
        System.out.println("\t\t\t\tTeam members:");
        System.out.println("\t\t\t\t1. Mahima Mostofa.");
        System.out.println("\t\t\t\t2. Jeba Afroz Audity.");
        System.out.println("\t\t\t\t3. Naznin Nahar Ritu.");
        System.out.println("\t\t\t\t4. Akib Hasan Niloy.");
        System.out.println("\t\t\t\t5. Shahiar Kobir.");
        System.out.println("\n\t\t\t\tTHANK YOU");
        System.out.println("\n\n\n\n");
        System.exit(0);
    }
}