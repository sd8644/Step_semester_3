import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BusTicket {
    private final String passengerName;
    private final String destination;
    private boolean checkedIn;

    public BusTicket(String passengerName, String destination) {
        if (!isValidName(passengerName) || !isValidDestination(destination)) {
            throw new IllegalArgumentException("Invalid booking details provided.");
        }
        this.passengerName = passengerName;
        this.destination = destination;
        this.checkedIn = false;
    }

    private static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c) && c != ' ') {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidDestination(String destination) {
        if (destination == null || destination.trim().isEmpty()) {
            return false;
        }
        for (char c : destination.toCharArray()) {
            if (!Character.isLetter(c) && c != ' ') {
                return false;
            }
        }
        return true;
    }

    public void markCheckedIn() {
        if (this.checkedIn) {
            throw new IllegalStateException("Ticket is already checked in.");
        }
        this.checkedIn = true;
    }

    public static void processBatch(String[][] rawBookings) {
        if (rawBookings == null) {
            System.out.println("Valid: 0 | Rejected: 0 | Duplicates skipped: 0");
            return;
        }

        int validCount = 0;
        int rejectedCount = 0;
        int duplicatesCount = 0;

        Set<BookingPair> seenBookings = new HashSet<>();

        for (String[] booking : rawBookings) {
            if (booking == null || booking.length < 2) {
                rejectedCount++;
                continue;
            }

            String name = booking[0];
            String dest = booking[1];

            try {
                BusTicket ticket = new BusTicket(name, dest);
                BookingPair pair = new BookingPair(name, dest);

                if (seenBookings.contains(pair)) {
                    duplicatesCount++;
                } else {
                    seenBookings.add(pair);
                    validCount++;
                }
            } catch (IllegalArgumentException e) {
                rejectedCount++;
            }
        }

        System.out.printf("Valid: %d | Rejected: %d | Duplicates skipped: %d%n",
                validCount, rejectedCount, duplicatesCount);
    }

    private static class BookingPair {
        private final String name;
        private final String destination;

        public BookingPair(String name, String destination) {
            this.name = name;
            this.destination = destination;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BookingPair that = (BookingPair) o;
            return Objects.equals(name, that.name) && Objects.equals(destination, that.destination);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, destination);
        }
    }
}