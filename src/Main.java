import java.io.*;
import java.util.*;

public class Main {

    // Admin login credentials (can be replaced by a more secure method)
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        // Main menu loop
        while (true) {
            System.out.println("\n*** Healthcare Appointment Management System ***");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();  // Consume newline

            String username = "";
            String password = "";

            switch (choice) {
                case 1:
                    // Register a new user
                    System.out.print("Enter a new username: ");
                    username = sc.nextLine();

                    // Check if username already exists
                    if (checkUserExists(username)) {
                        System.out.println("Username already exists. Please choose a different username.");
                        break;
                    }

                    System.out.print("Enter a new password: ");
                    password = sc.nextLine();

                    // Register the user
                    registerUser(username, password);
                    System.out.println("Registration successful!");
                    break;

                case 2:
                    // Login an existing user
                    System.out.print("Enter your username: ");
                    username = sc.nextLine();

                    System.out.print("Enter your password: ");
                    password = sc.nextLine();

                    // Validate login
                    if (validateLogin(username, password)) {
                        System.out.println("Login successful!");

                        // After successful login, show the main options
                        showMainMenu(username);
                    } else {
                        System.out.println("Invalid username or password. Please try again.");
                    }
                    break;

                case 3:
                    // Admin login
                    System.out.print("Enter admin username: ");
                    String adminUsername = sc.nextLine();
                    System.out.print("Enter admin password: ");
                    String adminPassword = sc.nextLine();

                    if (adminLogin(adminUsername, adminPassword)) {
                        System.out.println("Admin Login successful.");
                        adminDashboard();
                    } else {
                        System.out.println("Invalid admin username or password.");
                    }
                    break;

                case 4:
                    // Exit the system
                    System.out.println("Thank you for using the system. Goodbye!");
                    return;  // Exit the program

                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }
        }
    }

    // Method to check if a user already exists in the users.txt file
    public static boolean checkUserExists(String username) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("users.txt"));
        String line;

        while ((line = br.readLine()) != null) {
            String[] userData = line.split(",");
            if (userData[0].equals(username)) {
                br.close();
                return true;  // User already exists
            }
        }
        br.close();
        return false;  // User does not exist
    }

    // Method to register a user by adding their username and password to the users.txt file
    public static void registerUser(String username, String password) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true));
        bw.write(username + "," + password);
        bw.newLine();
        bw.close();
    }

    // Method to validate the login credentials by checking the users.txt file
    public static boolean validateLogin(String username, String password) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("users.txt"));
        String line;

        while ((line = br.readLine()) != null) {
            String[] userData = line.split(",");
            if (userData[0].equals(username) && userData[1].equals(password)) {
                br.close();
                return true;  // Successful login
            }
        }
        br.close();
        return false;  // Invalid credentials
    }

    // Admin login method
    public static boolean adminLogin(String username, String password) {
        return username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD);
    }

    // Admin dashboard
    public static void adminDashboard() throws IOException {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nAdmin Dashboard:");
            System.out.println("1. View all appointments");
            System.out.println("2. View all doctors");
            System.out.println("3. Add new doctor");
            System.out.println("4. Remove a doctor");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    viewAppointments();
                    break;
                case 2:
                    viewDoctors();
                    break;
                case 3:
                    addDoctor();
                    break;
                case 4:
                    removeDoctor();
                    break;
                case 5:
                    System.out.println("Exiting Admin Dashboard.");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // Method to view all appointments
    public static void viewAppointments() throws IOException {
        File inputFile = new File("appointments.txt");

        // Check if file exists
        if (!inputFile.exists()) {
            System.out.println("Appointments file not found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            System.out.println("All Appointments:");
            while ((line = br.readLine()) != null) {
                String[] appointmentData = line.split(",");
                if (appointmentData.length == 4) {
                    System.out.println("User: " + appointmentData[0] + ", Doctor: " + appointmentData[1] + ", Date: " + appointmentData[2] + ", Time: " + appointmentData[3]);
                } else {
                    System.out.println("Invalid appointment data format: " + line);
                }
            }
        }
    }

    // Method to view all doctors
    public static void viewDoctors() throws IOException {
        File inputFile = new File("doctors.txt");

        // Check if file exists
        if (!inputFile.exists()) {
            System.out.println("Doctors file not found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            System.out.println("List of All Doctors:");
            int index = 1;
            while ((line = br.readLine()) != null) {
                System.out.println(index + ". " + line);
                index++;
            }
        }
    }

    // Method to add a new doctor
    public static void addDoctor() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter doctor's name to add: ");
        String doctorName = sc.nextLine();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("doctors.txt", true))) {
            bw.write(doctorName);
            bw.newLine();
            System.out.println("Doctor " + doctorName + " added successfully.");
        }
    }

    // Method to remove a doctor
    public static void removeDoctor() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter doctor's name to remove: ");
        String doctorName = sc.nextLine();

        File inputFile = new File("doctors.txt");
        File tempFile = new File("temp_doctors.txt");

        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals(doctorName)) {
                    found = true;
                    continue; // Skip this doctor to remove them
                }
                bw.write(line);
                bw.newLine();
            }
        }

        if (found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
            System.out.println("Doctor " + doctorName + " removed successfully.");
        } else {
            tempFile.delete();
            System.out.println("Doctor " + doctorName + " not found.");
        }
    }

    // Method to show main menu options after successful login
    public static void showMainMenu(String username) throws IOException {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n*** Healthcare Appointment Management System ***");
            System.out.println("1. Book an appointment");
            System.out.println("2. View appointment history");
            System.out.println("3. Cancel an appointment");
            System.out.println("4. Reschedule an appointment");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    Appointment.bookAppointmentTerminal(username);
                    break;
                case 2:
                    Appointment.viewAppointmentHistory(username);
                    break;
                case 3:
                    Appointment.cancelAppointmentTerminal(username);
                    break;
                case 4:
                    Appointment.rescheduleAppointmentTerminal(username);
                    break;
                case 5:
                    System.out.println("Logged out successfully!");
                    return;  // Return to the main menu
                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }
        }
    }
}
