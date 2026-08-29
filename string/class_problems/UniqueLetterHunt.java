import java.util.LinkedHashMap;
import java.util.Map;

public class UniqueLetterHunt {

    public static char findFirstNonRepeatingChar(String text) {
        Map<Character, Integer> frequencyMap = new LinkedHashMap<>();

        for (char ch : text.toCharArray()) {
            frequencyMap.put(ch, frequencyMap.getOrDefault(ch, 0) + 1);
        }

        for (char ch : text.toCharArray()) {
            if (frequencyMap.get(ch) == 1) {
                return ch;
            }
        }

        return '\0';
    }

    public static void processInput(String text) {
        char result = findFirstNonRepeatingChar(text);
        
        System.out.println("Input: \"" + text + "\"");
        if (result != '\0') {
            System.out.println("First Non-Repeating Character: '" + result + "'");
        } else {
            System.out.println("No Non-Repeating Character Found");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        processInput("swiss");
        processInput("aabbcc");
        processInput("leetcode");
    }
}