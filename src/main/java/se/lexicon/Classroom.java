package se.lexicon;

import java.util.List;

public class Classroom {
    private int roomId;
    private String name;
    private int capacity;
    private List<String> equipment;
    private boolean accessible;

    public Classroom(int roomId, String name, int capacity, List<String> equipment, boolean accessible) {
        this.roomId = roomId;
        this.name = name;
        this.capacity = capacity;
        this.equipment = equipment;
        this.accessible = accessible;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<String> getEquipment() {
        return equipment;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public boolean isAvailable() {
        // Implementation for checking availability
        return true;
    }

    public boolean matchesCriteria() {
        // Implementation for matching criteria
        return true;
    }
}
