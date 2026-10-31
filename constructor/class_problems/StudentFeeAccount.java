class StudentFeeAccount {
    private String regNo;
    private double totalFee;
    private double amountPaid;

    public StudentFeeAccount(String regNo, double totalFee, double amountPaid) {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = amountPaid;
    }

    public void pay(double amount) {
        if (amount > 0) {
            this.amountPaid += amount;
        }
    }

    public double getDue() {
        return totalFee - amountPaid;
    }
}

class HostelAccount extends StudentFeeAccount {
    public HostelAccount(String regNo, double totalFee, double amountPaid) {
        super(regNo, totalFee, amountPaid);
    }
}

class DormRoom {
    String roomNo;
    int beds;
    int occupied;

    public DormRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    public boolean allot() {
        if (occupied < beds) {
            occupied++;
            return true;
        }
        return false;
    }
}

class CollegeStudent {
    String name;
    String regNo;
    HostelAccount feeAccount;
    DormRoom room;

    static int totalStudents = 0;

    public CollegeStudent(String name, String regNo, HostelAccount feeAccount) {
        this.name = name;
        this.regNo = regNo;
        this.feeAccount = feeAccount;
        this.room = null;
        totalStudents++;
    }

    public void assignRoom(DormRoom room) {
        if (room != null && room.allot()) {
            this.room = room;
        }
    }

    public String fullStatus() {
        String roomStr = (room != null) ? room.roomNo : "unallotted";
        return name + " | Due: Rs " + feeAccount.getDue() + " | Room: " + roomStr;
    }
}

public class ManagementSystem {
    public static void main(String[] args) {
        DormRoom room1 = new DormRoom("C-214", 3, 2);
        DormRoom room2 = new DormRoom("C-507", 2, 1);

        HostelAccount fee1 = new HostelAccount("RA01", 200000, 60000);
        HostelAccount fee2 = new HostelAccount("RA02", 180000, 0);
        HostelAccount fee3 = new HostelAccount("RA03", 200000, 0);

        fee1.pay(-5000); 
        fee2.pay(0);

        CollegeStudent s1 = new CollegeStudent("Ravi", "RA01", fee1);
        CollegeStudent s2 = new CollegeStudent("Anitha", "RA02", fee2);
        CollegeStudent s3 = new CollegeStudent("Karthik", "RA03", fee3);

        s1.assignRoom(room1);
        s2.assignRoom(room2);

        System.out.println(s1.fullStatus());
        System.out.println(s2.fullStatus());
        System.out.println(s3.fullStatus());
        System.out.println("Total students: " + CollegeStudent.totalStudents);
    }
}