public class BaseRegistration {
    private String bib;
    private double fee;
    private double paid;

    public BaseRegistration(String bib, double fee) {
        if (bib == null || bib.trim().length() < 4) {
            throw new IllegalArgumentException();
        }
        this.bib = bib;
        this.fee = fee;
    }

    public String getBib() { return bib; }
    public void pay(double amount) { this.paid += amount; }
    public double getBalanceDue() { return fee - paid; }

    public String announce() {
        return "Race Entry | Bib: " + bib + " | Balance: " + getBalanceDue();
    }

    public static String announceAll(BaseRegistration[] entries) {
        StringBuilder sb = new StringBuilder();
        for (BaseRegistration entry : entries) {
            sb.append(entry.announce());
            if (entry instanceof SquadRegistration) {
                SquadRegistration squad = (SquadRegistration) entry;
                sb.append(" [Team size via downcast: ").append(squad.getSquadSize()).append("]");
            }
            sb.append(" | ");
        }
        return sb.toString();
    }
}

class AthleteRegistration extends BaseRegistration {
    private String category;

    public AthleteRegistration(String bib, double fee, String category) {
        super(bib, fee);
        this.category = category;
    }

    @Override
    public String announce() {
        return "Runner Entry | Bib: " + getBib() + " | Category: " + category + " | Balance: " + getBalanceDue();
    }
}

class SquadRegistration extends BaseRegistration {
    private int squadSize;

    public SquadRegistration(String bib, double fee, int squadSize) {
        super(bib, fee);
        this.squadSize = squadSize;
    }

    public int getSquadSize() {
        return squadSize;
    }

    @Override
    public String announce() {
        return "Relay Team | Bib: " + getBib() + " | Team Size: " + squadSize + " | Balance: " + getBalanceDue();
    }
}