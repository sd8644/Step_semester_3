public abstract class LibraryItem {
    private static int counter = 1001;
    private final String itemId;

    public LibraryItem() {
        this.itemId = "ITEM-" + counter++;
    }

    public String getItemId() {
        return itemId;
    }

    public abstract int getLoanPeriodDays();
}

interface Renewable {
    String renew();
}

interface Reservable {
    String reserve();
}

class Textbook extends LibraryItem implements Renewable, Reservable {
    private final String title;

    public Textbook(String title) {
        this.title = title;
    }

    @Override
    public int getLoanPeriodDays() {
        return 14;
    }

    @Override
    public String renew() {
        return title + " renewed";
    }

    @Override
    public String reserve() {
        return title + " reserved";
    }
}

class Magazine extends LibraryItem implements Renewable {
    private final String title;

    public Magazine(String title) {
        this.title = title;
    }

    @Override
    public int getLoanPeriodDays() {
        return 7;
    }

    @Override
    public String renew() {
        return title + " renewed";
    }
}

class DigitalPass implements Renewable {
    private final String resourceName;

    public DigitalPass(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String renew() {
        return resourceName + " renewed";
    }
}

class Main {
    public static void processCheckouts(LibraryItem[] items) {
        for (LibraryItem item : items) {
            System.out.println(item.getLoanPeriodDays());
        }
    }

    public static String reserveIfSupported(Object o) {
        if (o instanceof Reservable) {
            return ((Reservable) o).reserve();
        }
        return "Reservation not supported";
    }

    public static void main(String[] args) {
        Textbook t = new Textbook("Java Fundamentals");
        System.out.println(t.getLoanPeriodDays());
        System.out.println(t.renew());
        System.out.println(t.reserve());

        Magazine m = new Magazine("Tech Monthly");
        System.out.println(reserveIfSupported(m));

        DigitalPass d = new DigitalPass("E-Journal Access");
        System.out.println(reserveIfSupported(d));

        LibraryItem ref = t;
        System.out.println(reserveIfSupported(ref));
    }
}