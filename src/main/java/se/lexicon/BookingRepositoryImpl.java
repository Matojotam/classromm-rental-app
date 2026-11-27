package se.lexicon;

import java.sql.Connection;
import java.sql.SQLException;

public class BookingRepository {
    private DatabaseManager dbManager;

    public BookingRepository(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public void saveBooking(Booking booking) throws SQLException {
        // Implementation for saving booking
    }

    public List<Booking> findByRoom(int roomId) {
        // Implementation for finding bookings by room
        return null;
    }

    public List<Booking> findByCustomer(int customerId) {
        // Implementation for finding bookings by customer
        return null;
    }

    public List<Booking> findUpcoming() {
        // Implementation for finding upcoming bookings
        return null;
    }

    public boolean checkConflict(Booking booking) {
        // Implementation for checking conflicts
        return false;
    }
}
