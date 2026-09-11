import java.util.LinkedHashMap;
import java.util.Map;

public class Membership {
    private String membershipId;
    String branchCode;
    protected double finesOwed;
    public String displayName;

    public Membership(String membershipId, String branchCode, double finesOwed, String displayName) {
        if (membershipId == null || membershipId.trim().length() < 4) {
            throw new IllegalArgumentException();
        }
        this.membershipId = membershipId;
        this.branchCode = branchCode;
        this.finesOwed = finesOwed;
        this.displayName = displayName;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if ("public".equals(fieldModifier)) {
            return "ALLOWED";
        }
        if ("private".equals(fieldModifier)) {
            return "SAME_CLASS".equals(accessorContext) ? "ALLOWED" : "DENIED";
        }
        if ("default".equals(fieldModifier) || "package-private".equals(fieldModifier)) {
            return ("SAME_CLASS".equals(accessorContext) || "SAME_PACKAGE".equals(accessorContext)) ? "ALLOWED" : "DENIED";
        }
        if ("protected".equals(fieldModifier)) {
            return ("SAME_CLASS".equals(accessorContext) || "SAME_PACKAGE".equals(accessorContext)) ? "ALLOWED" : "DENIED";
        }
        return "DENIED";
    }

    public static String summarizeByModifier(String[][] attempts) {
        Map counts = new LinkedHashMap<>();
        counts.put("private", new int[]{0, 0});
        counts.put("default", new int[]{0, 0});
        counts.put("protected", new int[]{0, 0});
        counts.put("public", new int[]{0, 0});

        if (attempts != null) {
            for (String[] attempt : attempts) {
                if (attempt != null && attempt.length >= 2) {
                    String modifier = "package-private".equals(attempt[0]) ? "default" : attempt[0];
                    String context = attempt[1];
                    String result = classifyAccess(modifier, context);

                    if (counts.containsKey(modifier)) {
                        if ("ALLOWED".equals(result)) {
                            counts.get(modifier)[0]++;
                        } else {
                            counts.get(modifier)[1]++;
                        }
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (Map.Entry entry : counts.entrySet()) {
            if (index > 0) {
                sb.append(" | ");
            }
            sb.append(entry.getKey()).append(": ")
              .append(entry.getValue()[0]).append(" allowed / ")
              .append(entry.getValue()[1]).append(" denied");
            index++;
        }

        return sb.toString();
    }
}