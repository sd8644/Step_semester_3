public class AtmValidator {

    public static void checkPinLength(String pin) {
        if (pin.length() != 4) {
            System.out.println("Invalid PIN — must be exactly 4 digits.");
        } else {
            System.out.println("PIN length OK.");
        }
    }

    public static void main(String[] args) {
        // Test Cases
        checkPinLength("482");   // Input: 3 digits
        checkPinLength("4820");  // Input: 4 digits
    }
}