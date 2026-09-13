public class BookingPass {
    private static int counter = 1000;
    private final String ticketId;
    private double basePrice;
    private double amountPaid;

    public BookingPass(double basePrice) {
        this.ticketId = "TCK-" + (++counter);
        this.basePrice = basePrice;
        this.amountPaid = 0;
    }

    public String getTicketId() {
        return ticketId;
    }

    public static int getTicketsIssued() {
        return counter - 1000;
    }

    public void pay(double amount) {
        this.amountPaid += amount;
    }

    public void pay(double amount, String mode) {
        System.out.println("Paid via " + mode);
        pay(amount);
    }

    public double getBalanceDue() {
        return basePrice - amountPaid;
    }

    public static boolean isValidPromoCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }
        if (code.charAt(0) != 'F') {
            return false;
        }
        for (int i = 1; i <= 3; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return false;
            }
        }
        return Character.isUpperCase(code.charAt(4));
    }

    public static String processNightlySettlement(BookingPass[] tickets) {
        int processed = 0;
        int nullSkipped = 0;
        int groupCount = 0;
        int individualCount = 0;

        if (tickets != null) {
            for (BookingPass ticket : tickets) {
                if (ticket == null) {
                    nullSkipped++;
                } else {
                    processed++;
                    if (ticket instanceof SquadPass) {
                        groupCount++;
                    } else {
                        individualCount++;
                    }
                }
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | " + groupCount + " group | " + individualCount + " individual";
    }
}

class SquadPass extends BookingPass {
    private int groupSize;

    public SquadPass(double basePrice, int groupSize) {
        super(basePrice);
        this.groupSize = groupSize;
    }
}