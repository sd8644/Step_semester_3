public class ExtendedAccessRuleEngine {

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if ("private".equals(fieldModifier)) {
            return "SAME_CLASS".equals(accessorContext) ? "ALLOWED" : "DENIED";
        }

        if ("default".equals(fieldModifier)) {
            return ("SAME_CLASS".equals(accessorContext) || "SAME_PACKAGE".equals(accessorContext)) 
                   ? "ALLOWED" : "DENIED";
        }

        if ("protected".equals(fieldModifier)) {
            if ("SAME_CLASS".equals(accessorContext) 
                || "SAME_PACKAGE".equals(accessorContext) 
                || "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE".equals(accessorContext)) {
                return "ALLOWED";
            }
            return "DENIED";
        }

        if ("public".equals(fieldModifier)) {
            return "ALLOWED";
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
            if (!words[i].isEmpty()) {
                result.append(Character.toUpperCase(words[i].charAt(0)))
                      .append(words[i].substring(1).toLowerCase());
                if (i < words.length - 1) {
                    result.append(" ");
                }
            }
        }

        return result.toString();
    }
}