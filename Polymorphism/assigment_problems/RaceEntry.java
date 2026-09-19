public class RaceEntry {
    private String bibNumber;
    private double entryFee;
    private double amountPaid;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("Bib number must be at least 4 non-whitespace characters.");
        }
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
        this.amountPaid = 0.0;
    }

    public void pay(double amount) {
        this.amountPaid += amount;
    }

    public double getBalanceDue() {
        return this.entryFee - this.amountPaid;
    }

    public static String registerBatch(String[] bibNumbers, double entryFee) {
        int registered = 0;
        int rejected = 0;

        for (String bib : bibNumbers) {
            try {
                new RaceEntry(bib, entryFee);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Registered: " + registered + " | Rejected: " + rejected;
    }
}

class RunnerEntry extends RaceEntry {
    private String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }
}