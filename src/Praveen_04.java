import java.util.*;

public class Praveen_04 {

    // ─── User Profile ─────────────────────────────────────────────────
    static String username    = "praveen";
    static String password    = "pass123";
    static String fullName    = "Praveen Kumar";
    static String email       = "praveen@example.com";
    static String phone       = "9876543210";

    // ─── Exam Config ──────────────────────────────────────────────────
    static final int EXAM_DURATION_SECONDS = 120; // 2 minutes timer
    static int[] selectedAnswers;                  // stores user's chosen option (1-4), 0 = not answered

    static Scanner sc = new Scanner(System.in);

    // ═══════════════════════════════════════════════════════════════════
    //  MCQ Bank  (question, optionA, optionB, optionC, optionD, correctOption 1-4)
    // ═══════════════════════════════════════════════════════════════════
    static String[][] questions = {
        {"Which keyword is used to create a class in Java?",
            "class", "Class", "define", "struct", "1"},
        {"What is the size of an int in Java?",
            "2 bytes", "4 bytes", "8 bytes", "16 bytes", "2"},
        {"Which method is the entry point of a Java program?",
            "start()", "init()", "main()", "run()", "3"},
        {"Which of these is NOT a Java primitive type?",
            "int", "float", "String", "boolean", "3"},
        {"What does JVM stand for?",
            "Java Virtual Machine", "Java Variable Method", "Java Verified Module", "Java Value Manager", "1"},
        {"Which operator is used for string concatenation in Java?",
            "+", "&", ".", "*", "1"},
        {"Which collection does NOT allow duplicate elements?",
            "ArrayList", "LinkedList", "HashSet", "Vector", "3"},
        {"What is the default value of a boolean in Java?",
            "true", "false", "0", "null", "2"},
        {"Which keyword is used to inherit a class?",
            "implements", "extends", "inherits", "super", "2"},
        {"Which exception is thrown when dividing by zero in Java?",
            "NullPointerException", "IOException", "ArithmeticException", "NumberFormatException", "3"}
    };

    // ═══════════════════════════════════════════════════════════════════
    //  MAIN
    // ═══════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        printBanner();
        selectedAnswers = new int[questions.length]; // all 0 = unanswered

