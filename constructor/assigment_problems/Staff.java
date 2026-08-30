class Staff {
    private int staffId;
    private String staffName;
    private double basePay;

    public Staff(int staffId, String staffName, double basePay) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.basePay = basePay;
    }

    public double getBasePay() {
        return basePay;
    }
}

class ExecutiveStaff extends Staff {
    private double bonus;

    public ExecutiveStaff(int staffId, String staffName, double basePay, double bonus) {
        super(staffId, staffName, basePay);
        this.bonus = bonus;
    }

    public double totalEarnings() {
        return getBasePay() + bonus;
    }
}

class BayLocation {
    String bayCode;

    public BayLocation(String bayCode) {
        this.bayCode = bayCode;
    }
}

class StaffProfile {
    String fullName;
    String code;
    Staff staffRef;
    BayLocation bay;

    static int totalCount = 0;

    public StaffProfile(String fullName, String code, Staff staffRef, BayLocation bay) {
        this.fullName = fullName;
        this.code = code;
        this.staffRef = staffRef;
        this.bay = bay;
        totalCount++;
    }

    public String fullProfile() {
        double pay = (staffRef instanceof ExecutiveStaff) ? 
                     ((ExecutiveStaff) staffRef).totalEarnings() : staffRef.getBasePay();
        String bayInfo = (bay != null) ? bay.bayCode : "no parking assigned";

        return fullName + " | Pay: Rs " + pay + " | Slot: " + bayInfo;
    }
}

public class Main {
    public static void main(String[] args) {
        BayLocation b1 = new BayLocation("A1");
        BayLocation b2 = new BayLocation("A2");

        Staff s1 = new ExecutiveStaff(101, "Divya", 70000.0, 8000.0);
        Staff s2 = new Staff(102, "Karan", 40000.0);
        Staff s3 = new Staff(103, "Meera", 10000.0);

        StaffProfile p1 = new StaffProfile("Divya", "E101", s1, b1);
        StaffProfile p2 = new StaffProfile("Karan", "E102", s2, b2);
        StaffProfile p3 = new StaffProfile("Meera", "E103", s3, null);

        System.out.println(p1.fullProfile());
        System.out.println(p2.fullProfile());
        System.out.println(p3.fullProfile());
        System.out.println("Total records: " + StaffProfile.totalCount);
    }
}