public class AccessRuleEngine {

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if ("private".equals(fieldModifier)) {
            return "SAME_CLASS".equals(accessorContext) ? "ALLOWED" : "DENIED";
        }
        if ("default".equals(fieldModifier) || "protected".equals(fieldModifier)) {
            return "SAME_CLASS".equals(accessorContext) || "SAME_PACKAGE".equals(accessorContext) ? "ALLOWED" : "DENIED";
        }
        if ("public".equals(fieldModifier)) {
            return "ALLOWED";
        }
        return "DENIED";
    }

    public static String summarizeBatch(String[][] attempts) {
        int allowed = 0;
        int denied = 0;
        if (attempts != null) {
            for (String[] attempt : attempts) {
                if (attempt != null && attempt.length >= 2) {
                    String result = classifyAccess(attempt[0], attempt[1]);
                    if ("ALLOWED".equals(result)) {
                        allowed++;
                    } else {
                        denied++;
                    }
                }
            }
        }
        return "Allowed: " + allowed + " | Denied: " + denied;
    }
}

class PatientRecord {
    private String patientId;
    String wardCode;
    protected double vitalsScore;
    public String facilityName;

    public PatientRecord(String patientId, String wardCode, double vitalsScore, String facilityName) {
        if (patientId == null) {
            throw new IllegalArgumentException("patientId cannot be null");
        }
        String trimmedId = patientId.trim();
        if (trimmedId.isEmpty() || trimmedId.length() < 4) {
            throw new IllegalArgumentException("patientId must not be blank and must be at least 4 characters long");
        }
        this.patientId = patientId;
        this.wardCode = wardCode;
        this.vitalsScore = vitalsScore;
        this.facilityName = facilityName;
    }

    public String getPatientId() {
        return patientId;
    }
}