class BookIssue {
    String title;
    String borrowerName;
    int daysOverdue;

    public BookIssue(String title, String borrowerName, int daysOverdue) {
        this.title = title;
        this.borrowerName = borrowerName;
        this.daysOverdue = daysOverdue;
    }

    public double fineAmount() {
        if (daysOverdue > 0) {
            return daysOverdue * 5.0;
        }
        return 0.0;
    }

    public boolean isSeverelyOverdue() {
        return daysOverdue > 14;
    }

    public static double totalFineCollected(BookIssue[] issues) {
        double total = 0.0;
        for (BookIssue issue : issues) {
            total += issue.fineAmount();
        }
        return total;
    }
}

public class Main {
    /*
     * totalFineCollected is static because it computes an aggregate fine across 
     * a collection of multiple books, operating as a utility on the class level 
     * without relying on the instance state of any single BookIssue object. 
     * 
     * Conversely, fineAmount is an instance method because the calculated fine 
     * depends directly on the specific daysOverdue property unique to that 
     * individual BookIssue instance.
     */
    public static void main(String[] args) {
        BookIssue[] issues = {
            new BookIssue("Clean Code", "Alice", 18),
            new BookIssue("Effective Java", "Bob", 5),
            new BookIssue("Refactoring", "Charlie", 0),
            new BookIssue("DSA Handbook", "David", 21),
            new BookIssue("Design Patterns", "Eve", 9)
        };

        for (BookIssue issue : issues) {
            String status = issue.isSeverelyOverdue() ? "Severely overdue" : "OK";
            System.out.println(issue.title + " - " + issue.daysOverdue + " days - " + status);
        }

        System.out.println("Total fine collected: Rs " + BookIssue.totalFineCollected(issues));
    }
}