import java.util.*;

/**
 * =============================================================
 * CLASS - Reservation
 * =============================================================
 * Represents a confirmed booking
 * @version 8.1
 */

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
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

    public void displayReservation() {
        System.out.println(
                reservationId + " | " +
                        guestName + " | " +
                        roomType
        );
    }
}

/**
 * =============================================================
 * CLASS - BookingHistory
 * =============================================================
 * Stores confirmed reservations in chronological order
 * @version 8.1
 */

class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // Retrieve booking history
    public List<Reservation> getHistory() {
        return history;
    }
}

/**
 * =============================================================
 * CLASS - BookingReportService
 * =============================================================
 * Generates reports from booking history
 * @version 8.1
 */

class BookingReportService {

    private BookingHistory bookingHistory;

    public BookingReportService(BookingHistory bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    // Display full booking history
    public void displayAllBookings() {

        System.out.println("\nBooking History Report\n");

        for (Reservation reservation : bookingHistory.getHistory()) {
            reservation.displayReservation();
        }
    }

    // Generate summary report
    public void generateSummaryReport() {

        int totalBookings = bookingHistory.getHistory().size();

        Map<String, Integer> roomTypeCount = new HashMap<>();

        for (Reservation reservation : bookingHistory.getHistory()) {

            String roomType = reservation.getRoomType();

            roomTypeCount.put(
                    roomType,
                    roomTypeCount.getOrDefault(roomType, 0) + 1
            );
        }

        System.out.println("\nBooking Summary Report\n");

        System.out.println("Total Bookings: " + totalBookings);

        for (String roomType : roomTypeCount.keySet()) {
            System.out.println(roomType + " Bookings: " + roomTypeCount.get(roomType));
        }
    }
}

/**
 * =============================================================
 * MAIN CLASS - UseCase8BookingHistoryReport
 * =============================================================
 * Demonstrates booking history tracking and reporting
 * @version 8.1
 */

public class U8 {

    public static void main(String[] args) {

        System.out.println("Booking History & Reporting System\n");

        BookingHistory bookingHistory = new BookingHistory();

        // Simulated confirmed bookings
        bookingHistory.addReservation(
                new Reservation("RES101", "Alice", "Single Room")
        );

        bookingHistory.addReservation(
                new Reservation("RES102", "Bob", "Double Room")
        );

        bookingHistory.addReservation(
                new Reservation("RES103", "Charlie", "Suite Room")
        );

        bookingHistory.addReservation(
                new Reservation("RES104", "David", "Single Room")
        );

        BookingReportService reportService =
                new BookingReportService(bookingHistory);

        // Display booking history
        reportService.displayAllBookings();

        // Generate summary report
        reportService.generateSummaryReport();
    }
}