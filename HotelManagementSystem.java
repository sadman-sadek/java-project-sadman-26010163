import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * HotelManagementSystem.java
 * ---------------------------------------------------
 * A console-based Hotel Management System covering:
 *  1. Room Management (Add/View/Update - Deluxe, Suite, Standard)
 *  2. Guest Registration & Profiles
 *  3. Room Booking & Reservation
 *  4. Check-in / Check-out System
 *  5. Billing & Invoice Generation
 *  6. Room Service & Amenities Tracker
 *  7. Search & Filter Utility
 *  8. Admin Dashboard (secure login)
 * ---------------------------------------------------
 * Compile: javac HotelManagementSystem.java
 * Run    : java HotelManagementSystem
 */
public class HotelManagementSystem {

    // ================= In-memory Data Stores =================
    static List<Room> rooms = new ArrayList<>();
    static List<Guest> guests = new ArrayList<>();
    static List<Booking> bookings = new ArrayList<>();

    static int guestIdCounter = 1;
    static int bookingIdCounter = 1;

    static Scanner sc = new Scanner(System.in);
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Simple admin credentials for the secure login
    static final String ADMIN_USERNAME = "admin";
    static final String ADMIN_PASSWORD = "admin123";

    // ================= Entry Point =================
    public static void main(String[] args) {
        seedSampleRooms(); // pre-load a few rooms so the system isn't empty on first run

        System.out.println("=========================================");
        System.out.println("   WELCOME TO HOTEL MANAGEMENT SYSTEM");
        System.out.println("=========================================");

        if (adminLogin()) {
            adminDashboard();
        } else {
            System.out.println("Too many failed attempts. Exiting system.");
        }

        System.out.println("\nThank you for using Hotel Management System. Goodbye!");
    }

    // ================= Admin Login (Secure Access) =================
    static boolean adminLogin() {
        int attempts = 3;
        while (attempts > 0) {
            System.out.print("\nEnter Admin Username: ");
            String user = sc.nextLine().trim();
            System.out.print("Enter Admin Password: ");
            String pass = sc.nextLine().trim();

            if (user.equals(ADMIN_USERNAME) && pass.equals(ADMIN_PASSWORD)) {
                System.out.println("\nLogin successful! Welcome, " + user + ".");
                return true;
            } else {
                attempts--;
                System.out.println("Invalid credentials. Attempts left: " + attempts);
            }
        }
        return false;
    }

