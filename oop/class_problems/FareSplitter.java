import java.math.BigDecimal;
import java.math.RoundingMode;

public class FareSplitter {
    private static final double DEFAULT_FARE = 0.0;
    private static final int DEFAULT_PASSENGER_COUNT = 2;

    private final String tripId;
    private final double totalFare;
    private final int passengerCount;

    public FareSplitter(String tripId, double totalFare, int passengerCount) {
        if (tripId == null || tripId.trim().isEmpty()) {
            throw new IllegalArgumentException("Trip ID cannot be null or empty.");
        }
        if (totalFare < 0) {
            throw new IllegalArgumentException("Total fare cannot be negative.");
        }
        if (passengerCount <= 0) {
            throw new IllegalArgumentException("Passenger count must be greater than zero.");
        }
        this.tripId = tripId;
        this.totalFare = totalFare;
        this.passengerCount = passengerCount;
    }

    public FareSplitter(String tripId, double totalFare) {
        this(tripId, totalFare, DEFAULT_PASSENGER_COUNT);
    }

    public FareSplitter(String tripId) {
        this(tripId, DEFAULT_FARE, DEFAULT_PASSENGER_COUNT);
    }

    public double[] fareBreakdown() {
        long totalCents = Math.round(this.totalFare * 100);
        long baseShareCents = totalCents / this.passengerCount;
        long remainderCents = totalCents % this.passengerCount;

        double[] breakdown = new double[this.passengerCount];

        for (int i = 0; i < this.passengerCount; i++) {
            long shareCents = baseShareCents;
            if (i == this.passengerCount - 1) {
                shareCents += remainderCents;
            }
            breakdown[i] = shareCents / 100.0;
        }

        return breakdown;
    }

    public boolean isConfirmationOverdue(int confirmed, int expected) {
        if (expected <= 0 || confirmed < 0) {
            return false;
        }
        return confirmed < expected;
    }

    public String getTripId() {
        return tripId;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public int getPassengerCount() {
        return passengerCount;
    }
}