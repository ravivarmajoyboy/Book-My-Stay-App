import java.util.*;

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
}

class BookingRequest {
    String customerName;
    String roomType;

    public BookingRequest(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

class RoomAllocationService {
    private RoomInventory inventory;
    private Queue<BookingRequest> queue;
    private Map<String, Set<String>> allocatedRooms;
    private int counter = 1;

    public RoomAllocationService(RoomInventory inventory) {
        this.inventory = inventory;
        this.queue = new LinkedList<>();
        this.allocatedRooms = new HashMap<>();
    }

    public void addRequest(BookingRequest request) {
        queue.add(request);
    }

    private String generateRoomId(String type) {
        return type.substring(0, 2).toUpperCase() + counter++;
    }

    public void processBookings() {
        while (!queue.isEmpty()) {
            BookingRequest req = queue.poll();

            if (inventory.getAvailability(req.roomType) > 0) {
                String roomId = generateRoomId(req.roomType);

                allocatedRooms.putIfAbsent(req.roomType, new HashSet<>());
                allocatedRooms.get(req.roomType).add(roomId);

                inventory.decrement(req.roomType);

                System.out.println("Booking Confirmed for " + req.customerName);
                System.out.println("Room Type: " + req.roomType);
                System.out.println("Room ID: " + roomId + "\n");
            } else {
                System.out.println("Booking Failed for " + req.customerName + " (No availability)\n");
            }
        }
    }
}

public class Mystay {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService(inventory);

        System.out.print("Enter number of booking requests: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter customer name: ");
            String name = sc.nextLine();

            System.out.print("Enter room type (Single Room/Double Room/Suite Room): ");
            String type = sc.nextLine();

            service.addRequest(new BookingRequest(name, type));
        }

        System.out.println("\n--- Processing Bookings ---\n");
        service.processBookings();

        sc.close();
    }
}