class HostelRoom {
    String roomNo;
    int beds;
    int occupied;

    public HostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    public void allot(String name) {
        if (occupied < beds) {
            occupied++;
            System.out.println(name + " allotted to room " + roomNo);
        }
    }
}

public class Main {
    public static HostelRoom findAvailableRoom(HostelRoom[] rooms) {
        if (rooms == null) return null;
        for (HostelRoom room : rooms) {
            if (room != null && room.occupied < room.beds) {
                return room;
            }
        }
        return null;
    }

    public static void safeAllot(HostelRoom[] rooms, String studentName) {
        HostelRoom room = findAvailableRoom(rooms);
        if (room != null) {
            room.allot(studentName);
        } else {
            System.out.println("No rooms available for " + studentName);
        }
    }

    public static void main(String[] args) {
        HostelRoom[] roomsPass = new HostelRoom[] {
            new HostelRoom("C-214", 3, 2),
            new HostelRoom("C-507", 2, 2)
        };
        safeAllot(roomsPass, "Divya");

        HostelRoom[] roomsFail = new HostelRoom[] {
            new HostelRoom("C-214", 3, 3),
            new HostelRoom("C-507", 2, 2)
        };
        safeAllot(roomsFail, "Divya");
    }
}

/*
JUSTIFICATION:
In Java, array variables and object variables store reference values (memory addresses pointing to the actual objects on the heap), not the actual objects themselves. 
When passing a HostelRoom array to safeAllot or findAvailableRoom, Java passes a copy of the reference to the array. 
The array elements within the parameter still point directly to the exact same HostelRoom instances in memory. 
Therefore, mutating an object via a reference inside the method alters the original object without creating a duplicate copy.
*/