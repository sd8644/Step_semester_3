public class PatientProfile {
    private String patientId;
    private String name;
    private boolean discharged;
    private String lockerPinHash;

    public PatientProfile() {
        this(null, null);
    }

    public PatientProfile(String name) {
        this(null, name);
    }

    public PatientProfile(String patientId, String name) {
        if (patientId != null) {
            setPatientId(patientId);
        }
        this.name = name;
        this.discharged = false;
    }

    public String getPatientId() {
        return this.patientId;
    }

    public void setPatientId(String patientId) {
        if (this.patientId == null && patientId != null) {
            this.patientId = patientId;
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDischarged() {
        return this.discharged;
    }

    public void setDischarged(boolean discharged) {
        this.discharged = discharged;
    }

    public void setLockerPin(String pin) {
        if (pin != null) {
            this.lockerPinHash = String.valueOf(pin.hashCode());
        }
    }
}