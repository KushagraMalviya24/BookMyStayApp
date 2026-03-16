import java.io.*;
import java.util.*;

/**
 * =============================================================
 * CLASS - SystemState
 * =============================================================
 * Represents system state for persistence
 * @version 12.1
 */

class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, Integer> inventory;
    private List<String> bookingHistory;

    public SystemState(Map<String, Integer> inventory,
                       List<String> bookingHistory) {

        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public List<String> getBookingHistory() {
        return bookingHistory;
    }
}

/**
 * =============================================================
 * CLASS - PersistenceService
 * =============================================================
 * Handles saving and loading system state
 * @version 12.1
 */

class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save system state
    public void saveState(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);

            System.out.println("System state saved successfully.");

        } catch (IOException e) {

            System.out.println("Error saving system state.");
        }
    }

    // Load system state
    public SystemState loadState() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) in.readObject();

            System.out.println("System state restored successfully.");

            return state;

        } catch (Exception e) {

            System.out.println("No previous system state found. Starting fresh.");

            return null;
        }
    }
}

/**
 * =============================================================
 * MAIN CLASS - UseCase12DataPersistenceRecovery
 * =============================================================
 * Demonstrates persistence and system recovery
 * @version 12.1
 */

public class U12 {

    public static void main(String[] args) {

        System.out.println("Data Persistence & System Recovery\n");

        PersistenceService persistenceService = new PersistenceService();

        // Attempt to load previous system state
        SystemState restoredState = persistenceService.loadState();

        Map<String, Integer> inventory;
        List<String> bookingHistory;

        if (restoredState != null) {

            inventory = restoredState.getInventory();
            bookingHistory = restoredState.getBookingHistory();

        } else {

            // Initialize new system state
            inventory = new HashMap<>();

            inventory.put("Single Room", 5);
            inventory.put("Double Room", 3);
            inventory.put("Suite Room", 2);

            bookingHistory = new ArrayList<>();
        }

        // Simulate booking activity
        bookingHistory.add("RES201 | Alice | Single Room");
        bookingHistory.add("RES202 | Bob | Double Room");

        inventory.put("Single Room", inventory.get("Single Room") - 1);
        inventory.put("Double Room", inventory.get("Double Room") - 1);

        // Display current state
        System.out.println("Current Inventory:");

        for (String room : inventory.keySet()) {

            System.out.println(room + " : " + inventory.get(room));
        }

        System.out.println("\nBooking History:");

        for (String booking : bookingHistory) {

            System.out.println(booking);
        }

        // Save system state
        SystemState currentState =
                new SystemState(inventory, bookingHistory);

        persistenceService.saveState(currentState);
    }
}