public class DeliveryAccount {
    private String studentId;
    private double orderValue;
    private static SurgeFeeCalculator calculator;

    static {
        calculator = new SurgeFeeCalculator(1.0);
    }

    public DeliveryAccount(String studentId) {
        this(studentId, 0.0);
    }

    public DeliveryAccount(String studentId, double orderValue) {
        this.studentId = studentId;
        this.orderValue = orderValue;
    }

    // Reuses Problem 4's SurgeFeeCalculator
    public final double calculateSurgeFee(int delayMinutes) {
        return calculator.calculateSurgeFee(this.orderValue, delayMinutes);
    }

    public static void processBatch(DeliveryAccount[] accounts, double[] amounts, int[] delayMinutesArray) {
        if (accounts == null || amounts == null || delayMinutesArray == null) {
            throw new IllegalArgumentException("Input arrays cannot be null");
        }
        if (accounts.length != amounts.length || accounts.length != delayMinutesArray.length) {
            throw new IllegalArgumentException("Parallel array lengths must match to prevent index misalignment");
        }

        int processed = 0, nullSkipped = 0, premium = 0, regular = 0;
        double grandTotalSurgeFees = 0.0;

        for (int i = 0; i < accounts.length; i++) {
            DeliveryAccount acc = accounts[i];
            if (acc == null) {
                nullSkipped++;
                continue;
            }

            acc.orderValue = amounts[i];
            double fee = acc.calculateSurgeFee(delayMinutesArray[i]);

            if (acc instanceof PremiumDeliveryAccount) {
                premium++;
                fee *= 0.5; // Premium gets 50% discount on surge fees
            } else {
                regular++;
            }

            grandTotalSurgeFees += fee;
            processed++;
        }

        System.out.println(processed + " processed | " + nullSkipped + " null skipped | " +
                           premium + " premium | " + regular + " regular | grand total surge fees = " + grandTotalSurgeFees);
    }
}

class PremiumDeliveryAccount extends DeliveryAccount {
    public PremiumDeliveryAccount(String studentId, double orderValue) {
        super(studentId, orderValue);
    }

    public PremiumDeliveryAccount(String studentId) {
        super(studentId);
    }
}