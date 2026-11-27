package se.lexicon;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {
    private int bookingId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String comments;

    public Booking(int bookingId, LocalDate date, LocalTime startTime, LocalTime endTime, String comments) {
        this.bookingId = bookingId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.comments = comments;
    }

    public int getBookingId() {
        return bookingId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getComments() {
        return comments;
    }

    public boolean validate() {
        // Implementation for validation
        return true;
    }

    public boolean hasConflict() {
        // Implementation for conflict checking
        return false;
    }
}
