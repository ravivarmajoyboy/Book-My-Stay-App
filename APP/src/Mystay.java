import java.util.*;

class Reservation {
    private String reservationId;
    private String customerName;
    private String roomType;

    public Reservation(String reservationId, String customerName, String roomType) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRoomType() {
        return roomType;
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
}

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void increment(String type) {
        inventory.put(type, getAvailability(type) + 1);
    }
}

class CancellationService {
    private Map<String, Reservation> reservationMap;
    private Stack<String> rollbackStack;
    private RoomInventory inventory;

    public CancellationService(RoomInventory inventory) {
        this.inventory = inventory;
        this.reservationMap = new HashMap<>();
        this.rollbackStack = new Stack<>();
    }

    public void addReservation(Reservation r) {
        reservationMap.put(r.getReservationId(), r);
    }

    public void cancelReservation(String reservationId) {

        if (!reservationMap.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Reservation not found");
            return;
        }

        Reservation r = reservationMap.get(reservationId);

        rollbackStack.push(r.getReservationId());

        inventory.increment(r.getRoomType());

        reservationMap.remove(reservationId);

        System.out.println("Cancellation Successful");
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Room Released: " + r.getRoomType());
    }

    public void showRollbackStack() {
        System.out.println("\n--- Rollback Stack (LIFO) ---");
        if (rollbackStack.isEmpty()) {
            System.out.println("No rollbacks recorded");
            return;
        }

        for (String id : rollbackStack) {
            System.out.println(id);
        }
    }

    public void showInventory() {
        System.out.println("\n--- Inventory State ---");
        System.out.println("Single Room: " + inventory.getAvailability("Single Room"));
        System.out.println("Double Room: " + inventory.getAvailability("Double Room"));
        System.out.println("Suite Room: " + inventory.getAvailability("Suite Room"));
    }
}

public class Mystay {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        CancellationService service = new CancellationService(inventory);

        System.out.print("Enter number of confirmed bookings to add: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("\nEnter Reservation ID: ");
            String id = sc.nextLine();

            System.out.print("Enter Customer Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Room Type: ");
            String type = sc.nextLine();

            service.addReservation(new Reservation(id, name, type));
        }

        System.out.print("\nEnter Reservation ID to cancel: ");
        String cancelId = sc.nextLine();

        service.cancelReservation(cancelId);

        service.showRollbackStack();
        service.showInventory();

        System.out.println("---- Available Rooms ----\n");
        searchService.searchAvailableRooms();
    }
}