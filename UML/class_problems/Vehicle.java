import java.util.*;

abstract class Vehicle {
    private String plate, model;
    private boolean available = true;
    public Vehicle(String plate, String model) { this.plate = plate; this.model = model; }
    public String getPlate() { return plate; }
    public String getModel() { return model; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public abstract double calculateCharge(int days);
}

class StandardCar extends Vehicle {
    public StandardCar(String plate, String model) { super(plate, model); }
    public double calculateCharge(int days) { return days * 50.0; }
}

class LuxuryCar extends Vehicle {
    public LuxuryCar(String plate, String model) { super(plate, model); }
    public double calculateCharge(int days) { return days * 100.0; }
}

class Customer {
    private String id, name;
    public Customer(String id, String name) { this.id = id; this.name = name; }
}

class Rental {
    private Customer customer;
    private Vehicle vehicle;
    private int days;
    private double totalCharge;

    public Rental(Customer customer, Vehicle vehicle, int days) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.days = days;
        this.totalCharge = vehicle.calculateCharge(days);
        this.vehicle.setAvailable(false);
    }

    public Vehicle getVehicle() { return vehicle; }
    public double getTotalCharge() { return totalCharge; }
    public void returnVehicle() { this.vehicle.setAvailable(true); }
}

class RentalService {
    private Map vehicles = new HashMap<>();
    private Map activeRentals = new HashMap<>();

    public void addVehicle(Vehicle v) { vehicles.put(v.getPlate(), v); }

    public Rental rentVehicle(Customer c, String plate, int days) {
        Vehicle v = vehicles.get(plate);
        if (v == null || !v.isAvailable()) throw new IllegalStateException("Vehicle unavailable.");
        Rental rental = new Rental(c, v, days);
        activeRentals.put(plate, rental);
        System.out.printf("%s rented for %d days. Total charge: $%.2f%n", v.getModel(), days, rental.getTotalCharge());
        return rental;
    }

    public void returnVehicle(String plate) {
        Rental rental = activeRentals.remove(plate);
        if (rental == null) throw new IllegalStateException("No active rental.");
        rental.returnVehicle();
        System.out.printf("%s returned. Now available.%n", rental.getVehicle().getModel());
    }
}

public class Main {
    public static void main(String[] args) {
        RentalService service = new RentalService();
        service.addVehicle(new LuxuryCar("LUX1", "Luxury Car A"));
        service.addVehicle(new StandardCar("STD1", "Standard Car B"));

        Customer c = new Customer("C1", "Alice");

        service.rentVehicle(c, "LUX1", 3);
        service.rentVehicle(c, "STD1", 5);
        service.returnVehicle("LUX1");
    }
}