package se.lexicon;

import java.util.Scanner;

public class ConsoleUI {
    private BookingSystem bookingSystem;

    public ConsoleUI(BookingSystem bookingSystem) {
        this.bookingSystem = bookingSystem;
    }

    public void displayMenu() {
        System.out.println("1. Register Customer");
        System.out.println("2. Manage Classroom");
        System.out.println("3. Search Rooms");
        System.out.println("4. Create Booking");
        System.out.println("5. View Bookings");
        System.out.println("6. Exit");
    }

    public void registerCustomer() {
        // Implementation for registering customer
    }

    public void manageClassroom() {
        // Implementation for managing classroom
    }

    public void searchRooms() {
        // Implementation for searching rooms
    }

    public void createBooking() {
        // Implementation for creating booking
    }

    public void viewBookings() {
        // Implementation for viewing bookings
    }
}
