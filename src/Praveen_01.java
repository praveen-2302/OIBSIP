import java.util.ArrayList;
import java.util.Scanner;

public class Praveen_01 {

    // ─── Data Store ──────────────────────────────────────────────────
    static ArrayList<Reservation> reservations = new ArrayList<>();
    static int pnrCounter = 1000;
    static Scanner sc = new Scanner(System.in);

    // ─── Predefined Train Data ────────────────────────────────────────
    static String[][] trains = {
        {"101", "Chennai Express"},
        {"102", "Coimbatore Superfast"},
        {"103", "Tiruppur Intercity"},
        {"104", "Mumbai Rajdhani"},
        {"105", "Delhi Shatabdi"}
    };

    // ─── Login Credentials ────────────────────────────────────────────
    static String validUser = "admin";
    static String validPass = "admin123";

    // ═══════════════════════════════════════════════════════════════════
    //  Reservation Model
    // ═══════════════════════════════════════════════════════════════════
    static class Reservation {
        int    pnr;
        String passengerName;
        String trainNumber;
        String trainName;
        String classType;
        String dateOfJourney;
        String from;
        String destination;
        String status; // CONFIRMED / CANCELLED

        Reservation(int pnr, String passengerName, String trainNumber,
                    String trainName, String classType, String dateOfJourney,
                    String from, String destination) {
            this.pnr           = pnr;
            this.passengerName = passengerName;
            this.trainNumber   = trainNumber;
            this.trainName     = trainName;
            this.classType     = classType;
            this.dateOfJourney = dateOfJourney;
            this.from          = from;
            this.destination   = destination;
            this.status        = "CONFIRMED";
        }

