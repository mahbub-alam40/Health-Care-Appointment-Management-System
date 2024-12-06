import java.io.*;
import java.security.MessageDigest;
import java.util.Scanner;

public class User {
    private String username;
    private String password; // Hashed password
    private String name;
    private String email;

    // Constructor
    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    // Register user
    public static void registerUser(String username, String password, String name, String email) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true))) {
            String hashedPassword = hashPassword(password); // Hash the password before storing
            bw.write(username + "," + hashedPassword + "," + name + "," + email);
            bw.newLine();
            System.out.println("Registration Successful!");
        } catch (IOException e) {
            System.out.println("Error during registration: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error hashing password: " + e.getMessage());
        }
    }

    // Check if a username is already taken
    public static boolean isUsernameTaken(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user data: " + e.getMessage());
        }
        return false;
    }

    // Login validation
    // User Login (Terminal-based input)
    public static String userLogin() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        try {
            if (login(username, password)) {
                System.out.println("Login Successful!");
                return username;  // Return the username on successful login
            } else {
                System.out.println("Invalid credentials!");
                return null;  // Return null if login fails
            }
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
            return null;
        }
    }

    // Hash password using SHA-256
    public static String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // User Registration (Terminal-based input)
    public static void userRegistration() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = sc.nextLine();
        if (isUsernameTaken(username)) {
            System.out.println("Username already exists. Please try a different one.");
            return;
        }

        System.out.print("Enter password: ");
        String password = sc.nextLine();
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter your email: ");
        String email = sc.nextLine();

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            System.out.println("Invalid email format.");
            return;
        }

        registerUser(username, password, name, email);
    }

    // User Login (Terminal-based input)
    public static boolean login(String username, String password) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                String storedUsername = userData[0];
                String storedHashedPassword = userData[1];

                if (storedUsername.equals(username) && storedHashedPassword.equals(hashPassword(password))) {
                    return true; // User found and password matches
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user data: " + e.getMessage());
        }
        return false; // User not found or password doesn't match
    }
}
