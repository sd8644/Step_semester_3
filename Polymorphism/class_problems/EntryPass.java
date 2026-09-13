public class EntryPass {
    private String id;
    private double price;
    private double paid;

    public EntryPass(String id, double price) {
        if (id == null || id.trim().isEmpty() || id.length() < 4) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.price = price;
        this.paid = 0;
    }

    public double getBalanceDue() {
        return price - paid;
    }

    public String printTicket() {
        return "Standard | Balance: " + getBalanceDue();
    }

    public static String batchPrint(EntryPass[] passes) {
        StringBuilder sb = new StringBuilder();
        for (EntryPass pass : passes) {
            sb.append(pass.printTicket());
            if (pass instanceof ModulePass) {
                ModulePass modulePass = (ModulePass) pass;
                sb.append(" [Track via downcast: ").append(modulePass.getSubject()).append("]");
            }
            sb.append(" | ");
        }
        return sb.toString();
    }
}

class ModulePass extends EntryPass {
    private String subject;

    public ModulePass(String id, double price, String subject) {
        super(id, price);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public String printTicket() {
        return "Workshop | Track: " + subject + " | Balance: " + getBalanceDue();
    }
}