import java.util.Scanner;

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

public class Mystay {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        System.out.print("Enter availability for Single Room: ");
        int singleAvailability = sc.nextInt();

        System.out.print("Enter availability for Double Room: ");
        int doubleAvailability = sc.nextInt();

        System.out.print("Enter availability for Suite Room: ");
        int suiteAvailability = sc.nextInt();

        System.out.println("\n---- Room Details ----\n");

        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailability + "\n");

        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailability + "\n");

        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailability + "\n");

        sc.close();
    }
}