import java.util.*;

/**
 * =============================================================
 * CLASS - Reservation
 * =============================================================
 * Represents a booking request
 * @version 11.1
 */

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * =============================================================
 * CLASS - RoomInventory
 * =============================================================
 * Shared inventory accessed by multiple threads
 * @version 11.1
 */

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    // Thread-safe room allocation
    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {

            inventory.put(roomType, available - 1);

            return true;
        }

        return false;
    }

    public void displayInventory() {

        System.out.println("\nRemaining Inventory:");

        for (String roomType : inventory.keySet()) {

            System.out.println(roomType + " : " + inventory.get(roomType));
        }
    }
}

/**
 * =============================================================
 * CLASS - ConcurrentBookingProcessor
 * =============================================================
 * Processes bookings using multiple threads
 * @version 11.1
 */

class ConcurrentBookingProcessor implements Runnable {

    private Queue<Reservation> bookingQueue;

    private RoomInventory inventory;

    public ConcurrentBookingProcessor(Queue<Reservation> bookingQueue,
                                      RoomInventory inventory) {

        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            Reservation reservation;

            synchronized (bookingQueue) {

                if (bookingQueue.isEmpty()) {
                    return;
                }

                reservation = bookingQueue.poll();
            }

            boolean allocated =
                    inventory.allocateRoom(reservation.getRoomType());

            if (allocated) {

                System.out.println(Thread.currentThread().getName()
                        + " booked room for "
                        + reservation.getGuestName()
                        + " (" + reservation.getRoomType() + ")");

            } else {

                System.out.println(Thread.currentThread().getName()
                        + " booking failed for "
                        + reservation.getGuestName()
                        + " (No rooms available)");
            }
        }
    }
}

/**
 * =============================================================
 * MAIN CLASS - UseCase11ConcurrentBookingSimulation
 * =============================================================
 * Demonstrates concurrent booking requests
 * @version 11.1
 */

public class U11 {

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation\n");

        Queue<Reservation> bookingQueue = new LinkedList<>();

        // Simulated guest booking requests
        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Single Room"));
        bookingQueue.add(new Reservation("Charlie", "Double Room"));
        bookingQueue.add(new Reservation("David", "Suite Room"));
        bookingQueue.add(new Reservation("Eva", "Single Room"));

        RoomInventory inventory = new RoomInventory();

        // Create multiple booking threads
        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory),
                "Processor-1"
        );

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory),
                "Processor-2"
        );

        Thread t3 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory),
                "Processor-3"
        );

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.displayInventory();
    }
}