public class InventoryParser {

    public static void parseInventoryRecord(String csvLine) {
        String[] fields = csvLine.split(",");

        // Validate that exactly 3 fields are present
        if (fields.length != 3) {
            System.out.println("Invalid Record");
            return;
        }

        // Extract individual fields
        String productName = fields[0];
        String sku = fields[1];
        String quantity = fields[2];

        // Print the formatted output
        System.out.println("Product: " + productName + " | SKU: " + sku + " | Qty: " + quantity);
    }

    public static void main(String[] args) {
        // Test Cases
        parseInventoryRecord("Wireless Mouse,WM-2201,150");
        parseInventoryRecord("Wireless Mouse,150");
    }
}