        void print() {
            System.out.println("  ┌─────────────────────────────────────────┐");
            System.out.printf ("  │  PNR            : %-22d│%n", pnr);
            System.out.printf ("  │  Passenger Name : %-22s│%n", passengerName);
            System.out.printf ("  │  Train Number   : %-22s│%n", trainNumber);
            System.out.printf ("  │  Train Name     : %-22s│%n", trainName);
            System.out.printf ("  │  Class Type     : %-22s│%n", classType);
            System.out.printf ("  │  Date of Journey: %-22s│%n", dateOfJourney);
            System.out.printf ("  │  From           : %-22s│%n", from);
            System.out.printf ("  │  Destination    : %-22s│%n", destination);
            System.out.printf ("  │  Status         : %-22s│%n", status);
            System.out.println("  └─────────────────────────────────────────┘");
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    //  MAIN
    // ═══════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        printBanner();

        // ── Login ──
        if (!login()) {
            System.out.println("\n✘  Access denied. Exiting system.");
            return;
        }

        // ── Main Menu ──
        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Choose an option (1-4): ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": loginForm();        break;
                case "2": reservationForm();  break;
                case "3": cancellationForm(); break;
                case "4": running = false;
                          System.out.println("\nThank you for using the Online Reservation System. Goodbye!\n");
                          break;
                default:  System.out.println("⚠  Invalid option. Please enter a number between 1 and 4.");
            }
        }
    }

    // ─── Banner ───────────────────────────────────────────────────────
    static void printBanner() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║       ONLINE RESERVATION SYSTEM              ║");
        System.out.println("║       Praveen_03  |  AICTE OASIS Project     ║");
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    // ─── Login ────────────────────────────────────────────────────────
    static boolean login() {
        System.out.println("\n─── LOGIN FORM ──────────────────────────────");
        int attempts = 0;
        while (attempts < 3) {
            System.out.print("User ID  : ");
            String uid = sc.nextLine().trim();
            System.out.print("Password : ");
            String pwd = sc.nextLine().trim();

            if (uid.equals(validUser) && pwd.equals(validPass)) {
                System.out.println("✔  Login successful!\n");
                return true;
            }
            attempts++;
            System.out.println("✘  Invalid credentials. Attempts remaining: " + (3 - attempts));
        }
        return false;
    }

    // ─── Main Menu ────────────────────────────────────────────────────
    static void printMenu() {
        System.out.println("\n════════════════════════════════════════════");
        System.out.println("            MAIN MENU                       ");
        System.out.println("════════════════════════════════════════════");
        System.out.println("  1. Login Form (Change User)");
        System.out.println("  2. Reservation Form");
        System.out.println("  3. Cancellation Form");
        System.out.println("  4. Exit");
        System.out.println("════════════════════════════════════════════");
    }

    // ─── Module 1: Login Form ─────────────────────────────────────────
    static void loginForm() {
        System.out.println("\n─── CHANGE USER / RE-LOGIN ──────────────────");
        login();
    }

    // ─── Module 2: Reservation Form ───────────────────────────────────
    static void reservationForm() {
        System.out.println("\n─── RESERVATION FORM ────────────────────────");

        System.out.print("Passenger Name        : ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) { System.out.println("⚠  Name cannot be empty."); return; }

        // Show available trains
        System.out.println("\nAvailable Trains:");
        for (String[] t : trains) {
            System.out.printf("  Train No: %-6s  Name: %s%n", t[0], t[1]);
        }
        System.out.print("\nEnter Train Number    : ");
        String trainNo = sc.nextLine().trim();

        String trainName = getTrainName(trainNo);
        if (trainName == null) {
            System.out.println("⚠  Train number not found.");
            return;
        }
        System.out.println("Train Name (auto)     : " + trainName);

        System.out.println("\nClass Types: SL (Sleeper) | 3A (3-Tier AC) | 2A (2-Tier AC) | 1A (First AC) | GN (General)");
        System.out.print("Class Type            : ");
        String classType = sc.nextLine().trim().toUpperCase();
        if (!classType.matches("SL|3A|2A|1A|GN")) {
            System.out.println("⚠  Invalid class type. Use SL / 3A / 2A / 1A / GN.");
            return;
        }

        System.out.print("Date of Journey (DD/MM/YYYY): ");
        String doj = sc.nextLine().trim();
        if (!doj.matches("\\d{2}/\\d{2}/\\d{4}")) {
            System.out.println("⚠  Invalid date format. Use DD/MM/YYYY.");
            return;
        }

        System.out.print("From (Source Station) : ");
        String from = sc.nextLine().trim();
        if (from.isEmpty()) { System.out.println("⚠  Source cannot be empty."); return; }

        System.out.print("Destination Station   : ");
        String dest = sc.nextLine().trim();
        if (dest.isEmpty()) { System.out.println("⚠  Destination cannot be empty."); return; }

        // Insert reservation
        int pnr = ++pnrCounter;
        Reservation r = new Reservation(pnr, name, trainNo, trainName, classType, doj, from, dest);
        reservations.add(r);

        System.out.println("\n✔  Reservation confirmed!");
        r.print();
    }

    // ─── Module 3: Cancellation Form ──────────────────────────────────
    static void cancellationForm() {
        System.out.println("\n─── CANCELLATION FORM ───────────────────────");
        System.out.print("Enter PNR Number: ");
        String input = sc.nextLine().trim();

        int pnr;
        try {
            pnr = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("⚠  Invalid PNR. Please enter a numeric value.");
            return;
        }

        Reservation found = null;
        for (Reservation r : reservations) {
            if (r.pnr == pnr) { found = r; break; }
        }

        if (found == null) {
            System.out.println("⚠  No reservation found with PNR: " + pnr);
            return;
        }

        System.out.println("\nReservation Details:");
        found.print();

        if (found.status.equals("CANCELLED")) {
            System.out.println("⚠  This ticket is already cancelled.");
            return;
        }

        System.out.print("\nDo you want to cancel this ticket? (yes/no): ");
        String confirm = sc.nextLine().trim().toLowerCase();

        if (confirm.equals("yes")) {
            found.status = "CANCELLED";
            System.out.println("✔  Ticket with PNR " + pnr + " has been successfully cancelled.");
        } else {
            System.out.println("Cancellation aborted. Your ticket is still active.");
        }
    }

    // ─── Utility ──────────────────────────────────────────────────────
    static String getTrainName(String trainNo) {
        for (String[] t : trains) {
            if (t[0].equals(trainNo)) return t[1];
        }
        return null;
    }
}