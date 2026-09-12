import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class HotelManagementSystem {

    // ================= In-memory Data Stores =================
    static List<Room> rooms = new ArrayList<>();
    static List<Guest> guests = new ArrayList<>();
    static List<Booking> bookings = new ArrayList<>();

    static int guestIdCounter = 1;
    static int bookingIdCounter = 1;

    static Scanner sc = new Scanner(System.in);
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Simple admin credentials
    static final String ADMIN_USERNAME = "admin";

            } else {

                attempts--;

                System.out.println(
                    "Invalid credentials. Attempts left: " + attempts
                );
            }
        }

        return false;
    }

    // ================= Admin Dashboard =================
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

                case 1:
                    roomManagementMenu();
                    break;

                case 2:
                    guestManagementMenu();
                    break;

                case 3:
                    bookingMenu();
                    break;

                case 4:
                    checkInOutMenu();
                    break;

                case 5:
                    billingMenu();
                    break;

                case 6:
                    roomServiceMenu();
                    break;

                case 7:
                    searchFilterMenu();
                    break;

                case 0:
                    System.out.println("Logging out...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
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

                case 1:
                    addRoom();
                    break;

                case 2:
                    viewRooms(rooms);
                    break;

                case 3:
                    updateRoom();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }

    // ================= Add Room =================

    static void addRoom() {

        System.out.print("Enter Room Number: ");
        String roomNumber = sc.nextLine().trim();

        if (roomNumber.isEmpty()) {
            System.out.println("Room number cannot be empty.");
            return;
        }

        if (findRoomByNumber(roomNumber) != null) {
            System.out.println("Room number already exists!");
            return;
        }

        System.out.println(
            "Select Room Type: 1. Deluxe  2. Suite  3. Standard"
        );

        int t = readInt();

        String type;

        if (t == 1) {
            type = "Deluxe";
        } else if (t == 2) {
            type = "Suite";
        } else if (t == 3) {
            type = "Standard";
        } else {
            System.out.println("Invalid room type.");
            return;
        }

        System.out.print("Enter Price Per Night: ");
        double price = readDouble();

        if (price <= 0) {
            System.out.println("Price must be greater than 0.");
            return;
        }

        Room room = new Room(roomNumber, type, price);

        rooms.add(room);

        System.out.println("Room added successfully!");
    }

    // ================= View Rooms =================

    static void viewRooms(List<Room> list) {

        if (list.isEmpty()) {
            System.out.println("No rooms found.");
            return;
        }

        System.out.println(
            "\nRoomNo | Type      | Price/Night | Status"
        );

        System.out.println(
            "---------------------------------------------"
        );

        for (Room r : list) {

            System.out.printf(
                "%-6s | %-9s | %-11.2f | %s%n",
                r.roomNumber,
                r.type,
                r.pricePerNight,
                r.status
            );
        }
    }

    // ================= Update Room =================

    static void updateRoom() {

        System.out.print("Enter Room Number to update: ");
        String roomNumber = sc.nextLine().trim();

        Room room = findRoomByNumber(roomNumber);

        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        System.out.println(
            "1. Update Type  2. Update Price  3. Update Status"
        );

        int c = readInt();

        switch (c) {

            case 1:

                System.out.println(
                    "New Type: 1. Deluxe  2. Suite  3. Standard"
                );

                int t = readInt();

                if (t == 1) {
                    room.type = "Deluxe";
                } else if (t == 2) {
                    room.type = "Suite";
                } else if (t == 3) {
                    room.type = "Standard";
                } else {
                    System.out.println("Invalid room type.");
                    return;
                }

                break;

            case 2:

                System.out.print("New Price Per Night: ");
                double newPrice = readDouble();

                if (newPrice <= 0) {
                    System.out.println("Price must be greater than 0.");
                    return;
                }

                room.pricePerNight = newPrice;

                break;

            case 3:

                System.out.println(
                    "New Status: 1. Available  2. Occupied  3. Reserved"
                );

                int s = readInt();

                if (s == 1) {
                    room.status = "Available";
                } else if (s == 2) {
                    room.status = "Occupied";
                } else if (s == 3) {
                    room.status = "Reserved";
                } else {
                    System.out.println("Invalid room status.");
                    return;
                }

                break;

            default:

                System.out.println("Invalid choice.");
                return;
        }

        System.out.println("Room updated successfully!");
    }

    // ================= Find Room =================

    static Room findRoomByNumber(String roomNumber) {

        for (Room r : rooms) {

            if (r.roomNumber.equalsIgnoreCase(roomNumber)) {
                return r;
            }
        }

        return null;
    }

    // ================= 2. GUEST REGISTRATION & PROFILES =================

    static void guestManagementMenu() {

        int choice;

        do {

            System.out.println(
                "\n------- Guest Registration & Profiles -------"
            );

            System.out.println("1. Register New Guest");
            System.out.println("2. View All Guests");
            System.out.println("0. Back");
            System.out.print("Choice: ");

            choice = readInt();

            switch (choice) {

                case 1:
                    registerGuest();
                    break;

                case 2:
                    viewGuests(guests);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }

    // ================= Register Guest =================

    static void registerGuest() {

        System.out.print("Enter Guest Name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Guest name cannot be empty.");
            return;
        }

        System.out.print("Enter Contact Number: ");
        String contact = sc.nextLine().trim();

        if (contact.isEmpty()) {
            System.out.println("Contact number cannot be empty.");
            return;
        }

        System.out.print(
            "Enter ID Proof (e.g., NID/Passport No): "
        );

        String idProof = sc.nextLine().trim();

        if (idProof.isEmpty()) {
            System.out.println("ID proof cannot be empty.");
            return;
        }

        Guest guest = new Guest(
            guestIdCounter++,
            name,
            contact,
            idProof
        );

        guests.add(guest);

        System.out.println(
            "Guest registered successfully! Guest ID: "
            + guest.id
        );
    }

    // ================= View Guests =================

    static void viewGuests(List<Guest> list) {

        if (list.isEmpty()) {
            System.out.println("No guests found.");
            return;
        }

        System.out.println(
            "\nID  | Name            | Contact       | ID Proof"
        );

        System.out.println(
            "---------------------------------------------------"
        );

        for (Guest g : list) {

            System.out.printf(
                "%-3d | %-15s | %-13s | %s%n",
                g.id,
                g.name,
                g.contact,
                g.idProof
            );
        }
    }

    // ================= Find Guest =================

    static Guest findGuestById(int id) {

        for (Guest g : guests) {

            if (g.id == id) {
                return g;
            }
        }

        return null;
    }

    // ================= 3. ROOM BOOKING & RESERVATION =================

    static void bookingMenu() {

        int choice;

        do {

            System.out.println(
                "\n------- Room Booking & Reservation -------"
            );

            System.out.println("1. Check Room Availability");
            System.out.println("2. Book a Room");
            System.out.println("3. View All Bookings");
            System.out.println("0. Back");
            System.out.print("Choice: ");

            choice = readInt();

            switch (choice) {

                case 1:
                    checkAvailability();
                    break;

                case 2:
                    bookRoom();
                    break;

                case 3:
                    viewBookings(bookings);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }

    // ================= Check Availability =================

    static void checkAvailability() {

        List<Room> available = new ArrayList<>();

        for (Room r : rooms) {

            if (r.status.equals("Available")) {
                available.add(r);
            }
        }

        System.out.println("\nAvailable Rooms:");

        viewRooms(available);
    }

    // ================= Book Room =================

    static void bookRoom() {

        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }

        System.out.print(
            "Enter Guest ID (or 0 to register new guest): "
        );

        int gid = readInt();

        Guest guest;

        if (gid == 0) {

            registerGuest();

            if (guests.isEmpty()) {
                System.out.println("Guest registration failed.");
                return;
            }

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
            System.out.println(
                "Room is not available for booking."
            );
            return;
        }

        LocalDate checkIn = readDate(
            "Enter Check-in Date (yyyy-MM-dd): "
        );

        LocalDate checkOut = readDate(
            "Enter Check-out Date (yyyy-MM-dd): "
        );

        if (!checkOut.isAfter(checkIn)) {

            System.out.println(
                "Check-out date must be after check-in date."
            );

            return;
        }

        Booking booking = new Booking(
            bookingIdCounter++,
            guest,
            room,
            checkIn,
            checkOut
        );

        bookings.add(booking);

        room.status = "Reserved";

        System.out.println(
            "Booking successful! Booking ID: "
            + booking.bookingId
        );
    }

    // ================= View Bookings =================

    static void viewBookings(List<Booking> list) {

        if (list.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println(
            "\nBookID | Guest           | Room | CheckIn    | CheckOut   | Status"
        );

        System.out.println(
            "---------------------------------------------------------------------"
        );

        for (Booking b : list) {

            System.out.printf(
                "%-6d | %-15s | %-4s | %-10s | %-10s | %s%n",
                b.bookingId,
                b.guest.name,
                b.room.roomNumber,
                b.checkInDate.format(dtf),
                b.checkOutDate.format(dtf),
                b.status
            );
        }
    }

    // ================= Find Booking =================

    static Booking findBookingById(int id) {

        for (Booking b : bookings) {

            if (b.bookingId == id) {
                return b;
            }
        }

        return null;
    }

    // ================= 4. CHECK-IN / CHECK-OUT SYSTEM =================

    static void checkInOutMenu() {

        int choice;

        do {

            System.out.println(
                "\n------- Check-in / Check-out System -------"
            );

            System.out.println("1. Check-in Guest");
            System.out.println("2. Check-out Guest");
            System.out.println("3. View Active Stays");
            System.out.println("0. Back");
            System.out.print("Choice: ");

            choice = readInt();

            switch (choice) {

                case 1:
                    checkInGuest();
                    break;

                case 2:
                    checkOutGuest();
                    break;

                case 3:
                    viewActiveStays();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }

    // ================= Check-in Guest =================

    static void checkInGuest() {

        System.out.print(
            "Enter Booking ID to check-in: "
        );

        int id = readInt();

        Booking booking = findBookingById(id);

        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }

        if (!booking.status.equals("Booked")) {

            System.out.println(
                "This booking cannot be checked in "
                + "(current status: "
                + booking.status
                + ")."
            );

            return;
        }

        booking.status = "Checked-in";
        booking.room.status = "Occupied";

        System.out.println(
            "Guest checked in successfully. Room "
            + booking.room.roomNumber
            + " is now Occupied."
        );
    }

    // ================= Check-out Guest =================

    static void checkOutGuest() {

        System.out.print(
            "Enter Booking ID to check-out: "
        );

        int id = readInt();

        Booking booking = findBookingById(id);

        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }

        if (!booking.status.equals("Checked-in")) {

            System.out.println(
                "This booking cannot be checked out "
                + "(current status: "
                + booking.status
                + ")."
            );

            return;
        }

        booking.status = "Checked-out";
        booking.room.status = "Available";

        System.out.println(
            "Guest checked out successfully. Room "
            + booking.room.roomNumber
            + " is now Available."
        );

        generateInvoice(booking);
    }

    // ================= View Active Stays =================

    static void viewActiveStays() {

        List<Booking> active = new ArrayList<>();

        for (Booking b : bookings) {

            if (b.status.equals("Checked-in")) {
                active.add(b);
            }
        }

        System.out.println("\nActive Stays:");

        viewBookings(active);
    }

    // ================= 5. BILLING & INVOICE GENERATION =================

    static void billingMenu() {

        int choice;

        do {

            System.out.println(
                "\n------- Billing & Invoice Generation -------"
            );

            System.out.println(
                "1. Generate Invoice for a Booking"
            );

            System.out.println("0. Back");
            System.out.print("Choice: ");

            choice = readInt();

            switch (choice) {

                case 1:

                    System.out.print("Enter Booking ID: ");

                    int id = readInt();

                    Booking b = findBookingById(id);

                    if (b == null) {
                        System.out.println("Booking not found.");
                    } else {
                        generateInvoice(b);
                    }

                    break;

                case 0:
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }

    // ================= Generate Invoice =================

    static void generateInvoice(Booking booking) {

        long days = ChronoUnit.DAYS.between(
            booking.checkInDate,
            booking.checkOutDate
        );

        if (days <= 0) {
            days = 1;
        }

        double roomCost =
            days * booking.room.pricePerNight;

        double serviceCost = 0.0;

        for (ServiceCharge sc : booking.services) {
            serviceCost += sc.amount;
        }

        double total = roomCost + serviceCost;

        System.out.println(
            "\n============ INVOICE ============"
        );

        System.out.println(
            "Booking ID   : " + booking.bookingId
        );

        System.out.println(
            "Guest Name   : " + booking.guest.name
        );

        System.out.println(
            "Room Number  : "
            + booking.room.roomNumber
            + " ("
            + booking.room.type
            + ")"
        );

        System.out.println(
            "Check-in     : "
            + booking.checkInDate.format(dtf)
        );

        System.out.println(
            "Check-out    : "
            + booking.checkOutDate.format(dtf)
        );

        System.out.println(
            "No. of Days  : " + days
        );

        System.out.printf(
            "Room Charges : %.2f%n",
            roomCost
        );

        if (!booking.services.isEmpty()) {

            System.out.println("Service Charges:");

            for (ServiceCharge s : booking.services) {

                System.out.printf(
                    "   - %-20s : %.2f%n",
                    s.description,
                    s.amount
                );
            }
        }

        System.out.println(
            "----------------------------------"
        );

        System.out.printf(
            "TOTAL AMOUNT : %.2f%n",
            total
        );

        System.out.println(
            "=================================="
        );
    }

    // ================= 6. ROOM SERVICE & AMENITIES TRACKER =================

    static void roomServiceMenu() {

        int choice;

        do {

            System.out.println(
                "\n------- Room Service & Amenities Tracker -------"
            );

            System.out.println(
                "1. Add Service/Amenity Charge to Booking"
            );

            System.out.println(
                "2. View Charges for a Booking"
            );

            System.out.println("0. Back");
            System.out.print("Choice: ");

            choice = readInt();

            switch (choice) {

                case 1:
                    addServiceCharge();
                    break;

                case 2:
                    viewServiceCharges();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }

    // ================= Add Service Charge =================

    static void addServiceCharge() {

        System.out.print("Enter Booking ID: ");

        int id = readInt();

        Booking booking = findBookingById(id);

        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }

        System.out.print(
            "Enter Service Description (e.g., Food, Laundry): "
        );

        String desc = sc.nextLine().trim();

        if (desc.isEmpty()) {
            System.out.println(
                "Service description cannot be empty."
            );
            return;
        }

        System.out.print("Enter Amount: ");

        double amount = readDouble();

        if (amount <= 0) {
            System.out.println(
                "Amount must be greater than 0."
            );
            return;
        }

        booking.services.add(
            new ServiceCharge(desc, amount)
        );

        System.out.println(
            "Service charge added to booking #"
            + booking.bookingId
        );
    }

    // ================= View Service Charges =================

    static void viewServiceCharges() {

        System.out.print("Enter Booking ID: ");

        int id = readInt();

        Booking booking = findBookingById(id);

        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }

        if (booking.services.isEmpty()) {

            System.out.println(
                "No service charges recorded for this booking."
            );

            return;
        }

        System.out.println(
            "\nService Charges for Booking #"
            + booking.bookingId
            + ":"
        );

        for (ServiceCharge s : booking.services) {

            System.out.printf(
                "   - %-20s : %.2f%n",
                s.description,
                s.amount
            );
        }
    }

    // ================= 7. SEARCH & FILTER UTILITY =================

    static void searchFilterMenu() {

        int choice;

        do {

            System.out.println(
                "\n------- Search & Filter Utility -------"
            );

            System.out.println("1. Search Guest by Name");
            System.out.println("2. Search Guest by ID");
            System.out.println("3. Filter Rooms by Availability");
            System.out.println("4. Filter Rooms by Type");
            System.out.println("0. Back");
            System.out.print("Choice: ");

            choice = readInt();

            switch (choice) {

                case 1:
                    searchGuestByName();
                    break;

                case 2:
                    searchGuestById();
                    break;

                case 3:
                    filterRoomsByAvailability();
                    break;

                case 4:
                    filterRoomsByType();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }

    // ================= Search Guest By Name =================

    static void searchGuestByName() {

        System.out.print(
            "Enter name (or part of name) to search: "
        );

        String query = sc.nextLine()
                         .trim()
                         .toLowerCase();

        if (query.isEmpty()) {
            System.out.println("Search text cannot be empty.");
            return;
        }

        List<Guest> results = new ArrayList<>();

        for (Guest g : guests) {

            if (g.name.toLowerCase().contains(query)) {
                results.add(g);
            }
        }

        viewGuests(results);
    }

    // ================= Search Guest By ID =================

    static void searchGuestById() {

        System.out.print("Enter Guest ID: ");

        int id = readInt();

        Guest g = findGuestById(id);

        if (g == null) {

            System.out.println("Guest not found.");

        } else {

            viewGuests(
                Collections.singletonList(g)
            );
        }
    }

    // ================= Filter Rooms By Availability =================

    static void filterRoomsByAvailability() {

        System.out.println(
            "1. Available  2. Occupied  3. Reserved"
        );

        int c = readInt();

        String status;

        if (c == 1) {

            status = "Available";

        } else if (c == 2) {

            status = "Occupied";

        } else if (c == 3) {

            status = "Reserved";

        } else {

            System.out.println("Invalid status choice.");
            return;
        }

        List<Room> results = new ArrayList<>();

        for (Room r : rooms) {

            if (r.status.equals(status)) {
                results.add(r);
            }
        }

        viewRooms(results);
    }

    // ================= Filter Rooms By Type =================

    static void filterRoomsByType() {

        System.out.println(
            "1. Deluxe  2. Suite  3. Standard"
        );

        int c = readInt();

        String type;

        if (c == 1) {

            type = "Deluxe";

        } else if (c == 2) {

            type = "Suite";

        } else if (c == 3) {

            type = "Standard";

        } else {

            System.out.println("Invalid room type choice.");
            return;
        }

        List<Room> results = new ArrayList<>();

        for (Room r : rooms) {

            if (r.type.equals(type)) {
                results.add(r);
            }
        }

        viewRooms(results);
    }

    // ================= Utility / Helper Methods =================

    static int readInt() {

        while (true) {

            try {

                int val = Integer.parseInt(
                    sc.nextLine().trim()
                );

                return val;

            } catch (NumberFormatException e) {

                System.out.print(
                    "Invalid input. Please enter a number: "
                );
            }
        }
    }

    // ================= Read Double =================

    static double readDouble() {

        while (true) {

            try {

                return Double.parseDouble(
                    sc.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.print(
                    "Invalid input. Please enter a valid amount: "
                );
            }
        }
    }

    // ================= Read Date =================

    static LocalDate readDate(String prompt) {

        while (true) {

            System.out.print(prompt);

            try {

                return LocalDate.parse(
                    sc.nextLine().trim(),
                    dtf
                );

            } catch (Exception e) {

                System.out.println(
                    "Invalid date format. Use yyyy-MM-dd."
                );
            }
        }
    }

    // ================= Sample Rooms =================

    static void seedSampleRooms() {

        rooms.add(
            new Room("101", "Standard", 1500)
        );

        rooms.add(
            new Room("102", "Deluxe", 2500)
        );

        rooms.add(
            new Room("201", "Suite", 4000)
        );
    }

    // ================= Model Classes =================

    static class Room {

        String roomNumber;
        String type;
        double pricePerNight;
        String status;

        Room(
            String roomNumber,
            String type,
            double pricePerNight
        ) {

            this.roomNumber = roomNumber;
            this.type = type;
            this.pricePerNight = pricePerNight;
            this.status = "Available";
        }
    }

    // ================= Guest Class =================

    static class Guest {

        int id;
        String name;
        String contact;
        String idProof;

        Guest(
            int id,
            String name,
            String contact,
            String idProof
        ) {

            this.id = id;
            this.name = name;
            this.contact = contact;
            this.idProof = idProof;
        }
    }

    // ================= Booking Class =================

    static class Booking {

        int bookingId;
        Guest guest;
        Room room;
        LocalDate checkInDate;
        LocalDate checkOutDate;
        String status;

        List<ServiceCharge> services =
            new ArrayList<>();

        Booking(
            int bookingId,
            Guest guest,
            Room room,
            LocalDate checkIn,
            LocalDate checkOut
        ) {

            this.bookingId = bookingId;
            this.guest = guest;
            this.room = room;
            this.checkInDate = checkIn;
            this.checkOutDate = checkOut;
            this.status = "Booked";
        }
    }

    // ================= Service Charge Class =================

    static class ServiceCharge {

        String description;
        double amount;

        ServiceCharge(
            String description,
            double amount
        ) {

            this.description = description;
            this.amount = amount;
        }
    }
}