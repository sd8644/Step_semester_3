public final class BoardingPenaltyCalculator {
    private final double minimumPenaltyPercent;

    public BoardingPenaltyCalculator(double minimumPenaltyPercent) {
        if (minimumPenaltyPercent < 0) {
            throw new IllegalArgumentException("Minimum penalty percent cannot be negative.");
        }
        this.minimumPenaltyPercent = minimumPenaltyPercent;
    }

    public final double calculatePenalty(double ticketFare, int minutesLate) {
        if (ticketFare < 0 || minutesLate < 0) {
            throw new IllegalArgumentException("Fare and minutes late must be non-negative.");
        }

        if (minutesLate == 0) {
            return 0.0;
        }

        int tier1Minutes = Math.min(minutesLate, 5);
        int tier2Minutes = Math.max(0, Math.min(minutesLate - 5, 10));
        int tier3Minutes = Math.max(0, minutesLate - 15);

        double tieredPenalty = ticketFare * ((tier1Minutes * 0.005) + (tier2Minutes * 0.01) + (tier3Minutes * 0.02));
        double minimumFloor = ticketFare * (this.minimumPenaltyPercent / 100.0);

        return Math.max(tieredPenalty, minimumFloor);
    }
}