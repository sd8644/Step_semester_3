public class TextStats {

    public static void countVowelsAndConsonants(String text) {
        int vowels = 0;
        int consonants = 0;
        String lowercaseText = text.toLowerCase();

        for (int i = 0; i < lowercaseText.length(); i++) {
            char ch = lowercaseText.charAt(i);

            if (ch >= 'a' && ch <= 'z') {
                if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                    vowels++;
                } else {
                    consonants++;
                }
            }
        }

        System.out.println("Vowels: " + vowels + " | Consonants: " + consonants);
    }

    public static void main(String[] args) {
        countVowelsAndConsonants("Java Programming");
    }
}