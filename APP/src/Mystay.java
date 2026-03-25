import java.io.*;
import java.util.*;

class RoomInventory implements Serializable {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 3);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decrement(String type) {
        inventory.put(type, getAvailability(type) - 1);
    }

    public void increment(String type) {
        inventory.put(type, getAvailability(type) + 1);
    }

    public void display() {
        System.out.println("\n--- Inventory ---");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

class Reservation implements Serializable {
    String id;
    String name;
    String roomType;

    public Reservation(String id, String name, String roomType) {
        this.id = id;
        this.name = name;
        this.roomType = roomType;
    }
}

class PersistenceService {

    private static final String FILE_NAME = "hotel_state.dat";

    public static void save(RoomInventory inventory, List<Reservation> reservations) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(inventory);
            out.writeObject(reservations);
            System.out.println("System state saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static Object[] load() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No saved state found. Starting fresh system.");
            return null;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            RoomInventory inventory = (RoomInventory) in.readObject();
            List<Reservation> reservations = (List<Reservation>) in.readObject();
            System.out.println("System state restored successfully.");
            return new Object[]{inventory, reservations};
        } catch (Exception e) {
            System.out.println("Corrupted state detected. Starting fresh system.");
            return null;
        }
    }
}

public class Mystay {

    public static void main(String[] args) {

        Object[] state = PersistenceService.load();

        RoomInventory inventory;
        List<Reservation> reservations;

        if (state == null) {
            inventory = new RoomInventory();
            reservations = new ArrayList<>();
        } else {
            inventory = (RoomInventory) state[0];
            reservations = (List<Reservation>) state[1];
        }

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of bookings: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("\nEnter Reservation ID: ");
            String id = sc.nextLine();

            System.out.print("Enter Customer Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Room Type: ");
            String type = sc.nextLine();

            if (inventory.getAvailability(type) > 0) {
                inventory.decrement(type);
                reservations.add(new Reservation(id, name, type));
                System.out.println("Booking Confirmed");
            } else {
                System.out.println("Booking Failed (No Availability)");
            }
        }

        inventory.display();

        PersistenceService.save(inventory, reservations);

        sc.close();
    }
}