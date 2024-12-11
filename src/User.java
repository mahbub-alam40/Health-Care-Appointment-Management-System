import java.io.*;
import java.security.MessageDigest;
import java.util.Scanner;

public class User extends Person implements UserOperations {

    private String username;
    private String password; // Hashed password
    private String email;

    // Constructor
    public User(String username, String password, String name, String email) {
        super(name);
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Register user with hashed password
    @Override
    public void register(String username, String password, String name, String email) {
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
    @Override
    public boolean login(String username, String password) throws Exception {
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

    // Hash password using SHA-256
    private String hashPassword(String password) throws Exception {
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

        // Create user object and register
        User user = new User(username, password, name, email);
        user.register(username, password, name, email);
    }
}
