public class PalindromeVerificationToolkit {

    public static boolean isPalindromeIterative(String text) {
        String cleaned = text.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        int left = 0;
        int right = cleaned.length() - 1;

        while (left < right) {
            if (cleaned.charAt(left) != cleaned.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static boolean isPalindromeRecursive(String text) {
        String cleaned = text.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        return checkRecursiveHelper(cleaned, 0, cleaned.length() - 1);
    }

    private static boolean checkRecursiveHelper(String text, int left, int right) {
        if (left >= right) {
            return true;
        }
        if (text.charAt(left) != text.charAt(right)) {
            return false;
        }
        return checkRecursiveHelper(text, left + 1, right - 1);
    }

    public static boolean isPalindromeArrayReversal(String text) {
        String cleaned = text.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        char[] original = cleaned.toCharArray();
        char[] reversed = new char[original.length];

        for (int i = 0; i < original.length; i++) {
            reversed[i] = original[original.length - 1 - i];
        }

        return new String(original).equals(new String(reversed));
    }

    public static void verifyAndPrint(String input) {
        boolean iterativeResult = isPalindromeIterative(input);
        boolean recursiveResult = isPalindromeRecursive(input);
        boolean arrayReversalResult = isPalindromeArrayReversal(input);

        String iterStr = iterativeResult ? "Palindrome" : "Not Palindrome";
        String recurStr = recursiveResult ? "Palindrome" : "Not Palindrome";
        String revStr = arrayReversalResult ? "Palindrome" : "Not Palindrome";

        System.out.println("Input: \"" + input + "\"");
        System.out.println("Iterative: " + iterStr + " | Recursive: " + recurStr + " | Array Reversal: " + revStr);
        System.out.println();
    }

    public static void main(String[] args) {
        verifyAndPrint("madam");
        verifyAndPrint("hello");
        verifyAndPrint("A man, a plan, a canal: Panama");
    }
}