public final class SurgeFeeCalculator {
    private final double minimumSurgePercent;

    public SurgeFeeCalculator(double minimumSurgePercent) {
        this.minimumSurgePercent = minimumSurgePercent;
    }

    public final double calculateSurgeFee(double orderValue, int delayMinutes) {
        if (orderValue < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException("Order value and delay minutes must be non-negative");
        }
        if (delayMinutes == 0) {
            return 0.0;
        }

        double percent = 0.0;
        
        int tier1 = Math.min(delayMinutes, 5);
        percent += tier1 * 0.5;

        if (delayMinutes > 5) {
            int tier2 = Math.min(delayMinutes - 5, 10);
            percent += tier2 * 1.0;
        }

        if (delayMinutes > 15) {
            int tier3 = delayMinutes - 15;
            percent += tier3 * 2.0;
        }

        double effectivePercent = Math.max(percent, minimumSurgePercent);
        return orderValue * (effectivePercent / 100.0);
    }
}