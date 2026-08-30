public class IsbnNormalizer {

    public static String normalizeCode(String raw) {
        if (raw == null) {
            return "";
        }
        
        String trimmed = raw.trim();
        if (trimmed.length() < 3) {
            return trimmed.toUpperCase();
        }
        
        // Uppercase only the first 3 characters and attach the remaining rest
        String pubCode = trimmed.substring(0, 3).toUpperCase();
        String rest = trimmed.substring(3);
        
        return pubCode + rest;
    }

    public static String validateAndFormat(String code) {
        // Stage 1: Length Validation
        if (code.length() != 13) {
            return "Invalid: wrong length";
        }

        // Stage 2: Publisher Code (First 3 characters must be letters)
        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(code.charAt(i))) {
                return "Invalid: publisher code must be 3 letters";
            }
        }

        // Stage 3: Body (Remaining 10 characters must be digits)
        for (int i = 3; i < 13; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return "Invalid: body must contain only digits";
            }
        }

        // Stage 4: Format output using StringBuilder
        String pubCode = code.substring(0, 3);
        String year = code.substring(3, 7);
        String catalog = code.substring(7);

        StringBuilder formatted = new StringBuilder();
        formatted.append("[")
                 .append(pubCode)
                 .append("] YEAR: ")
                 .append(year)
                 .append(" | CATALOG: ")
                 .append(catalog);

        return formatted.toString();
    }

    public static void main(String[] args) {
        // Test Cases
        String[] testInputs = {
            "  pen2026004251 ",
            "12N2026004251",
            "abc202600425",    // Wrong length (12 chars)
            "PEN202600425A"     // Non-digit body
        };

        for (String input : testInputs) {
            String normalized = normalizeCode(input);
            String result = validateAndFormat(normalized);
            System.out.println("Input:  \"" + input + "\"");
            System.out.println("Output: " + result + "\n");
        }
    }
}