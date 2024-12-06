import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Appointment {

    // Regular expressions for validating date (YYYY-MM-DD) and time (HH:MM)
    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";
    private static final String TIME_REGEX = "^\\d{2}:\\d{2}$";

    private static boolean isValidDate(String date) {
        Pattern pattern = Pattern.compile(DATE_REGEX);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    private static boolean isValidTime(String time) {
        Pattern pattern = Pattern.compile(TIME_REGEX);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }

    // Book appointment
    public static void bookAppointment(String doctorName, String appointmentDate, String appointmentTime, String username) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("appointments.txt", true))) {
            bw.write(username + "," + doctorName + "," + appointmentDate + "," + appointmentTime);
            bw.newLine();
            System.out.println("Appointment booked with " + doctorName);
        }
    }

    // Appointment booking (Terminal-based input)
    public static void bookAppointmentTerminal(String username) throws IOException {
        Scanner sc = new Scanner(System.in);

        // Load and display doctor list
        List<String> doctors = loadDoctors();
        if (doctors.isEmpty()) {
            System.out.println("No doctors available!");
            return;
        }

        System.out.print("Select a doctor by number: ");
        int doctorChoice = sc.nextInt();
        sc.nextLine(); // Consume newline

        if (doctorChoice < 1 || doctorChoice > doctors.size()) {
            System.out.println("Invalid choice! Please try again.");
            return;
        }
        String doctorName = doctors.get(doctorChoice - 1);

        String appointmentDate = "";
        String appointmentTime = "";

        // Validate date format
        while (true) {
            System.out.print("Enter appointment date (YYYY-MM-DD): ");
            appointmentDate = sc.nextLine();
            if (isValidDate(appointmentDate)) {
                break;
            } else {
                System.out.println("Invalid date format! Please use YYYY-MM-DD.");
            }
        }

        // Validate time format
        while (true) {
            System.out.print("Enter appointment time (HH:MM): ");
            appointmentTime = sc.nextLine();
            if (isValidTime(appointmentTime)) {
                break;
            } else {
                System.out.println("Invalid time format! Please use HH:MM.");
            }
        }

        bookAppointment(doctorName, appointmentDate, appointmentTime, username);
    }

    // View appointment history (for a specific user)
    public static void viewAppointmentHistory(String username) throws IOException {
        File inputFile = new File("appointments.txt");

        // Check if the file exists
        if (!inputFile.exists()) {
            System.out.println("Appointments file not found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            boolean found = false;
            System.out.println("Appointment History for " + username + ":");
            while ((line = br.readLine()) != null) {
                String[] appointmentData = line.split(",");
                if (appointmentData[0].equals(username)) {
                    found = true;
                    System.out.println("Doctor: " + appointmentData[1] + ", Date: " + appointmentData[2] + ", Time: " + appointmentData[3]);
                }
            }
            if (!found) {
                System.out.println("No appointment history found for " + username);
            }
        }
    }

    // Appointment cancellation (Terminal-based input)
    public static void cancelAppointmentTerminal(String username) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter doctor name: ");
        String doctorName = sc.nextLine();

        String appointmentDate = "";
        String appointmentTime = "";

        // Validate date format
        while (true) {
            System.out.print("Enter appointment date (YYYY-MM-DD): ");
            appointmentDate = sc.nextLine();
            if (isValidDate(appointmentDate)) {
                break;
            } else {
                System.out.println("Invalid date format! Please use YYYY-MM-DD.");
            }
        }

        // Validate time format
        while (true) {
            System.out.print("Enter appointment time (HH:MM): ");
            appointmentTime = sc.nextLine();
            if (isValidTime(appointmentTime)) {
                break;
            } else {
                System.out.println("Invalid time format! Please use HH:MM.");
            }
        }

        cancelAppointment(doctorName, appointmentDate, appointmentTime, username);
    }

    // Appointment rescheduling (Terminal-based input)
    public static void rescheduleAppointmentTerminal(String username) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter doctor name: ");
        String doctorName = sc.nextLine();

        String oldDate = "";
        String oldTime = "";
        String newDate = "";
        String newTime = "";

        // Validate old date format
        while (true) {
            System.out.print("Enter current appointment date (YYYY-MM-DD): ");
            oldDate = sc.nextLine();
            if (isValidDate(oldDate)) {
                break;
            } else {
                System.out.println("Invalid date format! Please use YYYY-MM-DD.");
            }
        }

        // Validate old time format
        while (true) {
            System.out.print("Enter current appointment time (HH:MM): ");
            oldTime = sc.nextLine();
            if (isValidTime(oldTime)) {
                break;
            } else {
                System.out.println("Invalid time format! Please use HH:MM.");
            }
        }

        // Validate new date format
        while (true) {
            System.out.print("Enter new appointment date (YYYY-MM-DD): ");
            newDate = sc.nextLine();
            if (isValidDate(newDate)) {
                break;
            } else {
                System.out.println("Invalid date format! Please use YYYY-MM-DD.");
            }
        }

        // Validate new time format
        while (true) {
            System.out.print("Enter new appointment time (HH:MM): ");
            newTime = sc.nextLine();
            if (isValidTime(newTime)) {
                break;
            } else {
                System.out.println("Invalid time format! Please use HH:MM.");
            }
        }

        rescheduleAppointment(doctorName, oldDate, oldTime, newDate, newTime, username);
    }

    // Load and display doctor list
    public static List<String> loadDoctors() throws IOException {
        List<String> doctors = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("doctors.txt"))) {
            String line;
            int index = 1;
            System.out.println("\nAvailable Doctors:");
            while ((line = br.readLine()) != null) {
                doctors.add(line);
                System.out.println(index + ". " + line);
                index++;
            }
        }
        return doctors;
    }

    // Helper method for rescheduling and canceling appointments
    public static void rescheduleAppointment(String doctorName, String oldDate, String oldTime, String newDate, String newTime, String username) throws IOException {
        // Implementation for rescheduling an appointment
        File inputFile = new File("appointments.txt");
        File tempFile = new File("temp_appointments.txt");

        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] appointmentData = line.split(",");
                if (appointmentData[0].equals(username)
                        && appointmentData[1].equals(doctorName)
                        && appointmentData[2].equals(oldDate)
                        && appointmentData[3].equals(oldTime)) {
                    found = true;
                    // Write the updated appointment details
                    bw.write(username + "," + doctorName + "," + newDate + "," + newTime);
                } else {
                    bw.write(line); // Write existing appointments as is
                }
                bw.newLine();
            }
        }

        if (found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
            System.out.println("Appointment successfully rescheduled.");
        } else {
            tempFile.delete();
            System.out.println("No matching appointment found to reschedule.");
        }
    }

    // Helper method to cancel an appointment
    public static void cancelAppointment(String doctorName, String appointmentDate, String appointmentTime, String username) throws IOException {
        // Implementation for canceling an appointment
        File inputFile = new File("appointments.txt");
        File tempFile = new File("temp_appointments.txt");

        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] appointmentData = line.split(",");
                if (appointmentData[0].equals(username)
                        && appointmentData[1].equals(doctorName)
                        && appointmentData[2].equals(appointmentDate)
                        && appointmentData[3].equals(appointmentTime)) {
                    found = true;
                    continue; // Skip writing this line to effectively delete the appointment
                }
                bw.write(line);
                bw.newLine();
            }
        }

        if (found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
            System.out.println("Appointment successfully canceled.");
        } else {
            tempFile.delete();
            System.out.println("No matching appointment found to cancel.");
        }
    }
}
