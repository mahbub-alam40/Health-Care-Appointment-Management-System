import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Doctor {
    private String name;

    // Constructor
    public Doctor(String name) {
        this.name = name;
    }

    // Add a doctor to the list
    public static void addDoctor(String doctorName) throws IOException {
        FileWriter fw = new FileWriter("doctors.txt", true); // Append mode
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(doctorName);
        bw.newLine();
        bw.close();
        System.out.println("Doctor added: " + doctorName);
    }

    // Load all doctors from the file
    public static List<String> loadDoctors() throws IOException {
        List<String> doctors = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("doctors.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            doctors.add(line);
        }
        br.close();
        return doctors;
    }

    // Display the list of doctors with numbers for selection
    public static void displayDoctors() throws IOException {
        List<String> doctors = loadDoctors();
        System.out.println("Available Doctors:");
        for (int i = 0; i < doctors.size(); i++) {
            System.out.println((i + 1) + ". " + doctors.get(i));
        }
    }

    // Select a doctor by number
    public static String selectDoctor() throws IOException {
        displayDoctors();

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of the doctor you want to book an appointment with: ");
        int doctorIndex = sc.nextInt();

        List<String> doctors = loadDoctors();
        if (doctorIndex > 0 && doctorIndex <= doctors.size()) {
            return doctors.get(doctorIndex - 1);
        } else {
            System.out.println("Invalid selection. Please try again.");
            return selectDoctor(); // Retry selection
        }
    }
}
