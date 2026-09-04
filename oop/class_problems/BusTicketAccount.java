public class BusTicketAccount {
    private static final double DEFAULT_FARE = 1000.0;
    private static final double FLAT_PENALTY_RATE = 0.01;

    static {
        System.setProperty("reconciliation.engine", "active");
    }

    private final String bookingId;
    private final double ticketFare;

    public BusTicketAccount(String bookingId, double ticketFare) {
        if (bookingId == null || bookingId.trim().isEmpty() || ticketFare < 0) {
            throw new IllegalArgumentException("Invalid ticket details.");
        }
        this.bookingId = bookingId;
        this.ticketFare = ticketFare;
    }

    public BusTicketAccount(String bookingId) {
        this(bookingId, DEFAULT_FARE);
    }

    public String getBookingId() {
        return bookingId;
    }

    public double getTicketFare() {
        return ticketFare;
    }

    public final double calculatePenalty(int minutesLate) {
        if (minutesLate < 0) {
            throw new IllegalArgumentException("Minutes late cannot be negative.");
        }
        return minutesLate * (this.ticketFare * FLAT_PENALTY_RATE);
    }

    public void processAccount(BusTicketAccount account, double amount, int minutesLate) {
        double penalty = calculatePenalty(minutesLate);
    }

    public static void processBatch(BusTicketAccount[] accounts, double[] amounts, int[] minutesLateArray) {
        if (accounts == null || amounts == null || minutesLateArray == null) {
            throw new IllegalArgumentException("Input arrays cannot be null.");
        }
        if (accounts.length != amounts.length || accounts.length != minutesLateArray.length) {
            throw new IllegalArgumentException("Array lengths must match to prevent mismatched account settlements.");
        }

        int processed = 0;
        int nullSkipped = 0;
        int sleeperCount = 0;
        int regularCount = 0;
        double grandTotalPenalties = 0.0;

        for (int i = 0; i < accounts.length; i++) {
            BusTicketAccount account = accounts[i];
            if (account == null) {
                nullSkipped++;
                continue;
            }

            int minutesLate = minutesLateArray[i];
            double penalty = account.calculatePenalty(minutesLate);

            if (account instanceof SleeperTicketAccount) {
                sleeperCount++;
            } else {
                regularCount++;
            }

            account.processAccount(account, amounts[i], minutesLate);
            grandTotalPenalties += penalty;
            processed++;
        }

        System.out.printf("%d processed | %d null skipped | %d sleeper | %d regular | grand total penalties = %.1f%n",
                processed, nullSkipped, sleeperCount, regularCount, grandTotalPenalties);
    }
}

class SleeperTicketAccount extends BusTicketAccount {
    public SleeperTicketAccount(String bookingId, double ticketFare) {
        super(bookingId, ticketFare);
    }

    public SleeperTicketAccount(String bookingId) {
        super(bookingId);
    }
}