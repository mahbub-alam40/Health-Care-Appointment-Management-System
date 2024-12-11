public class Person {
    private String name;
    private String email;

    // Constructor
    public Person(String name) {
        this.name = name;
    }

    // Getter for name (Encapsulation)
    public String getName() {
        return name;
    }

    // Setter for name (Encapsulation)
    public void setName(String name) {
        this.name = name;
    }

    // Getter for email (Encapsulation)
    public String getEmail() {
        return email;
    }

    // Setter for email (Encapsulation)
    public void setEmail(String email) {
        this.email = email;
    }

    // Display details
    public void displayDetails() {
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
    }
}
