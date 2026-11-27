package se.lexicon;

import java.util.List;

public class Customer {
    private int customerId;
    private String name;
    private CustomerType type;
    private String contactInfo;

    public Customer(int customerId, String name, CustomerType type, String contactInfo) {
        this.customerId = customerId;
        this.name = name;
        this.type = type;
        this.contactInfo = contactInfo;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public CustomerType getType() {
        return type;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void register() {
        // Implementation for registering customer
    }

    public List<Booking> getBookings() {
        // Implementation for retrieving bookings
        return null;
    }
}
