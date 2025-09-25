import java.util.*;

class Room {
    int id;
    String category;
    double price;
    boolean available;

    Room(int id, String category, double price) {
        this.id = id;
        this.category = category;
        this.price = price;
        this.available = true;
    }
}

class Reservation {
    int reservationId;
    String customerName;
    Room room;

    Reservation(int reservationId, String customerName, Room room) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.room = room;
    }
}

class Hotel {
    private List<Room> rooms = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();
    private int reservationCounter = 1;

    void addRoom(Room room) {
        rooms.add(room);
    }

    void showAvailableRooms() {
        System.out.println("\n=== Available Rooms ===");
        for (Room r : rooms) {
            if (r.available) {
                System.out.println("Room ID: " + r.id + " | " + r.category + " | Price: " + r.price);
            }
        }
    }

    void bookRoom(String customer, int roomId) {
        for (Room r : rooms) {
            if (r.id == roomId && r.available) {
                r.available = false;
                Reservation res = new Reservation(reservationCounter++, customer, r);
                reservations.add(res);
                System.out.println("Booking successful! Reservation ID: " + res.reservationId);
                simulatePayment(r.price);
                return;
            }
        }
        System.out.println("Room not available!");
    }

    void cancelReservation(int reservationId) {
        for (Reservation res : reservations) {
            if (res.reservationId == reservationId) {
                res.room.available = true;
                reservations.remove(res);
                System.out.println("Reservation " + reservationId + " cancelled.");
                return;
            }
        }
        System.out.println("Reservation ID not found!");
    }

    void viewReservations() {
        System.out.println("\n=== All Reservations ===");
        for (Reservation res : reservations) {
            System.out.println("Reservation ID: " + res.reservationId +
                               " | Customer: " + res.customerName +
                               " | Room: " + res.room.category +
                               " | Price: " + res.room.price);
        }
    }

    private void simulatePayment(double amount) {
        System.out.println("Processing payment of $" + amount + "...");
        System.out.println("Payment successful!");
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel hotel = new Hotel();

        // Add some rooms
        hotel.addRoom(new Room(101, "Standard", 1000));
        hotel.addRoom(new Room(102, "Deluxe", 2000));
        hotel.addRoom(new Room(103, "Suite", 3000));

        while (true) {
            System.out.println("\n=== Hotel Reservation System ===");
            System.out.println("1. Show Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Reservations");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();

            if (choice == 1) {
                hotel.showAvailableRooms();
            } else if (choice == 2) {
                sc.nextLine(); // consume newline
                System.out.print("Enter customer name: ");
                String name = sc.nextLine();
                System.out.print("Enter Room ID to book: ");
                int roomId = sc.nextInt();
                hotel.bookRoom(name, roomId);
            } else if (choice == 3) {
                System.out.print("Enter Reservation ID to cancel: ");
                int resId = sc.nextInt();
                hotel.cancelReservation(resId);
            } else if (choice == 4) {
                hotel.viewReservations();
            } else if (choice == 5) {
                break;
            } else {
                System.out.println("Invalid option!");
            }
        }
        sc.close();
    }
}
