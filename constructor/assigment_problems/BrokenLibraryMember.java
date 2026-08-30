class BrokenLibraryMember {
    static String name;
    static String memberId;
    static int booksIssued;

    public BrokenLibraryMember(String name, String memberId, int booksIssued) {
        BrokenLibraryMember.name = name;
        BrokenLibraryMember.memberId = memberId;
        BrokenLibraryMember.booksIssued = booksIssued;
    }

    public void printMemberCard() {
        System.out.println(name);
    }
}

class FixedLibraryMember {
    String name;
    String memberId;
    int booksIssued;

    static String libraryName = "Central Library";
    static int memberCount = 1000;

    public FixedLibraryMember(String name, int booksIssued) {
        this.name = name;
        this.booksIssued = booksIssued;
        memberCount++;
        this.memberId = "LM-" + memberCount;
    }

    public void printMemberCard() {
        System.out.println(name + " | " + memberId);
    }

    public static void printTotalMembers() {
        System.out.println("Total members: " + (memberCount - 1000));
    }
}

public class Main {
    /*
     * Why marking name, memberId, and booksIssued as static is wrong:
     * 1. name: Shared across the class. Setting a new member's name overwrites 
     *    the name for all existing members.
     * 2. memberId: Must uniquely identify a single individual. Making it static 
     *    means every member shares the exact same ID.
     * 3. booksIssued: Tracks an individual's borrowing count. Making it static 
     *    causes one member's activity to mutate the counter for every member.
     * 
     * libraryName and memberCount belong as static fields because libraryName is 
     * shared globally across all members, and memberCount tracks system-wide totals.
     */
    public static void main(String[] args) {
        System.out.println("--- Broken Version ---");
        BrokenLibraryMember m1 = new BrokenLibraryMember("Aditi", "LM-1001", 2);
        BrokenLibraryMember m2 = new BrokenLibraryMember("Rohan", "LM-1002", 1);
        
        m1.printMemberCard();
        m2.printMemberCard();

        System.out.println("\n--- Fixed Version ---");
        FixedLibraryMember fixed1 = new FixedLibraryMember("Aditi", 2);
        FixedLibraryMember fixed2 = new FixedLibraryMember("Rohan", 1);

        fixed1.printMemberCard();
        fixed2.printMemberCard();
        FixedLibraryMember.printTotalMembers();
    }
}