import java.util.Arrays;

public class Pass {
    private String code;
    private double cost;
    private double totalPaid;
    private double[] penalties = new double[10];
    private int penaltyCount = 0;

    public Pass(String code, double cost) {
        if (code == null || code.trim().isEmpty() || code.length() < 4) {
            throw new IllegalArgumentException();
        }
        this.code = code;
        this.cost = cost;
        this.totalPaid = 0;
    }

    public void pay(double amount) {
        this.totalPaid += amount;
    }

    public double getBalanceDue() {
        return cost - totalPaid;
    }

    protected void applyLateFee(double amount) {
        this.cost += amount;
        if (penaltyCount < penalties.length) {
            penalties[penaltyCount++] = amount;
        }
    }

    public double[] getLateFeeHistory() {
        return Arrays.copyOf(penalties, penaltyCount);
    }
}

class SeminarPass extends Pass {
    private String topic;

    public SeminarPass(String code, double cost, String topic) {
        super(code, cost);
        this.topic = topic;
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}