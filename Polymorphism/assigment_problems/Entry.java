public class Entry {
    private String bib;
    private double fee;
    private double paid;

    public Entry(String bib, double fee) {
        if (bib == null || bib.trim().length() < 4) {
            throw new IllegalArgumentException();
        }
        this.bib = bib;
        this.fee = fee;
        this.paid = 0.0;
    }

    public String getBib() { return bib; }
    public void pay(double amount) { this.paid += amount; }
    public double getBalanceDue() { return fee - paid; }

    public String announce() {
        return "Base Entry | Bib: " + bib + " | Balance: " + getBalanceDue();
    }

    public static String classifyGeneration(Entry e) {
        if (e instanceof ProRunner) return "Multilevel descendant (3 generations deep)";
        if (e instanceof TeamEntry) return "Hierarchical sibling (independent branch)";
        if (e instanceof SoloRunner) return "Single-inheritance specialization";
        return "Base race entry";
    }

    public static double getTotalBalanceDue(Entry[] entries) {
        double total = 0.0;
        for (Entry e : entries) {
            total += e.getBalanceDue();
        }
        return total;
    }
}

class SoloRunner extends Entry {
    private String cat;

    public SoloRunner(String bib, double fee, String cat) {
        super(bib, fee);
        this.cat = cat;
    }

    public String getCat() { return cat; }

    @Override
    public String announce() {
        return "Runner Entry | Bib: " + getBib() + " | Category: " + cat + " | Balance: " + getBalanceDue();
    }
}

class ProRunner extends SoloRunner {
    private double bonus;

    public ProRunner(String bib, double fee, String cat, double bonus) {
        super(bib, fee, cat);
        this.bonus = bonus;
    }

    @Override
    public String announce() {
        return "Elite Runner | Bib: " + getBib() + " | Category: " + getCat() + " | Sponsor Bonus: " + bonus + " | Balance: " + getBalanceDue();
    }
}

class TeamEntry extends Entry {
    private int size;

    public TeamEntry(String bib, double fee, int size) {
        super(bib, fee);
        this.size = size;
    }

    @Override
    public String announce() {
        return "Relay Team | Bib: " + getBib() + " | Team Size: " + size + " | Balance: " + getBalanceDue();
    }
}