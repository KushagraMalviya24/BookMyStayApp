import java.util.*;

/**
 * =============================================================
 * CLASS - Reservation
 * =============================================================
 * Represents a guest booking request
 * @version 6.1
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
 * Maintains room availability
 * @version 6.1
 */

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decreaseAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

/**
 * =============================================================
 * CLASS - RoomAllocationService
 * =============================================================
 * Processes reservations and allocates rooms safely
 * @version 6.1
 */

class RoomAllocationService {

    private RoomInventory inventory;

    // Track allocated rooms by type
    private HashMap<String, Set<String>> allocatedRooms;

    // Track all used room IDs
    private Set<String> usedRoomIds;

    public RoomAllocationService(RoomInventory inventory) {

        this.inventory = inventory;

        allocatedRooms = new HashMap<>();
        usedRoomIds = new HashSet<>();
    }

    public void processReservations(Queue<Reservation> queue) {

        while (!queue.isEmpty()) {

            Reservation request = queue.poll();

            String roomType = request.getRoomType();

            if (inventory.getAvailability(roomType) > 0) {

                String roomId = generateRoomId(roomType);

                usedRoomIds.add(roomId);

                allocatedRooms
                        .computeIfAbsent(roomType, k -> new HashSet<>())
                        .add(roomId);

                inventory.decreaseAvailability(roomType);

                System.out.println("Reservation Confirmed");
                System.out.println("Guest: " + request.getGuestName());
                System.out.println("Room Type: " + roomType);
                System.out.println("Room ID: " + roomId);
                System.out.println("---------------------------");

            } else {

                System.out.println("Reservation Failed for "
                        + request.getGuestName()
                        + " (No rooms available)");
            }
        }
    }

    private String generateRoomId(String roomType) {

        String prefix = roomType.replace(" ", "").substring(0, 2).toUpperCase();

        String roomId;

        do {
            roomId = prefix + new Random().nextInt(100);
        } while (usedRoomIds.contains(roomId));

        return roomId;
    }
}

/**
 * =============================================================
 * MAIN CLASS - UseCase6RoomAllocationService
 * =============================================================
 * Demonstrates reservation confirmation and room allocation
 * @version 6.1
 */

public class U6 {

    public static void main(String[] args) {

        System.out.println("Reservation Confirmation & Room Allocation\n");

        RoomInventory inventory = new RoomInventory();

        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Single Room"));
        bookingQueue.add(new Reservation("Charlie", "Single Room"));
        bookingQueue.add(new Reservation("David", "Suite Room"));

        RoomAllocationService allocationService =
                new RoomAllocationService(inventory);

        allocationService.processReservations(bookingQueue);
    }
}