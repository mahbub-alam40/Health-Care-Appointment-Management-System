public class AdminUser extends User {

    // Constructor for AdminUser
    public AdminUser(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

    @Override
    public void register(String username, String password, String name, String email) {
        // Admin can register with a different logic, if necessary
        super.register(username, password, name, email); // Optionally reuse the parent method
        System.out.println("Admin Registration Successful!");
    }

    @Override
    public boolean login(String username, String password) throws Exception {
        // Admin login logic can be different if needed
        if ("admin".equals(username) && "admin123".equals(password)) {
            System.out.println("Admin Login Successful!");
            return true;
        }
        return super.login(username, password); // Fall back to regular user login logic
    }
}
