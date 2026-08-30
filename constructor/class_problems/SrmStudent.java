class SrmStudent {
    String name;
    String regNo;
    int attendance;

    public SrmStudent(String name, String regNo, int attendance) {
        this.name = name;
        this.regNo = regNo;
        this.attendance = attendance;
    }

    public void addAttendanceUpdate(int newAttendance) {
        this.attendance = newAttendance;
    }

    public boolean isEligible() {
        return this.attendance >= 75;
    }

    public static double classAverage(SrmStudent[] students) {
        if (students == null || students.length == 0) {
            return 0.0;
        }
        double sum = 0;
        for (SrmStudent student : students) {
            sum += student.attendance;
        }
        return sum / students.length;
    }
}

public class Main {
    public static void main(String[] args) {
        SrmStudent[] students = new SrmStudent[] {
            new SrmStudent("Ravi", "REG001", 82),
            new SrmStudent("Anitha", "REG002", 68),
            new SrmStudent("Karthik", "REG003", 91),
            new SrmStudent("Meera", "REG004", 74),
            new SrmStudent("Suresh", "REG005", 60)
        };

        for (SrmStudent student : students) {
            String status = student.isEligible() ? "Eligible" : "Detained";
            System.out.println(student.name + " - " + student.attendance + "% - " + status);
        }

        double avg = SrmStudent.classAverage(students);
        System.out.printf("Class average: %.1f%%\n", avg);
    }
}

/*
JUSTIFICATION:
- isEligible() is an instance method because eligibility depends directly on an individual student's specific attendance instance variable.
- classAverage() is a static method because computing the overall average operates on an array of multiple students rather than relying on the state of a single SrmStudent object.
*/