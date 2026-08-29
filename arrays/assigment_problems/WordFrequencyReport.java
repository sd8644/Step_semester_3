import java.util.*;

public class WordFrequencyReport {

    public static void printFilteredWordFrequency(String feedback) {
        if (feedback == null || feedback.trim().isEmpty()) {
            return;
        }

        // Define fixed stop words
        Set<String> stopWords = new HashSet<>(Arrays.asList("the", "was", "and", "a", "is", "of", "in"));

        // Step 1: Normalize (lowercase and strip punctuation)
        String cleanedText = feedback.toLowerCase()
                                     .replace(",", "")
                                     .replace(".", "")
                                     .replace("!", "")
                                     .replace("?", "");

        // Step 2: Split into words using whitespace pattern
        String[] words = cleanedText.split("\\s+");

        // Step 3 & 4: Filter stop words and count frequencies
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String word : words) {
            if (!word.isEmpty() && !stopWords.contains(word)) {
                frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
            }
        }

        // Step 5: Sort unique words by count in descending order
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(frequencyMap.entrySet());
        entries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Step 6: Print results
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        String input = "The mentor was great, the session was great and clear.";
        printFilteredWordFrequency(input);
    }
}