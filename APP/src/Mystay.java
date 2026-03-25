import java.util.*;

class Reservation {
    private String customerName;
    private String roomType;

    public Reservation(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public void displayQueue() {
        for (Reservation r : queue) {
            System.out.println("Customer: " + r.getCustomerName() + ", Room Type: " + r.getRoomType());
        }
    }
}

public class Mystay {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        System.out.print("Enter number of booking requests: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter customer name: ");
            String name = sc.nextLine();

            System.out.print("Enter room type (Single Room/Double Room/Suite Room): ");
            String type = sc.nextLine();

            bookingQueue.addRequest(new Reservation(name, type));
        }

        System.out.println("\n--- Booking Request Queue (FIFO Order) ---\n");
        bookingQueue.displayQueue();

        sc.close();
    }
}