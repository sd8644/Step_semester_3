public class FoodOrder {
    private final String studentName;
    private final String dishName;
    private boolean isDelivered = false;

    public FoodOrder(String studentName, String dishName) {
        if (studentName == null || studentName.trim().isEmpty() || 
            dishName == null || dishName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid order details");
        }
        this.studentName = studentName.trim();
        this.dishName = dishName.trim();
    }

    public void markDelivered() {
        if (isDelivered) {
            System.out.println("Warning: Order for " + studentName + " was already delivered!");
        } else {
            isDelivered = true;
            System.out.println("Order for " + studentName + " delivered successfully.");
        }
    }

    public static void processBatch(String[][] rawOrders) {
        int valid = 0;
        int rejected = 0;

        if (rawOrders != null) {
            for (String[] order : rawOrders) {
                try {
                    if (order == null || order.length < 2) {
                        rejected++;
                        continue;
                    }
                    new FoodOrder(order[0], order[1]);
                    valid++;
                } catch (IllegalArgumentException e) {
                    rejected++;
                }
            }
        }

        System.out.println("Valid: " + valid + " | Rejected: " + rejected);
    }
}