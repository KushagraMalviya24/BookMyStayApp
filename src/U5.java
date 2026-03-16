import java.util.LinkedList;
import java.util.Queue;

/* ================================
   RESERVATION CLASS
   ================================ */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
    }
}

/* ================================
   BOOKING REQUEST QUEUE
   ================================ */
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Booking request added to queue.");
    }

    public void displayQueue() {
        System.out.println("\nCurrent Booking Requests in Queue:");

        for (Reservation r : queue) {
            r.displayReservation();
        }
    }
}

/* ================================
   MAIN CLASS
   ================================ */
public class U5 {

    public static void main(String[] args) {

        BookingRequestQueue requestQueue = new BookingRequestQueue();

        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        requestQueue.addRequest(r1);
        requestQueue.addRequest(r2);
        requestQueue.addRequest(r3);

        requestQueue.displayQueue();
    }
}