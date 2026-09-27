import java.util.*;

interface PricingPlan {
    String getPlanName();
    double calculatePrice(double basePrice);
}

class DayScholarPlan implements PricingPlan {
    public String getPlanName() { return "Day Scholar"; }
    public double calculatePrice(double basePrice) { return basePrice; }
}

class HostellerPlan implements PricingPlan {
    public String getPlanName() { return "Hosteller"; }
    public double calculatePrice(double basePrice) { return basePrice * 0.90; }
}

class StaffPlan implements PricingPlan {
    public String getPlanName() { return "Staff"; }
    public double calculatePrice(double basePrice) { return basePrice * 0.80; }
}

enum TransactionType {
    TOP_UP,
    PURCHASE,
    REFUND
}

class Transaction {
    private final String id;
    private final TransactionType type;
    private final String description;
    private final double amount;
    private boolean refunded = false;

    public Transaction(String id, TransactionType type, String description, double amount) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.amount = amount;
    }

    public String getId() { return id; }
    public TransactionType getType() { return type; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public boolean isRefunded() { return refunded; }
    public void setRefunded(boolean refunded) { this.refunded = refunded; }
}

class SmartCard {
    private final String cardId;
    private final PricingPlan plan;
    private final List transactions = new ArrayList<>();
    private boolean isBlocked = false;

    public SmartCard(String cardId, PricingPlan plan) {
        this.cardId = cardId;
        this.plan = plan;
    }

    public String getCardId() { return cardId; }
    public boolean isBlocked() { return isBlocked; }

    public void setBlocked(boolean blocked) {
        this.isBlocked = blocked;
        System.out.println("Card " + cardId + " is now " + (blocked ? "BLOCKED" : "UNBLOCKED") + ".");
    }

    public double getBalance() {
        double balance = 0.0;
        for (Transaction t : transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    public boolean topUp(double amount) {
        if (isBlocked) {
            System.out.println("Top-up failed: Card " + cardId + " is BLOCKED.");
            return false;
        }
        if (amount < 100.0) {
            System.out.println("Top-up failed: Minimum top-up amount is ₹100.00.");
            return false;
        }
        if (getBalance() + amount > 5000.0) {
            System.out.println("Top-up failed: Balance cannot exceed ₹5,000.00.");
            return false;
        }

        Transaction t = new Transaction(UUID.randomUUID().toString(), TransactionType.TOP_UP, "Top-up", amount);
        transactions.add(t);
        System.out.printf("%s topped up with ₹%.2f. Balance: ₹%.2f.%n", cardId, amount, getBalance());
        return true;
    }

    public Transaction buyItem(String itemName, double basePrice) {
        if (isBlocked) {
            System.out.println("Purchase failed: Card " + cardId + " is BLOCKED.");
            return null;
        }

        double finalPrice = plan.calculatePrice(basePrice);
        if (getBalance() < finalPrice) {
            System.out.printf("Purchase failed: Insufficient balance (required ₹%.2f, available ₹%.2f).%n",
                    finalPrice, getBalance());
            return null;
        }

        Transaction t = new Transaction(UUID.randomUUID().toString(), TransactionType.PURCHASE, itemName, -finalPrice);
        transactions.add(t);
        System.out.printf("%s purchased for ₹%.2f. Balance: ₹%.2f.%n", itemName, finalPrice, getBalance());
        return t;
    }

    public boolean refund(Transaction purchaseTransaction) {
        if (isBlocked) {
            System.out.println("Refund failed: Card " + cardId + " is BLOCKED.");
            return false;
        }
        if (purchaseTransaction == null || purchaseTransaction.getType() != TransactionType.PURCHASE) {
            System.out.println("Refund rejected: Invalid transaction.");
            return false;
        }
        if (purchaseTransaction.isRefunded()) {
            System.out.println("Refund rejected: " + purchaseTransaction.getDescription() + " has already been refunded.");
            return false;
        }

        double refundAmount = Math.abs(purchaseTransaction.getAmount());
        if (getBalance() + refundAmount > 5000.0) {
            System.out.println("Refund failed: Balance cannot exceed ₹5,000.00.");
            return false;
        }

        purchaseTransaction.setRefunded(true);
        Transaction refundTx = new Transaction(UUID.randomUUID().toString(), TransactionType.REFUND, "Refund: " + purchaseTransaction.getDescription(), refundAmount);
        transactions.add(refundTx);

        System.out.printf("Refund of ₹%.2f for %s processed. Balance: ₹%.2f.%n",
                refundAmount, purchaseTransaction.getDescription(), getBalance());
        return true;
    }

    public void printMiniStatement() {
        StringBuilder statement = new StringBuilder();
        statement.append("Mini-statement for ").append(cardId).append(": ");
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            if (t.getAmount() >= 0) {
                statement.append(String.format("+%.2f", t.getAmount()));
            } else {
                statement.append(String.format("%.2f", t.getAmount()));
            }
            if (i < transactions.size() - 1) {
                statement.append(", ");
            }
        }
        statement.append(String.format(" = ₹%.2f.", getBalance()));
        System.out.println(statement.toString());
    }
}

public class CampusCanteenSmartCard {
    public static void main(String[] args) {
        PricingPlan hostellerPlan = new HostellerPlan();
        SmartCard card = new SmartCard("C-2045", hostellerPlan);

        card.topUp(500.00);

        Transaction vegThaliTx = card.buyItem("Veg Thali", 120.00);
        Transaction coldCoffeeTx = card.buyItem("Cold Coffee", 60.00);

        card.buyItem("Items", 400.00);

        card.refund(vegThaliTx);
        card.refund(vegThaliTx);

        card.printMiniStatement();
    }
}