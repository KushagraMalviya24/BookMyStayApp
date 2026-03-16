import java.util.*;

/**
 * =============================================================
 * CLASS - AddOnService
 * =============================================================
 * Represents an optional service for a reservation
 * @version 7.1
 */

class AddOnService {

    private String serviceName;
    private double serviceCost;

    public AddOnService(String serviceName, double serviceCost) {
        this.serviceName = serviceName;
        this.serviceCost = serviceCost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getServiceCost() {
        return serviceCost;
    }
}

/**
 * =============================================================
 * CLASS - AddOnServiceManager
 * =============================================================
 * Manages services selected for reservations
 * @version 7.1
 */

class AddOnServiceManager {

    // ReservationID -> List of services
    private Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {

        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println(service.getServiceName()
                + " added to reservation " + reservationId);
    }

    // Display services
    public void displayServices(String reservationId) {

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("\nServices for Reservation: " + reservationId);

        for (AddOnService service : services) {

            System.out.println(service.getServiceName()
                    + " : ₹" + service.getServiceCost());
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null)
            return 0;

        double total = 0;

        for (AddOnService service : services) {
            total += service.getServiceCost();
        }

        return total;
    }
}

/**
 * =============================================================
 * MAIN CLASS - UseCase7AddOnServiceSelection
 * =============================================================
 * Demonstrates adding optional services to reservations
 * @version 7.1
 */

public class U7 {

    public static void main(String[] args) {

        System.out.println("Add-On Service Selection\n");

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        String reservationId = "RES101";

        // Guest selects services
        serviceManager.addService(reservationId,
                new AddOnService("Breakfast", 500));

        serviceManager.addService(reservationId,
                new AddOnService("Airport Pickup", 1200));

        serviceManager.addService(reservationId,
                new AddOnService("Spa Access", 2000));

        // Display services
        serviceManager.displayServices(reservationId);

        // Display total cost
        double totalCost = serviceManager.calculateTotalCost(reservationId);

        System.out.println("\nTotal Add-On Cost: ₹" + totalCost);
    }
}