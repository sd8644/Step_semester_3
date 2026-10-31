public class MirrorText {

    public static String reverseEachWord(String sentence) {
        String[] words = sentence.split(" ");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            // Reverse the current word using StringBuilder
            StringBuilder reversedWord = new StringBuilder(words[i]);
            reversedWord.reverse();

            // Append the reversed word to the result
            result.append(reversedWord);

            // Add a space between words (but not after the last word)
            if (i < words.length - 1) {
                result.append(" ");
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        // Test Case
        String input = "hello club";
        String output = reverseEachWord(input);
        
        System.out.println("Input:  " + input);
        System.out.println("Output: " + output);
    }
}