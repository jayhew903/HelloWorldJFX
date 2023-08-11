package Database;
/**This is the database contacts class. used to call information from the database. */
import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;


public class DBContacts {
    /**method to call all the contacts from the database.
     * @return returns the list of customers
     * */
    public static ObservableList<Contacts> getAllContacts(){
        ObservableList<Contacts> coList = FXCollections.observableArrayList();

        try{
            //My sql Statement
            //code to get the information from the SQL DB join table
            String sql = "SELECT Contact_ID, Contact_Name, Email FROM client_schedule.contacts";

            //create a PreparedStatement
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            //execute query and get results
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contacts c = new Contacts(contactId, contactName, email);
                coList.add(c);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

        return coList;
    }
    /**
     * method to get the customers from the database by the id and name.
     * @return returns the customer list.
     * */
    public static ObservableList<Contacts> getTheContacts() {
        ObservableList<Contacts> contactList = FXCollections.observableArrayList();
        String sql = "SELECT Contact_Name, Contact_ID FROM contacts";
        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String contactName = rs.getString("Contact_Name");
                int contactId = rs.getInt("Contact_ID");

                Contacts c = new Contacts(contactId, contactName);
                System.out.println(c.getContactName());
                contactList.add(c);

            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

        return contactList;
    }



}
