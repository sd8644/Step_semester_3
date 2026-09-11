import java.util.Arrays;
import java.util.stream.Collectors;

public class AccessChecker {

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if ("public".equals(fieldModifier)) {
            return "ALLOWED";
        }
        
        if ("private".equals(fieldModifier)) {
            return "DENIED";
        }
        
        if ("package-private".equals(fieldModifier) || "default".equals(fieldModifier)) {
            if ("SAME_PACKAGE".equals(accessorContext) || "SUBCLASS_SAME_PACKAGE".equals(accessorContext)) {
                return "ALLOWED";
            }
            return "DENIED";
        }
        
        if ("protected".equals(fieldModifier)) {
            if ("SAME_PACKAGE".equals(accessorContext) || 
                "SUBCLASS_SAME_PACKAGE".equals(accessorContext) || 
                "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE".equals(accessorContext)) {
                return "ALLOWED";
            }
            return "DENIED";
        }
        
        return "DENIED";
    }

    public static String describeContext(String accessorContext) {
        if (accessorContext == null || accessorContext.isEmpty()) {
            return "";
        }

        String[] words = accessorContext.split("_");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i].toLowerCase();
            result.append(Character.toUpperCase(word.charAt(0)))
                  .append(word.substring(1));
            
            if (i < words.length - 1) {
                result.append(" ");
            }
        }

        return result.toString();
    }
}