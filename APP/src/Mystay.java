import java.util.*;

class RoomInventory {
    private final Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 3);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public synchronized boolean allocate(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public synchronized void display() {
        System.out.println("\n--- Final Inventory ---");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
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

class BookingQueue {
    private final Queue<BookingRequest> queue = new LinkedList<>();

    public synchronized void addRequest(BookingRequest r) {
        queue.offer(r);
    }

    public synchronized BookingRequest getRequest() {
        return queue.poll();
    }
}

class BookingWorker extends Thread {
    private final BookingQueue queue;
    private final RoomInventory inventory;

    public BookingWorker(BookingQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            BookingRequest req;

            synchronized (queue) {
                req = queue.getRequest();
            }

            if (req == null) break;

            boolean success = inventory.allocate(req.roomType);

            if (success) {
                System.out.println("Booking Success: " + req.customerName + " -> " + req.roomType);
            } else {
                System.out.println("Booking Failed (No Availability): " + req.customerName);
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class Mystay {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        BookingQueue queue = new BookingQueue();
        RoomInventory inventory = new RoomInventory();

        System.out.print("Enter number of booking requests: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("\nEnter Customer Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Room Type: ");
            String type = sc.nextLine();

            queue.addRequest(new BookingRequest(name, type));
        }

        BookingWorker w1 = new BookingWorker(queue, inventory);
        BookingWorker w2 = new BookingWorker(queue, inventory);
        BookingWorker w3 = new BookingWorker(queue, inventory);

        System.out.println("\n--- Processing Concurrent Bookings ---\n");

        w1.start();
        w2.start();
        w3.start();

        w1.join();
        w2.join();
        w3.join();

        inventory.display();

        sc.close();
    }
}