import java.util.Arrays;
import java.util.regex.Pattern;

public final class DischargeSummary {
    private static final Pattern MED_CODE_PATTERN;

    static {
        MED_CODE_PATTERN = Pattern.compile("^MED-[A-Z]$");
    }

    private final String patientId;
    private final String[] medicationCodes;

    public DischargeSummary(String patientId, String[] medicationCodes) {
        if (patientId == null) {
            throw new IllegalArgumentException("patientId cannot be null");
        }
        if (medicationCodes == null) {
            throw new IllegalArgumentException("medicationCodes cannot be null");
        }

        for (String code : medicationCodes) {
            if (code == null || !MED_CODE_PATTERN.matcher(code).matches()) {
                throw new IllegalArgumentException("Invalid medication code format: " + code);
            }
        }

        this.patientId = patientId;
        this.medicationCodes = Arrays.copyOf(medicationCodes, medicationCodes.length);
    }

    public String getPatientId() {
        return patientId;
    }

    public String[] getMedicationCodes() {
        return Arrays.copyOf(medicationCodes, medicationCodes.length);
    }

    public DischargeSummary withCorrectedMedication(int index, String newCode) {
        if (index < 0 || index >= medicationCodes.length) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }

        String[] updatedCodes = Arrays.copyOf(medicationCodes, medicationCodes.length);
        updatedCodes[index] = newCode;
        return new DischargeSummary(this.patientId, updatedCodes);
    }

    public static String processNightlyBatch(DischargeSummary[] summaries) {
        int processed = 0;
        int nullSkipped = 0;
        int criticalCareCount = 0;
        int routineCount = 0;

        if (summaries != null) {
            for (DischargeSummary summary : summaries) {
                if (summary == null) {
                    nullSkipped++;
                    continue;
                }

                processed++;
                if (summary instanceof CriticalCareDischargeSummary) {
                    criticalCareCount++;
                } else {
                    routineCount++;
                }
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | " + criticalCareCount + " critical-care | " + routineCount + " routine";
    }
}

class CriticalCareDischargeSummary extends DischargeSummary {
    private final int icuDays;

    public CriticalCareDischargeSummary(String patientId, String[] medicationCodes, int icuDays) {
        super(patientId, medicationCodes);
        this.icuDays = icuDays;
    }

    public int getIcuDays() {
        return icuDays;
    }
}