package Database;
/**This is the database countries class. Used to call information from the database. */
import Model.Countries;
import Model.Customers;
import Model.Users;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class DBCountries {
    /**
     * method to call all the countries from the database
     * @return returns list of all countries
     * */
    public static ObservableList<Countries> getAllCountries() {
        ObservableList<Countries> clist = FXCollections.observableArrayList();
        try {
            //my SQL statement
            String sql = "SELECT * from countries";
            //create a prepared statement
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            //execute query to get results
            ResultSet rs = ps.executeQuery();
            //work through result set, one row at a time

            while (rs.next()) {
                //get results data
                int countryID = rs.getInt("Country ID");
                String countryName = rs.getString("Country");
                Timestamp createDate = Timestamp.valueOf(rs.getTimestamp("Create Date").toLocalDateTime());
                String createdBy = rs.getString("Created By");
                Timestamp lastUpdate = Timestamp.valueOf(rs.getTimestamp("Last Update").toLocalDateTime());
                String lastUpdatedBy = rs.getString("Last Updated By");

                Countries C = new Countries(countryID, countryName, createDate.toLocalDateTime(), createdBy, lastUpdate.toLocalDateTime(), lastUpdatedBy);
                clist.add(C); //add new country to the list
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); //always print stack trace unless purposely hiding exceptions
        }

        return clist; //return the list
    }
/**
 * method to obtain countries from the database based on name and Id.
 * @return returns country list
 * */
    public static ObservableList<Countries> getAllCountriesId() {
        ObservableList<Countries> countryList = FXCollections.observableArrayList();
        String sql = "SELECT Country, Country_ID FROM countries";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String countryName = rs.getString("Country");
                int countryId = rs.getInt("Country_ID");

                Countries c = new Countries(countryId, countryName);
                countryList.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return countryList;
    }

/**
 * method to obtain the country Id based on the selected customer information.
 * returns filtered country Id.
 *
 * */
    public static Countries getCountry(int countryId) {
        String sql = "SELECT Country, Country_ID FROM countries WHERE Country_ID = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, countryId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String countryName = rs.getString("Country");


                Countries c = new Countries(countryId,countryName);
                System.out.println(c.getCountryName());

                return c;

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
/**
 * method to call the division based on the selected division Id.
 * @return list of division Id based on country Id
 * */
    public static Countries getCountryByDivision(int divisionId) {
        String sql = "SELECT c.Country, c.Country_ID FROM countries as c " +
                "INNER JOIN First_Level_Divisions as d " +
                "ON c.Country_ID = d.Country_ID AND d.Division_ID = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, divisionId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String countryName = rs.getString("Country");
                int countryId = rs.getInt("Country_ID");

                Countries c = new Countries(countryId,countryName);
                System.out.println(c.getCountryName());
                return c;

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
