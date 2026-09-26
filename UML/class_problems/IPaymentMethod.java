import java.util.*;

interface IPaymentMethod {
    boolean processPayment(double amount);
    String getMethodName();
}

class CreditCardPayment implements IPaymentMethod {
    public boolean processPayment(double amount) { return true; }
    public String getMethodName() { return "Credit Card"; }
}

class DigitalWalletPayment implements IPaymentMethod {
    private boolean shouldSucceed;
    public DigitalWalletPayment(boolean shouldSucceed) { this.shouldSucceed = shouldSucceed; }
    public boolean processPayment(double amount) { return shouldSucceed; }
    public String getMethodName() { return "Digital Wallet"; }
}

class FoodItem {
    private String name;
    private double price;
    public FoodItem(String name, double price) { this.name = name; this.price = price; }
    public String getName() { return name; }
    public double getPrice() { return price; }
}

class LineItem {
    private FoodItem foodItem;
    private int quantity;
    public LineItem(FoodItem foodItem, int quantity) { this.foodItem = foodItem; this.quantity = quantity; }
    public FoodItem getFoodItem() { return foodItem; }
    public int getQuantity() { return quantity; }
    public double getSubtotal() { return foodItem.getPrice() * quantity; }
}

enum OrderStatus { CREATED, PENDING_PAYMENT, PAID }

class Order {
    private String orderId;
    private List items = new ArrayList<>();
    private OrderStatus status = OrderStatus.CREATED;

    public Order(String orderId) { this.orderId = orderId; }
    public String getOrderId() { return orderId; }
    public OrderStatus getStatus() { return status; }

    public void addItem(FoodItem item, int quantity) {
        items.add(new LineItem(item, quantity));
    }

    public double calculateTotal() {
        double total = 0;
        for (LineItem item : items) total += item.getSubtotal();
        return total;
    }

    public boolean placeOrder(IPaymentMethod paymentMethod) {
        if (items.isEmpty()) {
            System.out.println("Cannot place order: Order must contain at least one item.");
            return false;
        }
        
        boolean success = paymentMethod.processPayment(calculateTotal());
        if (success) {
            status = OrderStatus.PAID;
            System.out.printf("Order placed successfully. Payment via %s successful. Order status: Paid.%n", paymentMethod.getMethodName());
            System.out.printf("Notification: Order #%s placed and paid.%n", orderId);
        } else {
            status = OrderStatus.PENDING_PAYMENT;
            System.out.printf("Order placed. Payment via %s failed. Order status: Pending Payment.%n", paymentMethod.getMethodName());
            System.out.printf("Notification: Order #%s placed, awaiting payment.%n", orderId);
        }
        return success;
    }
}

public class Main {
    public static void main(String[] args) {
        FoodItem pizza = new FoodItem("Pizza", 12.0);
        FoodItem soda = new FoodItem("Soda", 2.5);
        FoodItem burger = new FoodItem("Burger", 8.0);

        // 1 & 2. Attempt empty order placement
        Order emptyOrder = new Order("122");
        System.out.println("Order created.");
        emptyOrder.placeOrder(new CreditCardPayment());

        // 3. Order 123: Add Pizza & Soda, pay via Credit Card
        Order order123 = new Order("123");
        order123.addItem(pizza, 2);
        order123.addItem(soda, 1);
        System.out.println("Order created. Added Pizza (Qty 2), Soda (Qty 1).");
        order123.placeOrder(new CreditCardPayment());

        // 4. Order 124: Add Burger, pay via Digital Wallet (failed)
        Order order124 = new Order("124");
        order124.addItem(burger, 1);
        System.out.println("Order created. Added Burger (Qty 1).");
        order124.placeOrder(new DigitalWalletPayment(false));
    }
}