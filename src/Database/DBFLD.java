package Database;
/**This is the database First Level Division class. Used to obtain information in the database. */
import Model.FirstLevelDivisions;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class DBFLD {
    /**
     * method to obtain all FLD information from the database.
     * @return returns FLD list
     * */
    public static ObservableList<FirstLevelDivisions> getAllDivisions() {
        ObservableList<FirstLevelDivisions> divisionList = FXCollections.observableArrayList();
        String sql = "SELECT Division_ID, Division, Country_ID FROM first_level_divisions";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");


                FirstLevelDivisions f = new FirstLevelDivisions(divisionId, division, countryId);
                System.out.println(f.getDivision());
                divisionList.add(f);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return divisionList;
    }
/**
 * Method to filter the divisons in the combo box based on the country ID selected
 * Lamba expression to filter by the country Id in the division.
 * @return returns the filtered first level division list.
 * */
    public static ObservableList<FirstLevelDivisions> getCountryDivisions(int countryId) {
        ObservableList<FirstLevelDivisions> divisionList = getAllDivisions().stream()
                .filter(d -> d.getCountryId() == countryId)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        return divisionList;
    }
/**
 * method to call to collect the division of the selected customer to be displayed in the combo box.
 * @return returns the division based on the selected division id.
 * */
    public static FirstLevelDivisions getDivisionById(int divisionId) {
        ObservableList<FirstLevelDivisions> divisionList = getAllDivisions().stream()
                .filter(d -> d.getDivisionId() == divisionId)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        return divisionList.get(0);
    }
}
