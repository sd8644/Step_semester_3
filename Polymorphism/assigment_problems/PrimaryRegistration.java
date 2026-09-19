public class PrimaryRegistration {
    private static int counter = 0;
    private final String entryCode;
    private String bib;
    private double fee;
    private double paid;

    public PrimaryRegistration(String bib, double fee) {
        if (bib == null || bib.trim().length() < 4) {
            throw new IllegalArgumentException();
        }
        this.bib = bib;
        this.fee = fee;
        this.entryCode = "ENTRY-" + (++counter);
    }

    public String getEntryCode() { return entryCode; }
    public static int getBibCounter() { return counter; }

    public void pay(double amount) {
        this.paid += amount;
    }

    public void pay(double amount, String mode) {
        pay(amount);
        System.out.println("Paying via " + mode);
    }

    public static boolean isValidDiscountCode(String code) {
        if (code == null || code.length() != 5) return false;
        if (code.charAt(0) != 'M') return false;
        if (!Character.isDigit(code.charAt(1))) return false;
        if (!Character.isDigit(code.charAt(2))) return false;
        if (!Character.isDigit(code.charAt(3))) return false;
        return Character.isUpperCase(code.charAt(4));
    }

    public static String settleNight(PrimaryRegistration[] entries) {
        if (entries == null) return "0 processed | 0 null skipped | 0 relay | 0 individual";
        int nullCount = 0;
        int relayCount = 0;
        int individualCount = 0;

        for (PrimaryRegistration entry : entries) {
            if (entry == null) {
                nullCount++;
            } else if (entry instanceof SquadRoster) {
                relayCount++;
            } else {
                individualCount++;
            }
        }

        int processed = relayCount + individualCount;
        return processed + " processed | " + nullCount + " null skipped | " + relayCount + " relay | " + individualCount + " individual";
    }
}

class IndividualRoster extends PrimaryRegistration {
    private String category;

    public IndividualRoster(String bib, double fee, String category) {
        super(bib, fee);
        this.category = category;
    }
}

class SquadRoster extends PrimaryRegistration {
    private int teamSize;

    public SquadRoster(String bib, double fee, int teamSize) {
        super(bib, fee);
        this.teamSize = teamSize;
    }
}