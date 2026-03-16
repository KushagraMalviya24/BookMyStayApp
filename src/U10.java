import java.util.*;

/**
 * =============================================================
 * CLASS - Reservation
 * =============================================================
 * Represents a confirmed booking
 * @version 10.1
 */

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void displayReservation() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType + " | RoomID: " + roomId);
    }
}

/**
 * =============================================================
 * CLASS - RoomInventory
 * =============================================================
 * Maintains room availability
 * @version 10.1
 */

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void increaseAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " : " + inventory.get(roomType));
        }
    }
}

/**
 * =============================================================
 * CLASS - CancellationService
 * =============================================================
 * Handles booking cancellation and rollback
 * @version 10.1
 */

class CancellationService {

    private Map<String, Reservation> confirmedBookings;

    private Stack<String> rollbackStack;

    private RoomInventory inventory;

    public CancellationService(RoomInventory inventory) {

        this.inventory = inventory;

        confirmedBookings = new HashMap<>();

        rollbackStack = new Stack<>();
    }

    // Add confirmed booking (simulated)
    public void addBooking(Reservation reservation) {
        confirmedBookings.put(reservation.getReservationId(), reservation);
    }

    // Cancel reservation
    public void cancelReservation(String reservationId) {

        if (!confirmedBookings.containsKey(reservationId)) {

            System.out.println("Cancellation Failed: Reservation not found.");
            return;
        }

        Reservation reservation = confirmedBookings.remove(reservationId);

        String roomId = reservation.getRoomId();

        rollbackStack.push(roomId);

        inventory.increaseAvailability(reservation.getRoomType());

        System.out.println("\nReservation Cancelled Successfully");
        System.out.println("Released Room ID: " + roomId);
    }

    // Show rollback history
    public void displayRollbackHistory() {

        System.out.println("\nRollback Stack (Recently Released Rooms)");

        for (String roomId : rollbackStack) {
            System.out.println(roomId);
        }
    }
}

/**
 * =============================================================
 * MAIN CLASS - UseCase10BookingCancellation
 * =============================================================
 * Demonstrates cancellation and inventory rollback
 * @version 10.1
 */

public class U10 {

    public static void main(String[] args) {

        System.out.println("Booking Cancellation & Inventory Rollback\n");

        RoomInventory inventory = new RoomInventory();

        CancellationService cancellationService =
                new CancellationService(inventory);

        // Simulated confirmed bookings
        Reservation r1 =
                new Reservation("RES101", "Alice", "Single Room", "SR101");

        Reservation r2 =
                new Reservation("RES102", "Bob", "Double Room", "DR201");

        cancellationService.addBooking(r1);
        cancellationService.addBooking(r2);

        System.out.println("Confirmed Bookings:");
        r1.displayReservation();
        r2.displayReservation();

        // Cancel a booking
        cancellationService.cancelReservation("RES101");

        // Show updated inventory
        inventory.displayInventory();

        // Show rollback history
        cancellationService.displayRollbackHistory();
    }
}