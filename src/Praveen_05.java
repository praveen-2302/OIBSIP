import java.util.*;

public class Praveen_05 {

    // ═══════════════════════════════════════════════════════════════════
    //  Book Model
    // ═══════════════════════════════════════════════════════════════════
    static class Book {
        int    bookId;
        String title;
        String author;
        String category;
        int    totalCopies;
        int    availableCopies;

        Book(int bookId, String title, String author, String category, int totalCopies) {
            this.bookId          = bookId;
            this.title           = title;
            this.author          = author;
            this.category        = category;
            this.totalCopies     = totalCopies;
            this.availableCopies = totalCopies;
        }

        void print() {
            System.out.printf("  │ %-4d │ %-28s │ %-18s │ %-12s │ %5d │ %9d │%n",
                bookId, title, author, category, totalCopies, availableCopies);
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    //  Member Model
    // ═══════════════════════════════════════════════════════════════════
    static class Member {
        int    memberId;
        String name;
        String email;
        String phone;

        Member(int memberId, String name, String email, String phone) {
            this.memberId = memberId;
            this.name     = name;
            this.email    = email;
            this.phone    = phone;
        }

        void print() {
            System.out.printf("  │ %-6d │ %-20s │ %-25s │ %-12s │%n",
                memberId, name, email, phone);
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    //  Issue Record Model
    // ═══════════════════════════════════════════════════════════════════
    static class IssueRecord {
        int    recordId;
        int    memberId;
        int    bookId;
        String memberName;
        String bookTitle;
        String issueDate;
        String returnDate;
        String status;    // ISSUED / RETURNED
        double fine;

        IssueRecord(int recordId, int memberId, String memberName,
                    int bookId, String bookTitle, String issueDate) {
            this.recordId   = recordId;
            this.memberId   = memberId;
            this.memberName = memberName;
            this.bookId     = bookId;
            this.bookTitle  = bookTitle;
            this.issueDate  = issueDate;
            this.returnDate = "-";
            this.status     = "ISSUED";
            this.fine       = 0.0;
        }

        void print() {
            System.out.printf("  │ %-4d │ %-6d │ %-18s │ %-4d │ %-22s │ %-10s │ %-10s │ %6.1f │ %-8s │%n",
                recordId, memberId, memberName, bookId, bookTitle,
                issueDate, returnDate, fine, status);
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    //  Data Stores
    // ═══════════════════════════════════════════════════════════════════
    static ArrayList<Book>        books       = new ArrayList<>();
    static ArrayList<Member>      members     = new ArrayList<>();
    static ArrayList<IssueRecord> issueRecords = new ArrayList<>();

    static int bookIdCounter   = 100;
    static int memberIdCounter = 200;
    static int recordIdCounter = 300;

    // ─── Credentials ──────────────────────────────────────────────────
    static final String ADMIN_USER = "admin";
    static final String ADMIN_PASS = "admin123";
    static final String USER_PASS  = "user123";   // all normal users share this demo password

    static Scanner sc       = new Scanner(System.in);
    static boolean loggedIn = false;
    static boolean isAdmin  = false;
    static String  currentUser = "";

    // ═══════════════════════════════════════════════════════════════════
    //  MAIN
    // ═══════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        seedData();
        printBanner();

        boolean running = true;
        while (running) {
            if (!loggedIn) {
                printGuestMenu();
                System.out.print("Choose option: ");
                String ch = sc.nextLine().trim();
                switch (ch) {
                    case "1": loginMenu();  break;
                    case "2": running = false;
                              System.out.println("\nGoodbye!\n"); break;
                    default:  System.out.println("⚠  Invalid option.");
                }
            } else if (isAdmin) {
                adminMenu();
            } else {
                userMenu();
            }
        }
    }

    // ─── Seed Sample Data ─────────────────────────────────────────────
    static void seedData() {
        books.add(new Book(++bookIdCounter, "Java Programming",       "James Gosling",   "Technology",  5));
        books.add(new Book(++bookIdCounter, "Data Structures",        "Mark Allen",      "Computer Sci",4));
        books.add(new Book(++bookIdCounter, "Clean Code",             "Robert Martin",   "Technology",  3));
        books.add(new Book(++bookIdCounter, "Wings of Fire",          "A.P.J Abdul Kalam","Biography",  6));
        books.add(new Book(++bookIdCounter, "The Alchemist",          "Paulo Coelho",    "Fiction",     5));
        books.add(new Book(++bookIdCounter, "Operating Systems",      "Galvin Silberschatz","Computer Sci",3));

        members.add(new Member(++memberIdCounter, "Praveen Kumar",  "praveen@mail.com", "9876543210"));
        members.add(new Member(++memberIdCounter, "Anitha Raj",     "anitha@mail.com",  "9876501234"));
        members.add(new Member(++memberIdCounter, "Karthi Vel",     "karthi@mail.com",  "9123456789"));
    }

    // ─── Banner ───────────────────────────────────────────────────────
    static void printBanner() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║      DIGITAL LIBRARY MANAGEMENT SYSTEM       ║");
        System.out.println("║      Praveen_05  |  AICTE OASIS Project      ║");
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    // ─── Guest Menu ───────────────────────────────────────────────────
    static void printGuestMenu() {
        System.out.println("\n════════════════════════════════════════════");
        System.out.println("  1. Login");
        System.out.println("  2. Exit");
        System.out.println("════════════════════════════════════════════");
    }

    // ═══════════════════════════════════════════════════════════════════
    //  LOGIN
    // ═══════════════════════════════════════════════════════════════════
    static void loginMenu() {
        System.out.println("\n─── LOGIN ───────────────────────────────────");
        System.out.println("  Role: 1. Admin   2. User");
        System.out.print("  Choose role: ");
        String role = sc.nextLine().trim();

        int attempts = 0;
        while (attempts < 3) {
            System.out.print("  Username : ");
            String uid = sc.nextLine().trim();
            System.out.print("  Password : ");
            String pwd = sc.nextLine().trim();

            if (role.equals("1")) {
                // Admin login
                if (uid.equals(ADMIN_USER) && pwd.equals(ADMIN_PASS)) {
                    loggedIn    = true;
                    isAdmin     = true;
                    currentUser = "Admin";
                    System.out.println("✔  Admin login successful!");
                    return;
                }
            } else {
                // User login — username must match a member name (case-insensitive)
                Member m = findMemberByName(uid);
                if (m != null && pwd.equals(USER_PASS)) {
                    loggedIn    = true;
                    isAdmin     = false;
                    currentUser = m.name;
                    System.out.println("✔  Login successful! Welcome, " + currentUser + ".");
                    return;
                }
            }
            attempts++;
            System.out.println("✘  Invalid credentials. Attempts left: " + (3 - attempts));
        }
        System.out.println("✘  Too many failed attempts.");
    }

    // ═══════════════════════════════════════════════════════════════════
    //  ADMIN MODULE
    // ═══════════════════════════════════════════════════════════════════
    static void adminMenu() {
        System.out.println("\n════════════════════════════════════════════");
        System.out.println("  ADMIN PANEL  |  Logged in as: " + currentUser);
        System.out.println("════════════════════════════════════════════");
        System.out.println("  ── Book Management ──");
        System.out.println("  1. Add Book");
        System.out.println("  2. Update Book");
        System.out.println("  3. Delete Book");
        System.out.println("  4. View All Books");
        System.out.println("  ── Member Management ──");
        System.out.println("  5. Add Member");
        System.out.println("  6. Update Member");
        System.out.println("  7. Delete Member");
        System.out.println("  8. View All Members");
        System.out.println("  ── Issue & Return ──");
        System.out.println("  9. Issue Book");
        System.out.println(" 10. Return Book & Fine");
        System.out.println(" 11. View All Issue Records");
        System.out.println("  ── Session ──");
        System.out.println(" 12. Logout");
        System.out.println("════════════════════════════════════════════");
        System.out.print("Choose option: ");
        String ch = sc.nextLine().trim();

        switch (ch) {
            case "1":  addBook();            break;
            case "2":  updateBook();         break;
            case "3":  deleteBook();         break;
            case "4":  viewAllBooks();       break;
            case "5":  addMember();          break;
            case "6":  updateMember();       break;
            case "7":  deleteMember();       break;
            case "8":  viewAllMembers();     break;
            case "9":  issueBook();          break;
            case "10": returnBook();         break;
            case "11": viewAllRecords();     break;
            case "12": logout();             break;
            default:   System.out.println("⚠  Invalid option.");
        }
    }

    // ── Add Book ──────────────────────────────────────────────────────
    static void addBook() {
        System.out.println("\n─── ADD BOOK ────────────────────────────────");
        System.out.print("Title          : "); String title  = sc.nextLine().trim();
        System.out.print("Author         : "); String author = sc.nextLine().trim();
        System.out.print("Category       : "); String cat    = sc.nextLine().trim();
        System.out.print("Total Copies   : "); int copies    = readInt();
        if (title.isEmpty() || author.isEmpty() || copies <= 0) {
            System.out.println("⚠  Invalid input. Book not added."); return;
        }
        Book b = new Book(++bookIdCounter, title, author, cat, copies);
        books.add(b);
        System.out.println("✔  Book added with ID: " + b.bookId);
    }

    // ── Update Book ───────────────────────────────────────────────────
    static void updateBook() {
        System.out.println("\n─── UPDATE BOOK ─────────────────────────────");
        viewAllBooks();
        System.out.print("Enter Book ID to update: "); int id = readInt();
        Book b = findBook(id);
        if (b == null) { System.out.println("⚠  Book not found."); return; }

        System.out.printf("Title    [%s]: ", b.title);  String t = sc.nextLine().trim(); if (!t.isEmpty()) b.title  = t;
        System.out.printf("Author   [%s]: ", b.author); String a = sc.nextLine().trim(); if (!a.isEmpty()) b.author = a;
        System.out.printf("Category [%s]: ", b.category); String c = sc.nextLine().trim(); if (!c.isEmpty()) b.category = c;
        System.out.printf("Total Copies [%d]: ", b.totalCopies);
        String cp = sc.nextLine().trim();
        if (!cp.isEmpty()) {
            int newCopies = Integer.parseInt(cp);
            b.availableCopies += (newCopies - b.totalCopies);
            b.totalCopies = newCopies;
        }
        System.out.println("✔  Book updated.");
    }

    // ── Delete Book ───────────────────────────────────────────────────
    static void deleteBook() {
        System.out.println("\n─── DELETE BOOK ─────────────────────────────");
        viewAllBooks();
        System.out.print("Enter Book ID to delete: "); int id = readInt();
        Book b = findBook(id);
        if (b == null) { System.out.println("⚠  Book not found."); return; }
        System.out.print("Confirm delete '" + b.title + "'? (yes/no): ");
        if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
            books.remove(b);
            System.out.println("✔  Book deleted.");
        } else {
            System.out.println("Cancelled.");
        }
    }

    // ── View All Books ────────────────────────────────────────────────
    static void viewAllBooks() {
        System.out.println("\n─── BOOK LIST ───────────────────────────────────────────────────────────────────────────────");
        System.out.println("  │ ID   │ Title                        │ Author             │ Category     │ Total │ Available │");
        System.out.println("  │──────│──────────────────────────────│────────────────────│──────────────│───────│───────────│");
        for (Book b : books) b.print();
        System.out.println("  Total books: " + books.size());
    }

    // ── Add Member ────────────────────────────────────────────────────
    static void addMember() {
        System.out.println("\n─── ADD MEMBER ──────────────────────────────");
        System.out.print("Full Name : "); String name  = sc.nextLine().trim();
        System.out.print("Email     : "); String email = sc.nextLine().trim();
        System.out.print("Phone     : "); String phone = sc.nextLine().trim();
        if (name.isEmpty()) { System.out.println("⚠  Name cannot be empty."); return; }
        Member m = new Member(++memberIdCounter, name, email, phone);
        members.add(m);
        System.out.println("✔  Member added with ID: " + m.memberId);
    }

    // ── Update Member ─────────────────────────────────────────────────
    static void updateMember() {
        System.out.println("\n─── UPDATE MEMBER ───────────────────────────");
        viewAllMembers();
        System.out.print("Enter Member ID to update: "); int id = readInt();
        Member m = findMember(id);
        if (m == null) { System.out.println("⚠  Member not found."); return; }

        System.out.printf("Name  [%s]: ", m.name);  String n = sc.nextLine().trim(); if (!n.isEmpty()) m.name  = n;
        System.out.printf("Email [%s]: ", m.email); String e = sc.nextLine().trim(); if (!e.isEmpty()) m.email = e;
        System.out.printf("Phone [%s]: ", m.phone); String p = sc.nextLine().trim(); if (!p.isEmpty()) m.phone = p;
        System.out.println("✔  Member updated.");
    }

    // ── Delete Member ─────────────────────────────────────────────────
    static void deleteMember() {
        System.out.println("\n─── DELETE MEMBER ───────────────────────────");
        viewAllMembers();
        System.out.print("Enter Member ID to delete: "); int id = readInt();
        Member m = findMember(id);
        if (m == null) { System.out.println("⚠  Member not found."); return; }
        System.out.print("Confirm delete member '" + m.name + "'? (yes/no): ");
        if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
            members.remove(m);
            System.out.println("✔  Member deleted.");
        } else {
            System.out.println("Cancelled.");
        }
    }

    // ── View All Members ──────────────────────────────────────────────
    static void viewAllMembers() {
        System.out.println("\n─── MEMBER LIST ─────────────────────────────────────────────────────────────");
        System.out.println("  │ MemberID │ Name                 │ Email                     │ Phone        │");
        System.out.println("  │──────────│──────────────────────│───────────────────────────│──────────────│");
        for (Member m : members) m.print();
        System.out.println("  Total members: " + members.size());
    }

    // ── Issue Book ────────────────────────────────────────────────────
    static void issueBook() {
        System.out.println("\n─── ISSUE BOOK ──────────────────────────────");
        viewAllMembers();
        System.out.print("Member ID : "); int mid = readInt();
        Member m = findMember(mid);
        if (m == null) { System.out.println("⚠  Member not found."); return; }

        viewAllBooks();
        System.out.print("Book ID   : "); int bid = readInt();
        Book b = findBook(bid);
        if (b == null) { System.out.println("⚠  Book not found."); return; }
        if (b.availableCopies <= 0) { System.out.println("⚠  No copies available."); return; }

        System.out.print("Issue Date (DD/MM/YYYY): "); String date = sc.nextLine().trim();
        if (!date.matches("\\d{2}/\\d{2}/\\d{4}")) {
            System.out.println("⚠  Invalid date format."); return;
        }

        b.availableCopies--;
        IssueRecord rec = new IssueRecord(++recordIdCounter, m.memberId, m.name, b.bookId, b.title, date);
        issueRecords.add(rec);
        System.out.println("✔  Book issued. Record ID: " + rec.recordId);
    }

    // ── Return Book & Fine ────────────────────────────────────────────
    static void returnBook() {
        System.out.println("\n─── RETURN BOOK ─────────────────────────────");
        System.out.print("Enter Issue Record ID: "); int rid = readInt();

        IssueRecord rec = findRecord(rid);
        if (rec == null) { System.out.println("⚠  Record not found."); return; }
        if (rec.status.equals("RETURNED")) { System.out.println("⚠  Book already returned."); return; }

        System.out.print("Return Date (DD/MM/YYYY): "); String retDate = sc.nextLine().trim();
        if (!retDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
            System.out.println("⚠  Invalid date format."); return;
        }

        // Calculate fine (simple: ₹5 per day if late; demo uses fixed 0 if dates not parsed)
        int lateDays = calculateLateDays(rec.issueDate, retDate);
        rec.fine = lateDays > 14 ? (lateDays - 14) * 5.0 : 0.0;
        rec.returnDate = retDate;
        rec.status     = "RETURNED";

        Book b = findBook(rec.bookId);
        if (b != null) b.availableCopies++;

        System.out.println("✔  Book returned.");
        System.out.printf("   Late days: %d  |  Fine: ₹%.1f%n", Math.max(0, lateDays - 14), rec.fine);
    }

    // ── View All Records ──────────────────────────────────────────────
    static void viewAllRecords() {
        System.out.println("\n─── ISSUE RECORDS ────────────────────────────────────────────────────────────────────────────────────────────────────");
        System.out.println("  │ ID   │ MemID  │ Member Name        │ BkID │ Book Title             │ Issue      │ Return     │   Fine │ Status   │");
        System.out.println("  │──────│────────│────────────────────│──────│────────────────────────│────────────│────────────│────────│──────────│");
        for (IssueRecord r : issueRecords) r.print();
        System.out.println("  Total records: " + issueRecords.size());
    }

    // ═══════════════════════════════════════════════════════════════════
    //  USER MODULE
    // ═══════════════════════════════════════════════════════════════════
    static void userMenu() {
        System.out.println("\n════════════════════════════════════════════");
        System.out.println("  USER PANEL  |  Logged in as: " + currentUser);
        System.out.println("════════════════════════════════════════════");
        System.out.println("  1. Browse Books by Category");
        System.out.println("  2. Search Book by Title / Author");
        System.out.println("  3. View My Issued Books");
        System.out.println("  4. Email a Query (Simulated)");
        System.out.println("  5. Logout");
        System.out.println("════════════════════════════════════════════");
        System.out.print("Choose option: ");
        String ch = sc.nextLine().trim();

        switch (ch) {
            case "1": browseByCategory(); break;
            case "2": searchBook();       break;
            case "3": viewMyBooks();      break;
            case "4": emailQuery();       break;
            case "5": logout();           break;
            default:  System.out.println("⚠  Invalid option.");
        }
    }

    // ── Browse by Category ────────────────────────────────────────────
    static void browseByCategory() {
        System.out.println("\n─── BROWSE BY CATEGORY ──────────────────────");
        Set<String> cats = new LinkedHashSet<>();
        for (Book b : books) cats.add(b.category);
        System.out.println("  Available categories: " + cats);
        System.out.print("Enter category: "); String cat = sc.nextLine().trim();

        boolean found = false;
        for (Book b : books) {
            if (b.category.equalsIgnoreCase(cat)) {
                System.out.printf("  [%d] %-30s by %-20s  Available: %d%n",
                    b.bookId, b.title, b.author, b.availableCopies);
                found = true;
            }
        }
        if (!found) System.out.println("  No books found in category: " + cat);
    }

    // ── Search Book ───────────────────────────────────────────────────
    static void searchBook() {
        System.out.println("\n─── SEARCH BOOK ─────────────────────────────");
        System.out.print("Enter Title or Author keyword: "); String kw = sc.nextLine().trim().toLowerCase();
        boolean found = false;
        for (Book b : books) {
            if (b.title.toLowerCase().contains(kw) || b.author.toLowerCase().contains(kw)) {
                System.out.printf("  [%d] %-30s by %-20s | %s | Available: %d%n",
                    b.bookId, b.title, b.author, b.category, b.availableCopies);
                found = true;
            }
        }
        if (!found) System.out.println("  No books found for keyword: " + kw);
    }

    // ── View My Issued Books ──────────────────────────────────────────
    static void viewMyBooks() {
        System.out.println("\n─── MY ISSUED BOOKS ─────────────────────────");
        boolean found = false;
        for (IssueRecord r : issueRecords) {
            if (r.memberName.equalsIgnoreCase(currentUser)) {
                System.out.printf("  Record: %-4d | Book: %-22s | Issued: %-10s | Return: %-10s | Fine: ₹%-6.1f | %s%n",
                    r.recordId, r.bookTitle, r.issueDate, r.returnDate, r.fine, r.status);
                found = true;
            }
        }
        if (!found) System.out.println("  No issue records found for " + currentUser + ".");
    }

    // ── Email Query ───────────────────────────────────────────────────
    static void emailQuery() {
        System.out.println("\n─── EMAIL A QUERY ───────────────────────────");
        System.out.print("Your Email    : "); String from    = sc.nextLine().trim();
        System.out.print("Subject       : "); String subject = sc.nextLine().trim();
        System.out.print("Message       : "); String message = sc.nextLine().trim();
        System.out.println("\n✔  Your query has been submitted successfully!");
        System.out.println("   From    : " + from);
        System.out.println("   Subject : " + subject);
        System.out.println("   Message : " + message);
        System.out.println("   The library admin will respond shortly.");
    }

    // ─── Logout ───────────────────────────────────────────────────────
    static void logout() {
        System.out.println("\n─── LOGOUT ──────────────────────────────────");
        System.out.print("Confirm logout? (yes/no): ");
        if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
            loggedIn    = false;
            isAdmin     = false;
            currentUser = "";
            System.out.println("✔  Logged out successfully. Session closed.");
        } else {
            System.out.println("Logout cancelled.");
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    //  UTILITIES
    // ═══════════════════════════════════════════════════════════════════
    static Book findBook(int id) {
        for (Book b : books) if (b.bookId == id) return b;
        return null;
    }

    static Member findMember(int id) {
        for (Member m : members) if (m.memberId == id) return m;
        return null;
    }

    static Member findMemberByName(String name) {
        for (Member m : members) if (m.name.equalsIgnoreCase(name)) return m;
        return null;
    }

    static IssueRecord findRecord(int id) {
        for (IssueRecord r : issueRecords) if (r.recordId == id) return r;
        return null;
    }

    static int readInt() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /** Simple day-difference using DD/MM/YYYY strings. */
    static int calculateLateDays(String issueDate, String returnDate) {
        try {
            String[] ip = issueDate.split("/");
            String[] rp = returnDate.split("/");
            int iDay = Integer.parseInt(ip[0]), iMon = Integer.parseInt(ip[1]), iYr = Integer.parseInt(ip[2]);
            int rDay = Integer.parseInt(rp[0]), rMon = Integer.parseInt(rp[1]), rYr = Integer.parseInt(rp[2]);
            long iDays = iYr * 365L + iMon * 30L + iDay;
            long rDays = rYr * 365L + rMon * 30L + rDay;
            return (int) Math.max(0, rDays - iDays);
        } catch (Exception e) {
            return 0;
        }
    }
}