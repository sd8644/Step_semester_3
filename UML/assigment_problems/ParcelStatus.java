import java.util.*;

enum ParcelStatus {
    BOOKED,
    PICKED_UP,
    IN_TRANSIT,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED;

    public ParcelStatus getNextStatus() {
        switch (this) {
            case BOOKED: return PICKED_UP;
            case PICKED_UP: return IN_TRANSIT;
            case IN_TRANSIT: return OUT_FOR_DELIVERY;
            case OUT_FOR_DELIVERY: return DELIVERED;
            default: return null;
        }
    }
}

interface ShippingType {
    String getName();
    double calculateCharge(double weightKg);
}

class StandardShipping implements ShippingType {
    @Override
    public String getName() {
        return "Standard";
    }

    @Override
    public double calculateCharge(double weightKg) {
        return 40.0 + (10.0 * weightKg);
    }
}

class ExpressShipping implements ShippingType {
    @Override
    public String getName() {
        return "Express";
    }

    @Override
    public double calculateCharge(double weightKg) {
        return 80.0 + (15.0 * weightKg);
    }
}

class FragileShipping implements ShippingType {
    private final StandardShipping baseShipping = new StandardShipping();

    @Override
    public String getName() {
        return "Fragile";
    }

    @Override
    public double calculateCharge(double weightKg) {
        return baseShipping.calculateCharge(weightKg) + 50.0;
    }
}

interface NotificationChannel {
    void sendUpdate(String trackingNumber, ParcelStatus newStatus);
}

class SmsChannel implements NotificationChannel {
    @Override
    public void sendUpdate(String trackingNumber, ParcelStatus newStatus) {
        System.out.println("[SMS] " + trackingNumber + " is now " + newStatus + ".");
    }
}

class EmailChannel implements NotificationChannel {
    @Override
    public void sendUpdate(String trackingNumber, ParcelStatus newStatus) {
        System.out.println("[Email] " + trackingNumber + " is now " + newStatus + ".");
    }
}

class Customer {
    private final String id;
    private final String name;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
}

class Parcel {
    private final String trackingNumber;
    private final double weightKg;
    private final Customer customer;
    private final ShippingType shippingType;
    private ParcelStatus status;
    private final List channels = new ArrayList<>();

    public Parcel(String trackingNumber, double weightKg, Customer customer, ShippingType shippingType) {
        this.trackingNumber = trackingNumber;
        this.weightKg = weightKg;
        this.customer = customer;
        this.shippingType = shippingType;
        this.status = ParcelStatus.BOOKED;
    }

    public String getTrackingNumber() { return trackingNumber; }
    public double getWeightKg() { return weightKg; }
    public ShippingType getShippingType() { return shippingType; }
    public ParcelStatus getStatus() { return status; }

    public void subscribeChannel(NotificationChannel channel) {
        if (!channels.contains(channel)) {
            channels.add(channel);
        }
    }

    public void notifyChannels() {
        for (NotificationChannel channel : channels) {
            channel.sendUpdate(trackingNumber, status);
        }
    }

    public double getCharge() {
        return shippingType.calculateCharge(weightKg);
    }

    public boolean updateStatus(ParcelStatus newStatus) {
        if (this.status == ParcelStatus.CANCELLED) {
            System.out.println("Invalid transition: Parcel is CANCELLED.");
            return false;
        }

        if (this.status.getNextStatus() == newStatus) {
            this.status = newStatus;
            notifyChannels();
            return true;
        } else {
            System.out.println("Invalid transition: " + this.status + " → " + newStatus + " is not allowed.");
            return false;
        }
    }

    public boolean cancel() {
        if (this.status == ParcelStatus.BOOKED) {
            this.status = ParcelStatus.CANCELLED;
            System.out.println("Parcel " + trackingNumber + " has been cancelled.");
            notifyChannels();
            return true;
        } else {
            System.out.println("Cancellation failed: " + trackingNumber + " can be cancelled only while BOOKED.");
            return false;
        }
    }
}

class ParcelService {
    private final Map parcels = new HashMap<>();

    public Parcel bookParcel(String trackingNumber, double weightKg, Customer customer, ShippingType shippingType, List channels) {
        Parcel parcel = new Parcel(trackingNumber, weightKg, customer, shippingType);
        for (NotificationChannel channel : channels) {
            parcel.subscribeChannel(channel);
        }
        parcels.put(trackingNumber, parcel);

        System.out.printf("Parcel %s booked (%s, %.0f kg). Charge: ₹%.2f.%n",
                trackingNumber, shippingType.getName(), weightKg, parcel.getCharge());
        
        parcel.notifyChannels();
        return parcel;
    }

    public Parcel getParcel(String trackingNumber) {
        return parcels.get(trackingNumber);
    }
}

public class SwiftShipParcelTracker {
    public static void main(String[] args) {
        ParcelService service = new ParcelService();
        Customer customer = new Customer("C100", "Alice");

        List channels = Arrays.asList(new SmsChannel(), new EmailChannel());

        Parcel p101 = service.bookParcel("P101", 2.0, customer, new ExpressShipping(), channels);

        p101.updateStatus(ParcelStatus.PICKED_UP);

        p101.cancel();

        p101.updateStatus(ParcelStatus.IN_TRANSIT);

        p101.updateStatus(ParcelStatus.DELIVERED);
    }
}