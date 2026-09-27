import java.util.*;

interface CreditPolicy {
    String getTypeName();
    int getMaxCredits();
}

class RegularCreditPolicy implements CreditPolicy {
    public String getTypeName() { return "Regular"; }
    public int getMaxCredits() { return 24; }
}

class HonorsCreditPolicy implements CreditPolicy {
    public String getTypeName() { return "Honors"; }
    public int getMaxCredits() { return 28; }
}

class ExchangeCreditPolicy implements CreditPolicy {
    public String getTypeName() { return "Exchange"; }
    public int getMaxCredits() { return 20; }
}

class Student {
    private final String id;
    private final String name;
    private final CreditPolicy creditPolicy;
    private int currentCredits;

    public Student(String id, String name, int currentCredits, CreditPolicy creditPolicy) {
        this.id = id;
        this.name = name;
        this.currentCredits = currentCredits;
        this.creditPolicy = creditPolicy;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getCurrentCredits() { return currentCredits; }
    public CreditPolicy getCreditPolicy() { return creditPolicy; }

    public boolean canAddCredits(int credits) {
        return (currentCredits + credits) <= creditPolicy.getMaxCredits();
    }

    public void addCredits(int credits) { currentCredits += credits; }
    public void deductCredits(int credits) { currentCredits -= credits; }
}

class Elective {
    private final String name;
    private final int credits;
    private final int capacity;
    private final List enrolledStudents = new ArrayList<>();
    private final Queue waitlist = new LinkedList<>();

    public Elective(String name, int credits, int capacity) {
        this.name = name;
        this.credits = credits;
        this.capacity = capacity;
    }

    public String getName() { return name; }
    public int getCredits() { return credits; }

    public boolean isEnrolled(Student student) { return enrolledStudents.contains(student); }
    public boolean isWaitlisted(Student student) { return waitlist.contains(student); }

    public void enrollOrWaitlist(Student student) {
        if (isEnrolled(student) || isWaitlisted(student)) {
            System.out.println("Enrollment failed: " + student.getName() + " is already enrolled or waitlisted.");
            return;
        }

        if (!student.canAddCredits(credits)) {
            System.out.printf("Enrollment failed: %s would exceed the %s credit limit (%d/%d).%n",
                    student.getName(), student.getCreditPolicy().getTypeName(),
                    student.getCurrentCredits() + credits, student.getCreditPolicy().getMaxCredits());
            return;
        }

        if (enrolledStudents.size() < capacity) {
            enrolledStudents.add(student);
            student.addCredits(credits);
            System.out.printf("%s enrolled in %s (credits: %d/%d).%n",
                    student.getName(), name, student.getCurrentCredits(), student.getCreditPolicy().getMaxCredits());
            if (enrolledStudents.size() == capacity) {
                System.out.println(name + " is full.");
            }
        } else {
            waitlist.add(student);
            System.out.printf("%s added to waitlist (position %d).%n", student.getName(), waitlist.size());
        }
    }

    public void drop(Student student) {
        if (!enrolledStudents.contains(student)) {
            System.out.println("Drop failed: " + student.getName() + " is not enrolled in " + name + ".");
            return;
        }

        enrolledStudents.remove(student);
        student.deductCredits(credits);
        System.out.printf("%s dropped %s (credits: %d/%d).%n",
                student.getName(), name, student.getCurrentCredits(), student.getCreditPolicy().getMaxCredits());

        promoteWaitlistedStudent();
    }

    private void promoteWaitlistedStudent() {
        while (!waitlist.isEmpty() && enrolledStudents.size() < capacity) {
            Student nextStudent = waitlist.poll();
            if (nextStudent.canAddCredits(credits)) {
                enrolledStudents.add(nextStudent);
                nextStudent.addCredits(credits);
                System.out.printf("%s promoted from waitlist and enrolled in %s (credits: %d/%d).%n",
                        nextStudent.getName(), name, nextStudent.getCurrentCredits(), nextStudent.getCreditPolicy().getMaxCredits());
                break;
            } else {
                System.out.printf("Promotion failed for %s: Credit limit exceeded (%d/%d).%n",
                        nextStudent.getName(), nextStudent.getCurrentCredits() + credits, nextStudent.getCreditPolicy().getMaxCredits());
            }
        }
    }
}

public class ElectiveSeatRush {
    public static void main(String[] args) {
        CreditPolicy regular = new RegularCreditPolicy();
        CreditPolicy honors = new HonorsCreditPolicy();
        CreditPolicy exchange = new ExchangeCreditPolicy();

        Elective cloudComputing = new Elective("Cloud Computing", 4, 2);

        Student asha = new Student("S1", "Asha", 20, regular);
        Student ravi = new Student("S2", "Ravi", 22, honors);
        Student neha = new Student("S3", "Neha", 12, exchange);
        Student kiran = new Student("S4", "Kiran", 22, regular);

        cloudComputing.enrollOrWaitlist(asha);
        cloudComputing.enrollOrWaitlist(ravi);
        cloudComputing.enrollOrWaitlist(neha);
        cloudComputing.enrollOrWaitlist(kiran);
        cloudComputing.drop(asha);
    }
}