class ParkingSlot {
    String slotNo;
    int capacity;
    int occupiedCount;

    public ParkingSlot(String slotNo, int capacity, int occupiedCount) {
        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = occupiedCount;
    }

    public void allot(String vehicleNo) {
        if (occupiedCount < capacity) {
            occupiedCount++;
            System.out.println(vehicleNo + " allotted to slot " + slotNo);
        }
    }

    public static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        for (ParkingSlot slot : slots) {
            if (slot.occupiedCount < slot.capacity) {
                return slot;
            }
        }
        return null;
    }

    public static void safeAllot(ParkingSlot[] slots, String vehicleNo) {
        ParkingSlot availableSlot = findAvailableSlot(slots);
        if (availableSlot != null) {
            availableSlot.allot(vehicleNo);
        } else {
            System.out.println("No slots available for " + vehicleNo);
        }
    }
}

public class Main {
    /*
     * In Java, objects and arrays are managed by reference values passed by value. 
     * When passing a ParkingSlot array into these methods, Java copies only the memory 
     * reference pointing to the array (and its element references), not the underlying 
     * ParkingSlot objects. Therefore, modifications made directly via method calls 
     * operate on the original objects residing in memory.
     */
    public static void main(String[] args) {
        ParkingSlot[] slots1 = {
            new ParkingSlot("A1", 4, 3),
            new ParkingSlot("A2", 5, 5)
        };

        ParkingSlot.safeAllot(slots1, "TN09AB1234");

        ParkingSlot[] slots2 = {
            new ParkingSlot("A1", 4, 4),
            new ParkingSlot("A2", 5, 5)
        };

        ParkingSlot.safeAllot(slots2, "TN09AB1234");
    }
}