import java.util.*;

/**
 * =============================================================
 * CLASS - InvalidBookingException
 * =============================================================
 * Custom exception used for invalid booking scenarios
 * @version 9.1
 */

class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}

/**
 * =============================================================
 * CLASS - BookingValidator
 * =============================================================
 * Validates booking inputs and system constraints
 * @version 9.1
 */

class BookingValidator {

    private Set<String> validRoomTypes;

    public BookingValidator() {

        validRoomTypes = new HashSet<>();

        validRoomTypes.add("Single Room");
        validRoomTypes.add("Double Room");
        validRoomTypes.add("Suite Room");
    }

    // Validate booking request
    public void validateBooking(String guestName, String roomType, int availableRooms)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!validRoomTypes.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        if (availableRooms <= 0) {
            throw new InvalidBookingException("No rooms available for the selected type.");
        }
    }
}

/**
 * =============================================================
 * CLASS - RoomInventory
 * =============================================================
 * Maintains room availability
 * @version 9.1
 */

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

/**
 * =============================================================
 * MAIN CLASS - UseCase9ErrorHandlingValidation
 * =============================================================
 * Demonstrates validation and error handling
 * @version 9.1
 */

public class U9 {

    public static void main(String[] args) {

        System.out.println("Error Handling & Validation Demo\n");

        RoomInventory inventory = new RoomInventory();
        BookingValidator validator = new BookingValidator();

        // Example booking inputs
        String guestName = "Alice";
        String roomType = "Suite Room";   // intentionally unavailable

        try {

            int availableRooms = inventory.getAvailability(roomType);

            validator.validateBooking(guestName, roomType, availableRooms);

            System.out.println("Booking validated successfully.");
            System.out.println("Guest: " + guestName);
            System.out.println("Room Type: " + roomType);

        } catch (InvalidBookingException e) {

            System.out.println("Booking Failed!");
            System.out.println("Reason: " + e.getMessage());
        }

        System.out.println("\nSystem continues running safely.");
    }
}