package se.lexicon;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

// BookingSystem class implementing the UML diagram
public class BookingSystem {
    private final BookingRepository bookingRepository;

    /**
     * Constructor that initializes the booking system with a BookingRepository.
     * @param bookingRepository The repository to use for database operations.
     */
    public BookingSystem(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    /**
     * Registers a new customer in the database.
     * @param customer The customer to register.
     * @return The number of rows affected (should be 1 for a successful insert).
     * @throws Exception If an error occurs during the operation.
     */
    public int registerCustomer(Customer customer) throws Exception {
        // Implement customer registration logic
        // This could involve inserting into the Customer table
        // For simplicity, assume it's handled by another class or repository
        return 0; // Placeholder
    }

    /**
     * Manages classroom operations (e.g., adding, updating, or deleting classrooms).
     * @param classroom The classroom to manage.
     * @return The number of rows affected.
     * @throws Exception If an error occurs during the operation.
     */
    public int manageClassroom(Classroom classroom) throws Exception {
        // Implement classroom management logic
        // This could involve inserting or updating the Classroom table
        return 0; // Placeholder
    }

    /**
     * Searches for available classrooms based on criteria.
     * @param capacity Minimum capacity required.
     * @param equipment Equipment criteria (e.g., "projector,whiteboard").
     * @param accessible Whether the classroom must be accessible.
     * @return A list of available classrooms.
     * @throws Exception If an error occurs during the operation.
     */
    public List<Classroom> searchAvailableRooms(int capacity, String equipment, boolean accessible) throws Exception {
        // Implement search logic using SQL_Commands.sql's search query
        String sql = "SELECT * FROM Classroom " +
                     "WHERE capacity >= ? " +
                     "AND equipment LIKE ? " +
                     "AND isAccessible = ?";
        List<Classroom> classrooms = new ArrayList<>();
        try (PreparedStatement stmt = bookingRepository.getDatabaseManager().getConnection().prepareStatement(sql)) {
            stmt.setInt(1, capacity);
            stmt.setString(2, "%" + equipment + "%");
            stmt.setBoolean(3, accessible);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                classrooms.add(mapResultSetToClassroom(rs));
            }
        }
        return classrooms;
    }

    /**
     * Creates a new booking after validating it.
     * @param booking The booking to create.
     * @return True if the booking was successfully created, false otherwise.
     * @throws Exception If an error occurs during the operation.
     */
    public boolean createBooking(Booking booking) throws Exception {
        // Validate booking for conflicts
        if (booking.validate()) {
            // Check for conflicts with existing bookings
            if (!bookingRepository.checkConflict(
                    booking.getClassroomId(),
                    booking.getDate(),
                    booking.getStartTime(),
                    booking.getEndTime()
            )) {
                // Save the booking
                int rowsAffected = bookingRepository.saveBooking(booking);
                return rowsAffected > 0;
            }
        }
        return false;
    }

    /**
     * Validates a booking by checking for conflicts.
     * @param booking The booking to validate.
     * @return True if the booking is valid (no conflicts), false otherwise.
     * @throws Exception If an error occurs during the operation.
     */
    public boolean validateBooking(Booking booking) throws Exception {
        return !bookingRepository.checkConflict(
                booking.getClassroomId(),
                booking.getDate(),
                booking.getStartTime(),
                booking.getEndTime()
        );
    }

    /**
     * Retrieves all bookings for a specific room.
     * @param roomId The ID of the room.
     * @return A list of bookings for the room.
     * @throws Exception If an error occurs during the operation.
     */
    public List<Booking> getRoomBookings(int roomId) throws Exception {
        return bookingRepository.findByRoom(roomId);
    }

    /**
     * Retrieves all bookings for a specific customer.
     * @param customerId The ID of the customer.
     * @return A list of bookings for the customer.
     * @throws Exception If an error occurs during the operation.
     */
    public List<Booking> getCustomerBookings(int customerId) throws Exception {
        return bookingRepository.findByCustomer(customerId);
    }

    /**
     * Retrieves all upcoming bookings (those with a date in the future).
     * @return A list of upcoming bookings.
     * @throws Exception If an error occurs during the operation.
     */
    public List<Booking> getUpcomingBookings() throws Exception {
        return bookingRepository.findUpcoming();
    }

    /**
     * Maps a ResultSet to a Classroom object.
     * @param rs The ResultSet containing classroom data.
     * @return A Classroom object populated with data from the ResultSet.
     * @throws SQLException If an error occurs during mapping.
     */
    private Classroom mapResultSetToClassroom(ResultSet rs) throws SQLException {
        int roomId = rs.getInt("room_id");
        String name = rs.getString("name");
        int capacity = rs.getInt("capacity");
        String equipment = rs.getString("equipment");
        boolean accessible = rs.getBoolean("isAccessible");

        return new Classroom(roomId, name, capacity, equipment, accessible);
    }
}
