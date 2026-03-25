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

class BookingHistory {
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void addBooking(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getHistory() {
        return history;
    }
}

class BookingReportService {
    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    public void displayAllBookings() {
        List<Reservation> bookings = history.getHistory();

        if (bookings.isEmpty()) {
            System.out.println("No booking history available.");
            return;
        }

        System.out.println("\n---- Booking History ----\n");

        for (Reservation r : bookings) {
            System.out.println("Reservation ID: " + r.getReservationId());
            System.out.println("Customer Name: " + r.getCustomerName());
            System.out.println("Room Type: " + r.getRoomType());
            System.out.println("-------------------------");
        }
    }

    public void generateSummaryReport() {
        List<Reservation> bookings = history.getHistory();

        Map<String, Integer> roomCount = new HashMap<>();

        for (Reservation r : bookings) {
            roomCount.put(r.getRoomType(), roomCount.getOrDefault(r.getRoomType(), 0) + 1);
        }

        System.out.println("\n---- Summary Report ----\n");

        for (Map.Entry<String, Integer> entry : roomCount.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

public class Mystay {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService(history);

        System.out.print("Enter number of confirmed bookings: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter Reservation ID: ");
            String id = sc.nextLine();

            System.out.print("Enter Customer Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Room Type: ");
            String type = sc.nextLine();

            history.addBooking(new Reservation(id, name, type));
        }

        System.out.println("\n--- Admin Report View ---");
        reportService.displayAllBookings();
        reportService.generateSummaryReport();

        sc.close();
    }
}