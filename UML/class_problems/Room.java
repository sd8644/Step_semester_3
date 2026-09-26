import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

abstract class Room {
    private String roomNumber;
    public Room(String roomNumber) { this.roomNumber = roomNumber; }
    public String getRoomNumber() { return roomNumber; }
    public abstract String getCategoryName();
    public abstract double calculatePrice(long nights);
}

class DeluxeRoom extends Room {
    public DeluxeRoom(String roomNumber) { super(roomNumber); }
    public String getCategoryName() { return "Deluxe Room"; }
    public double calculatePrice(long nights) { return nights * 200.0; }
}

class StandardRoom extends Room {
    public StandardRoom(String roomNumber) { super(roomNumber); }
    public String getCategoryName() { return "Standard Room"; }
    public double calculatePrice(long nights) { return nights * 150.0; }
}

class Customer {
    private String id, name;
    public Customer(String id, String name) { this.id = id; this.name = name; }
}

enum ReservationStatus { ACTIVE, CANCELLED }

class Reservation {
    private String id;
    private Room room;
    private Customer customer;
    private LocalDate checkIn, checkOut;
    private double totalPrice;
    private ReservationStatus status = ReservationStatus.ACTIVE;

    public Reservation(String id, Room room, Customer customer, LocalDate checkIn, LocalDate checkOut) {
        this.id = id;
        this.room = room;
        this.customer = customer;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        this.totalPrice = room.calculatePrice(nights);
    }

    public String getId() { return id; }
    public Room getRoom() { return room; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public double getTotalPrice() { return totalPrice; }
    public ReservationStatus getStatus() { return status; }

    public boolean overlapsWith(LocalDate start, LocalDate end) {
        return status == ReservationStatus.ACTIVE && checkIn.isBefore(end) && start.isBefore(checkOut);
    }

    public boolean cancel(LocalDate currentDate) {
        if (status == ReservationStatus.ACTIVE && currentDate.isBefore(checkIn)) {
            status = ReservationStatus.CANCELLED;
            return true;
        }
        return false;
    }
}

class HotelBookingManager {
    private Map rooms = new HashMap<>();
    private List reservations = new ArrayList<>();
    private int idCounter = 1;

    public void addRoom(Room room) { rooms.put(room.getRoomNumber(), room); }

    public Reservation bookRoom(Customer customer, String roomNumber, LocalDate checkIn, LocalDate checkOut) {
        Room room = rooms.get(roomNumber);
        if (room == null) return null;

        for (Reservation res : reservations) {
            if (res.getRoom().getRoomNumber().equals(roomNumber) && res.overlapsWith(checkIn, checkOut)) {
                System.out.printf("Booking failed: %s %s is not available for %s to %s.%n",
                        room.getCategoryName(), roomNumber, checkIn, checkOut);
                return null;
            }
        }

        Reservation res = new Reservation("RES" + (idCounter++), room, customer, checkIn, checkOut);
        reservations.add(res);
        System.out.printf("%s %s booked from %s to %s. Total price: $%.2f.%n",
                room.getCategoryName(), roomNumber, checkIn, checkOut, res.getTotalPrice());
        return res;
    }

    public void cancelReservation(String reservationId, LocalDate currentDate) {
        for (Reservation res : reservations) {
            if (res.getId().equals(reservationId)) {
                if (res.cancel(currentDate)) {
                    System.out.printf("Reservation for %s %s cancelled successfully.%n",
                            res.getRoom().getCategoryName(), res.getRoom().getRoomNumber());
                } else {
                    System.out.println("Cancellation failed: Past cancellation deadline or already cancelled.");
                }
                return;
            }
        }
        System.out.println("Reservation not found.");
    }
}

public class Main {
    public static void main(String[] args) {
        HotelBookingManager manager = new HotelBookingManager();
        manager.addRoom(new DeluxeRoom("101"));
        manager.addRoom(new StandardRoom("205"));

        Customer customer = new Customer("C1", "Alice");

        Reservation res1 = manager.bookRoom(customer, "101", LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 5));
        Reservation res2 = manager.bookRoom(customer, "205", LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 7));
        manager.bookRoom(customer, "101", LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 7));

        if (res1 != null) {
            manager.cancelReservation(res1.getId(), LocalDate.of(2024, 11, 30));
        }
    }
}