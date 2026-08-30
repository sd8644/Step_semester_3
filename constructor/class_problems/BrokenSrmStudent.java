class BrokenSrmStudent {
    static String name;
    static String regNo;
    static int attendance;

    public BrokenSrmStudent(String name, String regNo, int attendance) {
        BrokenSrmStudent.name = name;
        BrokenSrmStudent.regNo = regNo;
        BrokenSrmStudent.attendance = attendance;
    }
}

class FixedSrmStudent {
    String name;
    String regNo;
    int attendance;

    static String university = "SRM";
    static int admissionCount = 11;

    public FixedSrmStudent(String name, int attendance) {
        this.name = name;
        this.attendance = attendance;
        this.regNo = "RA2311003010" + admissionCount++;
    }

    public void printIdCard() {
        System.out.println(name + " | " + regNo);
    }

    public static void printTotalAdmissions() {
        System.out.println("Students admitted so far: " + (admissionCount - 11));
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Broken version:");
        BrokenSrmStudent s1Broken = new BrokenSrmStudent("Ravi", "RA231100301011", 82);
        BrokenSrmStudent s2Broken = new BrokenSrmStudent("Meera", "RA231100301012", 74);

        System.out.println(BrokenSrmStudent.name);
        System.out.println(BrokenSrmStudent.name);
        System.out.println("(Ravi's data was overwritten — both students now show “Meera”)\n");

        System.out.println("Fixed version:");
        FixedSrmStudent s1Fixed = new FixedSrmStudent("Ravi", 82);
        FixedSrmStudent s2Fixed = new FixedSrmStudent("Meera", 74);

        s1Fixed.printIdCard();
        s2Fixed.printIdCard();
        FixedSrmStudent.printTotalAdmissions();
    }
}

/*
EXPLANATION:
- Marking name, regNo, and attendance as static means these variables belong to the SrmStudent class itself rather than to individual instances.
- Because static variables are shared across all instances in a single memory location, instantiating a second student ("Meera") updates the single static memory location, completely overwriting the values set by the first student ("Ravi").
- Name, registration number, and attendance percentage are unique state properties of each individual student, so they must be instance variables.
- University and admission count represent shared or global tracking data, which correctly belong as static fields shared by all instances.
*/