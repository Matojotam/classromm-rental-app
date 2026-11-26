package se.lexicon;

import java.sql.*;
import java.util.*;

// BookingRepository class implementing the UML diagram
public class BookingRepository {
    private final DatabaseManager dbManager;

    /**
     * Constructor that initializes the repository with a DatabaseManager instance.
     * @param dbManager The DatabaseManager to use for database operations.
     */
    public BookingRepository(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Saves a booking to the database.
     * @param booking The booking to save.
     * @return The number of rows affected (should be 1 for a successful insert).
     * @throws Exception If an error occurs during the operation.
     */
    public int saveBooking(Booking booking) throws Exception {
        String sql = "INSERT INTO Booking (customer_id, classroom_id, date, start_time, end_time, comments) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, booking.getCustomerId());
            stmt.setInt(2, booking.getClassroomId());
            stmt.setDate(3, Date.valueOf(booking.getDate()));
            stmt.setTime(4, Time.valueOf(booking.getStartTime()));
            stmt.setTime(5, Time.valueOf(booking.getEndTime()));
            stmt.setString(6, booking.getComments());
            return stmt.executeUpdate();
        }
    }

    /**
     * Finds bookings by room ID.
     * @param roomId The ID of the room to filter bookings by.
     * @return A list of bookings associated with the room.
     * @throws Exception If an error occurs during the operation.
     */
    public List<Booking> findByRoom(int roomId) throws Exception {
        String sql = "SELECT * FROM Booking WHERE classroom_id = ?";
        List<Booking> bookings = new ArrayList<>();
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        }
        return bookings;
    }

    /**
     * Finds bookings by customer ID.
     * @param customerId The ID of the customer to filter bookings by.
     * @return A list of bookings associated with the customer.
     * @throws Exception If an error occurs during the operation.
     */
    public List<Booking> findByCustomer(int customerId) throws Exception {
        String sql = "SELECT * FROM Booking WHERE customer_id = ?";
        List<Booking> bookings = new ArrayList<>();
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        }
        return bookings;
    }

    /**
     * Finds upcoming bookings (those with a date in the future).
     * @return A list of upcoming bookings.
     * @throws Exception If an error occurs during the operation.
     */
    public List<Booking> findUpcoming() throws Exception {
        String sql = "SELECT * FROM Booking WHERE date > CURRENT_DATE";
        List<Booking> bookings = new ArrayList<>();
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        }
        return bookings;
    }

    /**
     * Checks if a booking conflicts with existing bookings for the same room and date.
     * @param roomId The ID of the room.
     * @param date The date of the booking.
     * @param startTime The start time of the booking.
     * @param endTime The end time of the booking.
     * @return True if there is a conflict, false otherwise.
     * @throws Exception If an error occurs during the operation.
     */
    public boolean checkConflict(int roomId, LocalDate date, LocalTime startTime, LocalTime endTime) throws Exception {
        String sql = "SELECT * FROM Booking " +
                     "WHERE classroom_id = ? AND date = ? " +
                     "AND (start_time < ? AND end_time > ?)";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            stmt.setDate(2, Date.valueOf(date));
            stmt.setTime(3, Time.valueOf(startTime));
            stmt.setTime(4, Time.valueOf(endTime));
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if any conflicting booking exists
        }
    }

    /**
     * Maps a ResultSet to a Booking object.
     * @param rs The ResultSet containing booking data.
     * @return A Booking object populated with data from the ResultSet.
     * @throws SQLException If an error occurs during mapping.
     */
    private Booking mapResultSetToBooking(ResultSet rs) throws SQLException {
        int bookingId = rs.getInt("booking_id");
        int customerId = rs.getInt("customer_id");
        int classroomId = rs.getInt("classroom_id");
        LocalDate date = rs.getDate("date").toLocalDate();
        LocalTime startTime = rs.getTime("start_time").toLocalTime();
        LocalTime endTime = rs.getTime("end_time").toLocalTime();
        String comments = rs.getString("comments");

        return new Booking(bookingId, customerId, classroomId, date, startTime, endTime, comments);
    }
}