    // ================= Admin Dashboard (Main Menu) =================
    static void adminDashboard() {
        int choice;
        do {
            System.out.println("\n================ ADMIN DASHBOARD ================");
            System.out.println("1. Room Management");
            System.out.println("2. Guest Registration & Profiles");
            System.out.println("3. Room Booking & Reservation");
            System.out.println("4. Check-in / Check-out System");
            System.out.println("5. Billing & Invoice Generation");
            System.out.println("6. Room Service & Amenities Tracker");
            System.out.println("7. Search & Filter Utility");
            System.out.println("0. Logout / Exit");
            System.out.print("Enter your choice: ");

            choice = readInt();

            switch (choice) {
                case 1: roomManagementMenu(); break;
                case 2: guestManagementMenu(); break;
                case 3: bookingMenu(); break;
                case 4: checkInOutMenu(); break;
                case 5: billingMenu(); break;
                case 6: roomServiceMenu(); break;
                case 7: searchFilterMenu(); break;
                case 0: System.out.println("Logging out..."); break;
                default: System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    // ================= 1. ROOM MANAGEMENT =================
    static void roomManagementMenu() {
        int choice;
        do {
            System.out.println("\n------- Room Management -------");
            System.out.println("1. Add Room");
            System.out.println("2. View All Rooms");
            System.out.println("3. Update Room");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            choice = readInt();

            switch (choice) {
                case 1: addRoom(); break;
                case 2: viewRooms(rooms); break;
                case 3: updateRoom(); break;
                case 0: break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    static void addRoom() {
        System.out.print("Enter Room Number: ");
        String roomNumber = sc.nextLine().trim();

        if (findRoomByNumber(roomNumber) != null) {
            System.out.println("Room number already exists!");
            return;
        }

        System.out.println("Select Room Type: 1. Deluxe  2. Suite  3. Standard");
        int t = readInt();
        String type = (t == 1) ? "Deluxe" : (t == 2) ? "Suite" : "Standard";

        System.out.print("Enter Price Per Night: ");
        double price = readDouble();

        Room room = new Room(roomNumber, type, price);
        rooms.add(room);
        System.out.println("Room added successfully!");
    }

    static void viewRooms(List<Room> list) {
        if (list.isEmpty()) {
            System.out.println("No rooms found.");
            return;
        }
        System.out.println("\nRoomNo | Type      | Price/Night | Status");
        System.out.println("---------------------------------------------");
        for (Room r : list) {
            System.out.printf("%-6s | %-9s | %-11.2f | %s%n",
                    r.roomNumber, r.type, r.pricePerNight, r.status);
        }
    }

    static void updateRoom() {
        System.out.print("Enter Room Number to update: ");
        String roomNumber = sc.nextLine().trim();
        Room room = findRoomByNumber(roomNumber);

        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        System.out.println("1. Update Type  2. Update Price  3. Update Status");
        int c = readInt();
        switch (c) {
            case 1:
                System.out.println("New Type: 1. Deluxe  2. Suite  3. Standard");
                int t = readInt();
                room.type = (t == 1) ? "Deluxe" : (t == 2) ? "Suite" : "Standard";
                break;
            case 2:
                System.out.print("New Price Per Night: ");
                room.pricePerNight = readDouble();
                break;
            case 3:
                System.out.println("New Status: 1. Available  2. Occupied");
                int s = readInt();
                room.status = (s == 1) ? "Available" : "Occupied";
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }
        System.out.println("Room updated successfully!");
    }

    static Room findRoomByNumber(String roomNumber) {
        for (Room r : rooms) {
            if (r.roomNumber.equalsIgnoreCase(roomNumber)) return r;
        }
        return null;
    }

    // ================= 2. GUEST REGISTRATION & PROFILES =================
    static void guestManagementMenu() {
        int choice;
        do {
            System.out.println("\n------- Guest Registration & Profiles -------");
            System.out.println("1. Register New Guest");
            System.out.println("2. View All Guests");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            choice = readInt();

            switch (choice) {
                case 1: registerGuest(); break;
                case 2: viewGuests(guests); break;
                case 0: break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    static void registerGuest() {
        System.out.print("Enter Guest Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Enter Contact Number: ");
        String contact = sc.nextLine().trim();
        System.out.print("Enter ID Proof (e.g., NID/Passport No): ");
        String idProof = sc.nextLine().trim();

        Guest guest = new Guest(guestIdCounter++, name, contact, idProof);
        guests.add(guest);
        System.out.println("Guest registered successfully! Guest ID: " + guest.id);
    }

    static void viewGuests(List<Guest> list) {
        if (list.isEmpty()) {
            System.out.println("No guests found.");
            return;
        }
        System.out.println("\nID  | Name            | Contact       | ID Proof");
        System.out.println("---------------------------------------------------");
        for (Guest g : list) {
            System.out.printf("%-3d | %-15s | %-13s | %s%n",
                    g.id, g.name, g.contact, g.idProof);
        }
    }

    static Guest findGuestById(int id) {
        for (Guest g : guests) if (g.id == id) return g;
        return null;
    }

    // ================= 3. ROOM BOOKING & RESERVATION =================
    static void bookingMenu() {
        int choice;
        do {
            System.out.println("\n------- Room Booking & Reservation -------");
            System.out.println("1. Check Room Availability");
            System.out.println("2. Book a Room");
            System.out.println("3. View All Bookings");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            choice = readInt();

            switch (choice) {
                case 1: checkAvailability(); break;
                case 2: bookRoom(); break;
                case 3: viewBookings(bookings); break;
                case 0: break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    static void checkAvailability() {
        List<Room> available = new ArrayList<>();
        for (Room r : rooms) {
            if (r.status.equals("Available")) available.add(r);
        }
        System.out.println("\nAvailable Rooms:");
        viewRooms(available);
    }

    static void bookRoom() {
        System.out.print("Enter Guest ID (or 0 to register new guest): ");
        int gid = readInt();
        Guest guest;

        if (gid == 0) {
            registerGuest();
            guest = guests.get(guests.size() - 1);
        } else {
            guest = findGuestById(gid);
            if (guest == null) {
                System.out.println("Guest not found.");
                return;
            }
        }

        System.out.print("Enter Room Number to book: ");
        String roomNumber = sc.nextLine().trim();
        Room room = findRoomByNumber(roomNumber);

        if (room == null) {
            System.out.println("Room not found.");
            return;
        }
        if (!room.status.equals("Available")) {
            System.out.println("Room is not available for booking.");
            return;
        }

        LocalDate checkIn = readDate("Enter Check-in Date (yyyy-MM-dd): ");
        LocalDate checkOut = readDate("Enter Check-out Date (yyyy-MM-dd): ");

        if (!checkOut.isAfter(checkIn)) {
            System.out.println("Check-out date must be after check-in date.");
            return;
        }

        Booking booking = new Booking(bookingIdCounter++, guest, room, checkIn, checkOut);
        bookings.add(booking);
        room.status = "Reserved"; // reserved until actual check-in

        System.out.println("Booking successful! Booking ID: " + booking.bookingId);
    }

    static void viewBookings(List<Booking> list) {
        if (list.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }
        System.out.println("\nBookID | Guest           | Room | CheckIn    | CheckOut   | Status");
        System.out.println("---------------------------------------------------------------------");
        for (Booking b : list) {
            System.out.printf("%-6d | %-15s | %-4s | %-10s | %-10s | %s%n",
                    b.bookingId, b.guest.name, b.room.roomNumber,
                    b.checkInDate.format(dtf), b.checkOutDate.format(dtf), b.status);
        }
    }

    static Booking findBookingById(int id) {
        for (Booking b : bookings) if (b.bookingId == id) return b;
        return null;
    }

    // ================= 4. CHECK-IN / CHECK-OUT SYSTEM =================
    static void checkInOutMenu() {
        int choice;
        do {
            System.out.println("\n------- Check-in / Check-out System -------");
            System.out.println("1. Check-in Guest");
            System.out.println("2. Check-out Guest");
            System.out.println("3. View Active Stays");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            choice = readInt();

            switch (choice) {
                case 1: checkInGuest(); break;
                case 2: checkOutGuest(); break;
                case 3: viewActiveStays(); break;
                case 0: break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    static void checkInGuest() {
        System.out.print("Enter Booking ID to check-in: ");
        int id = readInt();
        Booking booking = findBookingById(id);

        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }
        if (!booking.status.equals("Booked")) {
            System.out.println("This booking cannot be checked in (current status: " + booking.status + ").");
            return;
        }

        booking.status = "Checked-in";
        booking.room.status = "Occupied";
        System.out.println("Guest checked in successfully. Room " + booking.room.roomNumber + " is now Occupied.");
    }

    static void checkOutGuest() {
        System.out.print("Enter Booking ID to check-out: ");
        int id = readInt();
        Booking booking = findBookingById(id);

        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }
        if (!booking.status.equals("Checked-in")) {
            System.out.println("This booking cannot be checked out (current status: " + booking.status + ").");
            return;
        }

        booking.status = "Checked-out";
        booking.room.status = "Available";
        System.out.println("Guest checked out successfully. Room " + booking.room.roomNumber + " is now Available.");
        generateInvoice(booking); // auto-generate invoice on checkout
    }

    static void viewActiveStays() {
        List<Booking> active = new ArrayList<>();
        for (Booking b : bookings) {
            if (b.status.equals("Checked-in")) active.add(b);
        }
        System.out.println("\nActive Stays:");
        viewBookings(active);
    }

    // ================= 5. BILLING & INVOICE GENERATION =================
    static void billingMenu() {
        int choice;
        do {
            System.out.println("\n------- Billing & Invoice Generation -------");
            System.out.println("1. Generate Invoice for a Booking");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            choice = readInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Booking ID: ");
                    int id = readInt();
                    Booking b = findBookingById(id);
                    if (b == null) System.out.println("Booking not found.");
                    else generateInvoice(b);
                    break;
                case 0: break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    static void generateInvoice(Booking booking) {
        long days = ChronoUnit.DAYS.between(booking.checkInDate, booking.checkOutDate);
        if (days <= 0) days = 1;

        double roomCost = days * booking.room.pricePerNight;
        double serviceCost = 0.0;
        for (ServiceCharge sc : booking.services) {
            serviceCost += sc.amount;
        }
        double total = roomCost + serviceCost;

        System.out.println("\n============ INVOICE ============");
        System.out.println("Booking ID   : " + booking.bookingId);
        System.out.println("Guest Name   : " + booking.guest.name);
        System.out.println("Room Number  : " + booking.room.roomNumber + " (" + booking.room.type + ")");
        System.out.println("Check-in     : " + booking.checkInDate.format(dtf));
        System.out.println("Check-out    : " + booking.checkOutDate.format(dtf));
        System.out.println("No. of Days  : " + days);
        System.out.printf("Room Charges : %.2f%n", roomCost);

        if (!booking.services.isEmpty()) {
            System.out.println("Service Charges:");
            for (ServiceCharge s : booking.services) {
                System.out.printf("   - %-20s : %.2f%n", s.description, s.amount);
            }
        }

        System.out.printf("----------------------------------%n");
        System.out.printf("TOTAL AMOUNT : %.2f%n", total);
        System.out.println("==================================");
    }

    // ================= 6. ROOM SERVICE & AMENITIES TRACKER =================
    static void roomServiceMenu() {
        int choice;
        do {
            System.out.println("\n------- Room Service & Amenities Tracker -------");
            System.out.println("1. Add Service/Amenity Charge to Booking");
            System.out.println("2. View Charges for a Booking");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            choice = readInt();

            switch (choice) {
                case 1: addServiceCharge(); break;
                case 2: viewServiceCharges(); break;
                case 0: break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    static void addServiceCharge() {
        System.out.print("Enter Booking ID: ");
        int id = readInt();
        Booking booking = findBookingById(id);

        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }

        System.out.print("Enter Service Description (e.g., Food, Laundry): ");
        String desc = sc.nextLine().trim();
        System.out.print("Enter Amount: ");
        double amount = readDouble();

        booking.services.add(new ServiceCharge(desc, amount));
        System.out.println("Service charge added to booking #" + booking.bookingId);
    }

    static void viewServiceCharges() {
        System.out.print("Enter Booking ID: ");
        int id = readInt();
        Booking booking = findBookingById(id);

        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }
        if (booking.services.isEmpty()) {
            System.out.println("No service charges recorded for this booking.");
            return;
        }

        System.out.println("\nService Charges for Booking #" + booking.bookingId + ":");
        for (ServiceCharge s : booking.services) {
            System.out.printf("   - %-20s : %.2f%n", s.description, s.amount);
        }
    }

    // ================= 7. SEARCH & FILTER UTILITY =================
    static void searchFilterMenu() {
        int choice;
        do {
            System.out.println("\n------- Search & Filter Utility -------");
            System.out.println("1. Search Guest by Name");
            System.out.println("2. Search Guest by ID");
            System.out.println("3. Filter Rooms by Availability");
            System.out.println("4. Filter Rooms by Type");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            choice = readInt();

            switch (choice) {
                case 1: searchGuestByName(); break;
                case 2: searchGuestById(); break;
                case 3: filterRoomsByAvailability(); break;
                case 4: filterRoomsByType(); break;
                case 0: break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    static void searchGuestByName() {
        System.out.print("Enter name (or part of name) to search: ");
        String query = sc.nextLine().trim().toLowerCase();
        List<Guest> results = new ArrayList<>();
        for (Guest g : guests) {
            if (g.name.toLowerCase().contains(query)) results.add(g);
        }
        viewGuests(results);
    }

    static void searchGuestById() {
        System.out.print("Enter Guest ID: ");
        int id = readInt();
        Guest g = findGuestById(id);
        if (g == null) System.out.println("Guest not found.");
        else viewGuests(Collections.singletonList(g));
    }

    static void filterRoomsByAvailability() {
        System.out.println("1. Available  2. Occupied  3. Reserved");
        int c = readInt();
        String status = (c == 1) ? "Available" : (c == 2) ? "Occupied" : "Reserved";

        List<Room> results = new ArrayList<>();
        for (Room r : rooms) if (r.status.equals(status)) results.add(r);
        viewRooms(results);
    }

    static void filterRoomsByType() {
        System.out.println("1. Deluxe  2. Suite  3. Standard");
        int c = readInt();
        String type = (c == 1) ? "Deluxe" : (c == 2) ? "Suite" : "Standard";

        List<Room> results = new ArrayList<>();
        for (Room r : rooms) if (r.type.equals(type)) results.add(r);
        viewRooms(results);
    }

    // ================= Utility / Helper Methods =================
    static int readInt() {
        while (true) {
            try {
                int val = Integer.parseInt(sc.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    static double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid amount: ");
            }
        }
    }

    static LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(sc.nextLine().trim(), dtf);
            } catch (Exception e) {
                System.out.println("Invalid date format. Use yyyy-MM-dd.");
            }
        }
    }

    static void seedSampleRooms() {
        rooms.add(new Room("101", "Standard", 1500));
        rooms.add(new Room("102", "Deluxe", 2500));
        rooms.add(new Room("201", "Suite", 4000));
    }

    // ================= Model Classes =================
    static class Room {
        String roomNumber;
        String type; // Deluxe, Suite, Standard
        double pricePerNight;
        String status; // Available, Occupied, Reserved

        Room(String roomNumber, String type, double pricePerNight) {
            this.roomNumber = roomNumber;
            this.type = type;
            this.pricePerNight = pricePerNight;
            this.status = "Available";
        }
    }

    static class Guest {
        int id;
        String name;
        String contact;
        String idProof;

        Guest(int id, String name, String contact, String idProof) {
            this.id = id;
            this.name = name;
            this.contact = contact;
            this.idProof = idProof;
        }
    }

    static class Booking {
        int bookingId;
        Guest guest;
        Room room;
        LocalDate checkInDate;
        LocalDate checkOutDate;
        String status; // Booked, Checked-in, Checked-out
        List<ServiceCharge> services = new ArrayList<>();

        Booking(int bookingId, Guest guest, Room room, LocalDate checkIn, LocalDate checkOut) {
            this.bookingId = bookingId;
            this.guest = guest;
            this.room = room;
            this.checkInDate = checkIn;
            this.checkOutDate = checkOut;
            this.status = "Booked";
        }
    }

    static class ServiceCharge {
        String description;
        double amount;

        ServiceCharge(String description, double amount) {
            this.description = description;
            this.amount = amount;
        }
    }
}