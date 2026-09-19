import java.util.Arrays;

public class BaseTicket {
    private String bib;
    private double fee;
    private double paid;
    private double[] history = new double[10];
    private int count = 0;

    public BaseTicket(String bib, double fee) {
        if (bib == null || bib.trim().length() < 4) {
            throw new IllegalArgumentException();
        }
        this.bib = bib;
        this.fee = fee;
    }

    public void pay(double amount) { this.paid += amount; }
    public double getBalanceDue() { return fee - paid; }

    protected void applyLateFee(double amount) {
        this.fee += amount;
        if (count == history.length) {
            history = Arrays.copyOf(history, history.length * 2);
        }
        history[count++] = amount;
    }

    public double[] getLateFeeHistory() {
        return Arrays.copyOf(history, count);
    }
}

class RacerPass extends BaseTicket {
    public RacerPass(String bib, double fee, String cat) {
        super(bib, fee);
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}