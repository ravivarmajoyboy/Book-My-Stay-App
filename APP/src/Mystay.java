import java.util.*;

abstract class Room {
    private String type;
    private int beds;
    private double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    public abstract void displayRoomDetails();
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1000.0);
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + getType());
        System.out.println("Beds: " + getBeds());
        System.out.println("Price: " + getPrice());
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 1800.0);
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + getType());
        System.out.println("Beds: " + getBeds());
        System.out.println("Price: " + getPrice());
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 3000.0);
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + getType());
        System.out.println("Beds: " + getBeds());
        System.out.println("Price: " + getPrice());
    }
}

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Set<String> getRoomTypes() {
        return inventory.keySet();
    }
}

class RoomSearchService {
    private Map<String, Room> rooms;
    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
        rooms = new HashMap<>();
        rooms.put("Single Room", new SingleRoom());
        rooms.put("Double Room", new DoubleRoom());
        rooms.put("Suite Room", new SuiteRoom());
    }

    public void searchAvailableRooms() {
        for (String type : inventory.getRoomTypes()) {
            int available = inventory.getAvailability(type);
            if (available > 0) {
                Room room = rooms.get(type);
                room.displayRoomDetails();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

public class Mystay {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        RoomSearchService searchService = new RoomSearchService(inventory);

        System.out.println("Welcome to Hotel Booking System");
        System.out.println("Version: 4.0\n");

        System.out.println("---- Available Rooms ----\n");
        searchService.searchAvailableRooms();
    }
}