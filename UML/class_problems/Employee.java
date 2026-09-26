import java.time.LocalDate;
import java.util.*;

enum LeaveStatus { PENDING, APPROVED, REJECTED }

abstract class Employee {
    private String id, name;
    public Employee(String id, String name) { this.id = id; this.name = name; }
    public String getName() { return name; }
    public abstract String getType();
}

class FullTimeEmployee extends Employee {
    public FullTimeEmployee(String id, String name) { super(id, name); }
    public String getType() { return "Full-time"; }
}

class PartTimeEmployee extends Employee {
    public PartTimeEmployee(String id, String name) { super(id, name); }
    public String getType() { return "Part-time"; }
}

class LeaveRequest {
    private String id;
    private Employee employee;
    private LocalDate startDate, endDate;
    private LeaveStatus status = LeaveStatus.PENDING;

    public LeaveRequest(String id, Employee employee, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() { return id; }
    public Employee getEmployee() { return employee; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public LeaveStatus getStatus() { return status; }

    public void setStatus(LeaveStatus newStatus) {
        if (this.status != LeaveStatus.PENDING && newStatus == LeaveStatus.PENDING) {
            throw new IllegalStateException("Cannot change status: " + this.status + " request cannot revert to Pending.");
        }
        this.status = newStatus;
    }
}

class LeaveManager {
    private List requests = new ArrayList<>();
    private int idCounter = 1;

    public LeaveRequest submitLeaveRequest(Employee employee, LocalDate startDate, LocalDate endDate) {
        LeaveRequest request = new LeaveRequest("LR" + (idCounter++), employee, startDate, endDate);
        requests.add(request);
        System.out.printf("Leave request submitted by %s for %s to %s. Status: %s.%n",
                employee.getName(), startDate, endDate, request.getStatus());
        return request;
    }

    public void approveRequest(LeaveRequest request) {
        updateStatus(request, LeaveStatus.APPROVED, "approved");
    }

    public void rejectRequest(LeaveRequest request) {
        updateStatus(request, LeaveStatus.REJECTED, "rejected");
    }

    public void revertToPending(LeaveRequest request) {
        try {
            request.setStatus(LeaveStatus.PENDING);
            System.out.println("Status changed to Pending.");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateStatus(LeaveRequest request, LeaveStatus status, String actionText) {
        try {
            request.setStatus(status);
            System.out.printf("Leave request for %s %s. Status: %s.%n",
                    request.getEmployee().getName(), actionText, request.getStatus());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        LeaveManager manager = new LeaveManager();

        Employee john = new FullTimeEmployee("E1", "John Doe");
        Employee jane = new PartTimeEmployee("E2", "Jane Smith");

        LeaveRequest req1 = manager.submitLeaveRequest(john, LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 12));
        manager.approveRequest(req1);

        LeaveRequest req2 = manager.submitLeaveRequest(jane, LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 5));

        manager.revertToPending(req1);
    }
}