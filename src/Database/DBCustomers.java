package Database;
/**This is the database customers class. Used to call information from the database. */
import Model.Customers;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DBCustomers {
    /**
     * method to call the list of customers from the database.
     * @return returns customers list
     * */
    public static ObservableList<Customers> getAllCustomer() {
        ObservableList<Customers> cxList = FXCollections.observableArrayList();
        //Make SQL Statement
        String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, first_level_divisions.Division_ID, first_level_divisions.Division, countries.Country FROM customers, " +
                "first_level_divisions, countries WHERE customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.Country_ID = countries.Country_ID";
        try {

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                //int countryId = rs.getInt("Country");

                Customers c = new Customers(customerId, customerName, address, postalCode, phone, divisionId);
                cxList.add(c);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cxList;
    }

    /**
     * method used to add customers to the customers table in the database.
     * */
    public static int insert(String customerName, String address, String postalCode,String phone, int divisionId) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        //ps.setInt(1, customerId);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        //ps.setInt(6, countryId);
        ps.setInt(5, divisionId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }
    /**
     * method to call to update customer information in the database.
     * */
    public static void update(int customerId, String customerName, String address, String postalCode, String phone, int divisionId) throws SQLException{
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionId);
        ps.setInt(6, customerId);

        ps.execute();

    }
/**
 * method to delete customers in the database.
 * */
    public static int delete(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }

    /**
     *
     * method to call customer information for the customer combo box.
     * @return returns customer list
     * */
    public static ObservableList<Customers> getTheCustomers() {
        ObservableList<Customers> cxList = FXCollections.observableArrayList();
        String sql = "SELECT Customer_Name, Customer_ID FROM customers";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String customerName = rs.getString("Customer_Name");
                int customerId = rs.getInt("Customer_ID");

                Customers cx = new Customers(customerId, customerName);
                System.out.println(cx.getCustomerName());
                cxList.add(cx);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return cxList;
    }

    /**method to count the number of customers in the customer table. */
    public static int countNumCx() throws SQLException {
        String sql = "SELECT COUNT(*) FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        int rowNumber = 0;
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            rowNumber = rs.getInt("COUNT(*)");
        }
        return rowNumber;
    }

}