import java.sql.*;
import java.util.Scanner;

public class TransportationManagementSystem {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/transport_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection connection;
    private Scanner scanner;

    public TransportationManagementSystem() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Database connected successfully.");
            scanner = new Scanner(System.in); // Initialize Scanner once
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addTransportationRecord() {
        try {
            System.out.print("Enter Vehicle Number: ");
            String vehicleNumber = scanner.nextLine();

            System.out.print("Enter Driver Name: ");
            String driverName = scanner.nextLine();

            System.out.print("Enter Route: ");
            String route = scanner.nextLine();

            System.out.print("Enter Departure Time (HH:MM:SS): ");
            String departureTime = scanner.nextLine();

            System.out.print("Enter Arrival Time (HH:MM:SS): ");
            String arrivalTime = scanner.nextLine();

            String query = "INSERT INTO transportation (vehicle_number, driver_name, route, departure_time, arrival_time) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, vehicleNumber);
            preparedStatement.setString(2, driverName);
            preparedStatement.setString(3, route);
            preparedStatement.setTime(4, Time.valueOf(departureTime));
            preparedStatement.setTime(5, Time.valueOf(arrivalTime));

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Record added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTransportationRecord() {
        try {
            System.out.print("Enter ID of the record to update: ");
            int id = Integer.parseInt(scanner.nextLine()); // Use nextLine() and parse to avoid input issues

            System.out.print("Enter New Vehicle Number: ");
            String vehicleNumber = scanner.nextLine();

            System.out.print("Enter New Driver Name: ");
            String driverName = scanner.nextLine();

            System.out.print("Enter New Route: ");
            String route = scanner.nextLine();

            System.out.print("Enter New Departure Time (HH:MM:SS): ");
            String departureTime = scanner.nextLine();

            System.out.print("Enter New Arrival Time (HH:MM:SS): ");
            String arrivalTime = scanner.nextLine();

            String query = "UPDATE transportation SET vehicle_number=?, driver_name=?, route=?, departure_time=?, arrival_time=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, vehicleNumber);
            preparedStatement.setString(2, driverName);
            preparedStatement.setString(3, route);
            preparedStatement.setTime(4, Time.valueOf(departureTime));
            preparedStatement.setTime(5, Time.valueOf(arrivalTime));
            preparedStatement.setInt(6, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Record updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewTransportationRecords() {
        try {
            String query = "SELECT * FROM transportation";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("ID | Vehicle Number | Driver Name | Route | Departure Time | Arrival Time");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String vehicleNumber = resultSet.getString("vehicle_number");
                String driverName = resultSet.getString("driver_name");
                String route = resultSet.getString("route");
                Time departureTime = resultSet.getTime("departure_time");
                Time arrivalTime = resultSet.getTime("arrival_time");

                System.out.printf("%d | %s | %s | %s | %s | %s%n", id, vehicleNumber, driverName, route, departureTime, arrivalTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTransportationRecord() {
        try {
            System.out.print("Enter ID of the record to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            String query = "DELETE FROM transportation WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Record deleted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void menu() {
        while (true) {
            System.out.println("\nTransportation Management System");
            System.out.println("1. Add Transportation Record");
            System.out.println("2. Update Transportation Record");
            System.out.println("3. View Transportation Records");
            System.out.println("4. Delete Transportation Record");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextLine()) {
                String input = scanner.nextLine();

                try {
                    int choice = Integer.parseInt(input);

                    switch (choice) {
                        case 1:
                            addTransportationRecord();
                            break;
                        case 2:
                            updateTransportationRecord();
                            break;
                        case 3:
                            viewTransportationRecords();
                            break;
                        case 4:
                            deleteTransportationRecord();
                            break;
                        case 5:
                            System.out.println("Exiting program.");
                            scanner.close();
                            return;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            } else {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    public static void main(String[] args) {
        TransportationManagementSystem system = new TransportationManagementSystem();
        system.menu();
    }
}
