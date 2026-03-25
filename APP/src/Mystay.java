import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decrement(String type) {
        inventory.put(type, getAvailability(type) - 1);
    }

    public boolean isValidRoomType(String type) {
        return inventory.containsKey(type);
    }
}

class InvalidBookingValidator {

    public void validate(String name, String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        if (name == null || name.trim().isEmpty()) {
            throw new InvalidBookingException("Customer name cannot be empty");
        }

        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("No availability for: " + roomType);
        }
    }
}

public class Mystay {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        InvalidBookingValidator validator = new InvalidBookingValidator();

        System.out.print("Enter number of booking attempts: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("\nEnter Customer Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Room Type: ");
            String type = sc.nextLine();

            try {
                validator.validate(name, type, inventory);

                inventory.decrement(type);
                System.out.println("Booking Successful for " + name + " (" + type + ")");

            } catch (InvalidBookingException e) {
                System.out.println("Booking Failed: " + e.getMessage());
            }
        }

        System.out.println("\n--- Final Inventory State ---");
        System.out.println("Single Room: " + inventory.getAvailability("Single Room"));
        System.out.println("Double Room: " + inventory.getAvailability("Double Room"));
        System.out.println("Suite Room: " + inventory.getAvailability("Suite Room"));

        sc.close();
    }
}