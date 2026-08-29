public class TypingSpeedChecker {

    public static void checkTypingAccuracy(String original, String typed) {
        int totalChars = original.length();
        int matchedCount = 0;
        int firstMismatchPos = -1;
        char origChar = ' ';
        char typedChar = ' ';

        for (int i = 0; i < totalChars; i++) {
            char c1 = original.charAt(i);
            char c2 = typed.charAt(i);

            if (c1 == c2) {
                matchedCount++;
            } else if (firstMismatchPos == -1) {
                firstMismatchPos = i + 1;
                origChar = c1;
                typedChar = c2;
            }
        }

        double accuracy = ((double) matchedCount / totalChars) * 100;

        if (firstMismatchPos == -1) {
            System.out.printf("Matched: %d/%d | Accuracy: %.2f%% | No Mismatches%n", 
                              matchedCount, totalChars, accuracy);
        } else {
            System.out.printf("Matched: %d/%d | Accuracy: %.2f%% | First Mismatch at position %d ('%c' vs '%c')%n", 
                              matchedCount, totalChars, accuracy, firstMismatchPos, origChar, typedChar);
        }
    }

    public static void main(String[] args) {
        String original1 = "hello world";
        String typed1 = "hello worlt";
        checkTypingAccuracy(original1, typed1);

        String original2 = "coding";
        String typed2 = "coding";
        checkTypingAccuracy(original2, typed2);
    }
}