        boolean running = true;
        while (running) {
            printMainMenu();
            System.out.print("Choose an option (1-5): ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": login();             break;
                case "2": updateProfile();     break;
                case "3": startExam();         break;
                case "4": logout();            break;
                case "5": running = false;
                          System.out.println("\nExiting system. Goodbye!\n");
                          break;
                default:  System.out.println("⚠  Invalid option. Enter 1-5.");
            }
        }
    }

    // ─── Banner ───────────────────────────────────────────────────────
    static void printBanner() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║         ONLINE EXAMINATION SYSTEM            ║");
        System.out.println("║         Praveen_04  |  AICTE OASIS           ║");
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    static boolean loggedIn = false;

    // ─── Main Menu ────────────────────────────────────────────────────
    static void printMainMenu() {
        System.out.println("\n════════════════════════════════════════════");
        System.out.println("  Status : " + (loggedIn ? "✔ Logged in as " + fullName : "✘ Not logged in"));
        System.out.println("════════════════════════════════════════════");
        System.out.println("  1. Login");
        System.out.println("  2. Update Profile and Password");
        System.out.println("  3. Start Exam (MCQs + Timer + Auto Submit)");
        System.out.println("  4. Logout");
        System.out.println("  5. Exit");
        System.out.println("════════════════════════════════════════════");
    }

    // ═══════════════════════════════════════════════════════════════════
    //  1. LOGIN
    // ═══════════════════════════════════════════════════════════════════
    static void login() {
        if (loggedIn) {
            System.out.println("ℹ  You are already logged in as " + fullName + ".");
            return;
        }
        System.out.println("\n─── LOGIN ───────────────────────────────────");
        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Username : ");
            String uid = sc.nextLine().trim();
            System.out.print("Password : ");
            String pwd = sc.nextLine().trim();

            if (uid.equals(username) && pwd.equals(password)) {
                loggedIn = true;
                System.out.println("✔  Login successful! Welcome, " + fullName + ".");
                return;
            }
            attempts++;
            System.out.println("✘  Invalid credentials. Attempts remaining: " + (3 - attempts));
        }
        System.out.println("✘  Too many failed attempts. Please try again later.");
    }

    // ═══════════════════════════════════════════════════════════════════
    //  2. UPDATE PROFILE AND PASSWORD
    // ═══════════════════════════════════════════════════════════════════
    static void updateProfile() {
        if (!checkLogin()) return;

        System.out.println("\n─── UPDATE PROFILE & PASSWORD ───────────────");
        System.out.println("  Leave a field blank to keep the current value.\n");

        System.out.printf("Full Name  [%s]: ", fullName);
        String inp = sc.nextLine().trim();
        if (!inp.isEmpty()) fullName = inp;

        System.out.printf("Email      [%s]: ", email);
        inp = sc.nextLine().trim();
        if (!inp.isEmpty()) email = inp;

        System.out.printf("Phone      [%s]: ", phone);
        inp = sc.nextLine().trim();
        if (!inp.isEmpty()) phone = inp;

        System.out.println("\n─── Change Password ─────────────────────────");
        System.out.print("Current Password : ");
        String oldPwd = sc.nextLine().trim();
        if (!oldPwd.equals(password)) {
            System.out.println("⚠  Incorrect current password. Profile saved but password NOT changed.");
        } else {
            System.out.print("New Password     : ");
            String newPwd = sc.nextLine().trim();
            System.out.print("Confirm Password : ");
            String confirmPwd = sc.nextLine().trim();
            if (newPwd.isEmpty()) {
                System.out.println("⚠  Password cannot be empty.");
            } else if (!newPwd.equals(confirmPwd)) {
                System.out.println("⚠  Passwords do not match. Password NOT changed.");
            } else {
                password = newPwd;
                System.out.println("✔  Password updated successfully.");
            }
        }

        System.out.println("\n✔  Profile updated:");
        System.out.println("   Full Name : " + fullName);
        System.out.println("   Email     : " + email);
        System.out.println("   Phone     : " + phone);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  3. START EXAM  (MCQs + Timer + Auto Submit)
    // ═══════════════════════════════════════════════════════════════════
    static void startExam() {
        if (!checkLogin()) return;

        System.out.println("\n─── ONLINE EXAMINATION ──────────────────────");
        System.out.println("  Total Questions : " + questions.length);
        System.out.printf ("  Time Limit      : %d seconds%n", EXAM_DURATION_SECONDS);
        System.out.println("  Enter option number (1-4) for each question.");
        System.out.println("  Type 'submit' at any time to finish early.");
        System.out.println("─────────────────────────────────────────────");
        System.out.print("Press ENTER to begin...");
        sc.nextLine();

        // Reset answers
        Arrays.fill(selectedAnswers, 0);

        long startTime  = System.currentTimeMillis();
        long endTime    = startTime + (EXAM_DURATION_SECONDS * 1000L);

        for (int i = 0; i < questions.length; i++) {
            long remaining = (endTime - System.currentTimeMillis()) / 1000;
            if (remaining <= 0) {
                System.out.println("\n⏰  Time's up! Auto-submitting exam...");
                break;
            }

            System.out.printf("%n  ⏱  Time remaining: %d sec%n", remaining);
            System.out.printf("  Q%d. %s%n", (i + 1), questions[i][0]);
            System.out.printf("    1. %s%n", questions[i][1]);
            System.out.printf("    2. %s%n", questions[i][2]);
            System.out.printf("    3. %s%n", questions[i][3]);
            System.out.printf("    4. %s%n", questions[i][4]);
            System.out.print("  Your answer (1-4) or 'submit': ");

            String ans = sc.nextLine().trim().toLowerCase();

            if (ans.equals("submit")) {
                System.out.println("\n✔  Exam submitted early.");
                break;
            }

            // Check timer again after input
            if (System.currentTimeMillis() >= endTime) {
                System.out.println("\n⏰  Time's up! Auto-submitting exam...");
                break;
            }

            if (ans.matches("[1-4]")) {
                selectedAnswers[i] = Integer.parseInt(ans);
                System.out.println("  ✔  Answer recorded.");
            } else {
                System.out.println("  ⚠  Invalid input. Question skipped (marked unanswered).");
            }
        }

        showResult();
    }

    // ─── Show Result ──────────────────────────────────────────────────
    static void showResult() {
        System.out.println("\n════════════════════════════════════════════");
        System.out.println("              EXAM RESULT                   ");
        System.out.println("════════════════════════════════════════════");

        int correct   = 0;
        int wrong     = 0;
        int unanswered = 0;

        for (int i = 0; i < questions.length; i++) {
            int correctOption = Integer.parseInt(questions[i][5]);
            if (selectedAnswers[i] == 0) {
                unanswered++;
                System.out.printf("  Q%2d: %-45s → NOT ANSWERED (Correct: %s)%n",
                    (i + 1), questions[i][0].substring(0, Math.min(45, questions[i][0].length())),
                    questions[i][correctOption]);
            } else if (selectedAnswers[i] == correctOption) {
                correct++;
                System.out.printf("  Q%2d: ✔ CORRECT%n", (i + 1));
            } else {
                wrong++;
                System.out.printf("  Q%2d: ✘ WRONG  → You chose: %-15s | Correct: %s%n",
                    (i + 1), questions[i][selectedAnswers[i]], questions[i][correctOption]);
            }
        }

        double percent = ((double) correct / questions.length) * 100;
        String grade;
        if      (percent >= 90) grade = "A+";
        else if (percent >= 80) grade = "A";
        else if (percent >= 70) grade = "B";
        else if (percent >= 60) grade = "C";
        else if (percent >= 50) grade = "D";
        else                    grade = "F (Fail)";

        System.out.println("════════════════════════════════════════════");
        System.out.println("  Student   : " + fullName);
        System.out.printf ("  Score     : %d / %d%n", correct, questions.length);
        System.out.printf ("  Percentage: %.1f%%%n", percent);
        System.out.println("  Grade     : " + grade);
        System.out.println("  Correct   : " + correct);
        System.out.println("  Wrong     : " + wrong);
        System.out.println("  Unanswered: " + unanswered);
        System.out.println("════════════════════════════════════════════");
    }

    // ═══════════════════════════════════════════════════════════════════
    //  4. LOGOUT
    // ═══════════════════════════════════════════════════════════════════
    static void logout() {
        if (!loggedIn) {
            System.out.println("ℹ  You are not logged in.");
            return;
        }
        System.out.println("\n─── LOGOUT ──────────────────────────────────");
        System.out.print("Are you sure you want to logout? (yes/no): ");
        String confirm = sc.nextLine().trim().toLowerCase();
        if (confirm.equals("yes")) {
            loggedIn = false;
            Arrays.fill(selectedAnswers, 0);
            System.out.println("✔  Session closed. You have been logged out successfully.");
        } else {
            System.out.println("Logout cancelled.");
        }
    }

    // ─── Guard ────────────────────────────────────────────────────────
    static boolean checkLogin() {
        if (!loggedIn) {
            System.out.println("⚠  Please login first to access this feature.");
            return false;
        }
        return true;
    }
}