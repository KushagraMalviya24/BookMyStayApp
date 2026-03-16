import java.util.*;

/* ================================
   ABSTRACT ROOM CLASS
   ================================ */
abstract class Room {
    protected int beds;
    protected int size;
    protected double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
    }
}

/* ================================
   ROOM TYPES
   ================================ */
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000);
    }
}

/* ================================
   SEARCH SERVICE
   ================================ */
class SearchService {

    public static void searchRooms(RoomInventory inventory) {

        Room single = new SingleRoom();
        Room dbl = new DoubleRoom();
        Room suite = new SuiteRoom();

        System.out.println("Available Rooms\n");

        if (inventory.getAvailability("Single") > 0) {
            System.out.println("Single Room:");
            single.displayDetails();
            System.out.println("Available: " + inventory.getAvailability("Single"));
            System.out.println();
        }

        if (inventory.getAvailability("Double") > 0) {
            System.out.println("Double Room:");
            dbl.displayDetails();
            System.out.println("Available: " + inventory.getAvailability("Double"));
            System.out.println();
        }

        if (inventory.getAvailability("Suite") > 0) {
            System.out.println("Suite Room:");
            suite.displayDetails();
            System.out.println("Available: " + inventory.getAvailability("Suite"));
            System.out.println();
        }
    }
}

/* ================================
   MAIN CLASS
   ================================ */
public class U4 {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        System.out.println("Room Search System\n");

        SearchService.searchRooms(inventory);
    }
}