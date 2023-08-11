package Database;
/**This is the database appointment class. Used to call information from the database. */
import Model.Appointments;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.management.Query;
import java.sql.*;
import java.time.*;

public abstract class DBAppointment {
/**
 * This provides all the appointments in the appointments table in the database.
 * the SQL statement calls the selected columns to be used in the appointments table.
 * @return returns the appointment list
 * */
    public static ObservableList<Appointments> getAllAppointments() {
        ObservableList<Appointments> aptList = FXCollections.observableArrayList();
        String sql = "SELECT Appointment_ID, Title, Description, Location, contacts.Contact_ID, Type, Start, End, Customer_ID, User_ID FROM client_schedule.appointments, client_schedule.contacts " +
                "WHERE appointments.Contact_ID = contacts.Contact_ID";
        try {
            //mySQL statement to get all the info on the table


            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contact = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                //Timestamp t1 = Timestamp.valueOf(start);
                //ZonedDateTime zdt = start.atZone(ZoneId.of("American/New_York"));
                //ZonedDateTime zdtlocal = zdt.withZoneSameInstant(ZoneId.systemDefault());

                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");

                Appointments a = new Appointments(appointmentId, title, description, location, contact, type, start, end, customerId, userId);
                System.out.println(a.getAppointmentId());
                aptList.add(a);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return aptList;
    }
/**
 * Allows us to call to add data into the appointments table in the database
 * */
    public static int insert(String title, String description, String location, String type, int contactId, int customerId, int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Contact_ID, Customer_ID, User_ID, Start, End)  VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setInt(5, contactId);
        ps.setInt(6, customerId);
        ps.setInt(7, userId);
        ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.of(startDate, startTime)));
        ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.of(endDate, endTime)));

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }
/**
 * This is to call to be able to update appointment information in the appointment table in the database.
 * */
    public static int update(int appointmentId, String title, String description, String location, String type, int contactId, int customerId, int userId, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Contact_ID = ?, Customer_ID = ?, User_ID = ?, Start = ?, End = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setInt(5, contactId);
        ps.setInt(6, customerId);
        ps.setInt(7, userId);
        ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.of(startDate, startTime)));
        ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.of(endDate, endTime)));
        ps.setInt(10, appointmentId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }
/**
 * This is to call to be able to delete appointments in the appointments table in the database.
 * */
    public static int delete(int appointmentId) throws SQLException {

        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;


    }
    /**
     * This is to call to delete the appointment ID associated with a specific customer.
     * */

    public static int deleteAptCxId(int customerId, int appointmentId) throws SQLException {
        String sql = "DELETE FROM appointments  WHERE Customer_ID = ? AND Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ps.setInt(2, appointmentId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

/**
 * To call to display the appointments filtered by the current month.
 * @return returns the appointment list basted on the current month.
 * */
    public static ObservableList<Appointments> getMonthCurrent() throws SQLException {
        ObservableList<Appointments> currentMonthApt = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Month(Start) = Month(CURDATE())";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            int contact = rs.getInt("Contact_ID");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");

            Appointments appointments = new Appointments(appointmentId, title, description, location, contact, type, start, end, customerId, userId);
            currentMonthApt.add(appointments);
        }
        return currentMonthApt;
    }
    /**
     * To call to display the appointments filtered by the current week.
     * @return appointments filtered by the current week.
     * */

    public static ObservableList<Appointments> getWeekCurrent() throws SQLException {
        ObservableList<Appointments> currentWeekApt = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Week(Start) = Week(CURDATE())";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            int contact = rs.getInt("Contact_ID");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");

            Appointments appointments = new Appointments(appointmentId, title, description, location, contact, type, start, end, customerId, userId);
            currentWeekApt.add(appointments);
        }
        return currentWeekApt;

    }
/**
 * creates a list of the types listed in the appointment tables
 * @return list of types
 * */
    public static ObservableList<Appointments> getAptType() {
        ObservableList<Appointments> aptTypeList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Type FROM appointments";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("Type");
                Appointments a = new Appointments(type);
                aptTypeList.add(a);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return aptTypeList;
    }
    /**
     * counts the number of appointments based on selected type and month.
     * @return returns the count of these filtered appointments
     * */

        public static int aptTypeMonth(String selectedMonth, Appointments selectedType) throws SQLException {
        ObservableList<Appointments> aptType = FXCollections.observableArrayList();
        String sql = "SELECT COUNT(distinct Type) AS monthType FROM appointments WHERE monthname(Start) = '" + selectedMonth + "' AND Type = '" + selectedType + "'";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        int countTpyeMonth = 0;
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            countTpyeMonth = rs.getInt("monthType");
            return countTpyeMonth;
        }
        return countTpyeMonth;
    }
    /**
     * List of appointments in the database filtered by the contact Id.
     * Lamba used to filter the data by the contact Id.
     * */
    public static ObservableList<Appointments> getAptContact(int selectedContactId) throws SQLException {
        ObservableList<Appointments> allApts = getAllAppointments();
        ObservableList<Appointments> contactApts = allApts.filtered(a -> {
            if (a.getContactId() == selectedContactId) {
                return true;
            }
            return false;
        });
        return contactApts;
    }

    /**
     * used to count the number of appointments in the database.
     * */
    public static int countNumApt() throws SQLException {
        String sql = "SELECT COUNT(*) FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        int rowNumber = 0;
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            rowNumber = rs.getInt("COUNT(*)");
        }
        return rowNumber;
    }
}

