public class Ticket {
    private String id;
    private double price;
    private double paid;

    public Ticket(String id, double price) {
        if (id == null || id.trim().isEmpty() || id.length() < 4) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.price = price;
        this.paid = 0;
    }

    public void pay(double amount) {
        this.paid += amount;
    }

    public double getBalanceDue() {
        return price - paid;
    }

    public String printTicket() {
        return "Standard Event Ticket | Balance Due: " + getBalanceDue();
    }

    public static String registerBatch(String[] ids, double price) {
        int registered = 0, rejected = 0;
        for (String id : ids) {
            try {
                new Ticket(id, price);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static String classifyGeneration(Ticket ticket) {
        if (ticket instanceof VIPWorkshopTicket) {
            return "Multilevel descendant (3 generations deep)";
        } else if (ticket instanceof CodingTicket) {
            return "Hierarchical sibling (independent branch)";
        } else if (ticket instanceof LabTicket) {
            return "Direct descendant (2 generations deep)";
        }
        return "Root class (1 generation)";
    }

    public static double getTotalBalanceDue(Ticket[] tickets) {
        double total = 0;
        for (Ticket ticket : tickets) {
            total += ticket.getBalanceDue();
        }
        return total;
    }
}

class LabTicket extends Ticket {
    private String domain;

    public LabTicket(String id, double price, String domain) {
        super(id, price);
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }

    @Override
    public String printTicket() {
        return "Workshop Ticket | Track: " + domain + " | Balance Due: " + getBalanceDue();
    }
}

class VIPWorkshopTicket extends LabTicket {
    private double kitCost;

    public VIPWorkshopTicket(String id, double price, String domain, double kitCost) {
        super(id, price, domain);
        this.kitCost = kitCost;
    }

    @Override
    public double getBalanceDue() {
        return super.getBalanceDue() + kitCost;
    }

    @Override
    public String printTicket() {
        return "Premium Workshop Ticket | Track: " + getDomain() + " | Kit Fee: " + kitCost + " | Balance Due: " + getBalanceDue();
    }
}

class CodingTicket extends Ticket {
    private String groupName;

    public CodingTicket(String id, double price, String groupName) {
        super(id, price);
        this.groupName = groupName;
    }

    @Override
    public String printTicket() {
        return "Hackathon Ticket | Team: " + groupName + " | Balance Due: " + getBalanceDue();
    }
}