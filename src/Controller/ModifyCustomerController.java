package Controller;
/**Controller class for the modify customers GUI. */
import Database.DBCountries;
import Database.DBCustomers;
import Database.DBFLD;
import Model.Countries;
import Model.Customers;
import Model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {
    /**
     * customer address text field
     * */
    @FXML
    private TextField cxAddressTxt;
    /**
     * cancel button
     * */
    @FXML
    private Button cxCancelBtn;
    /**
     * countries combo box.
     * */
    @FXML
    private ComboBox<Countries> cxCountryCB;
    /**
     * customer ID text field
     * this is disabled and cannot be modified
     * */
    @FXML
    private TextField cxIdTxt;
    /**
     * customer name text field
     * */
    @FXML
    private TextField cxNameTxt;
    /**
     * customer phone number text field.
     * */
    @FXML
    private TextField cxPhoneTxt;
    /**
     * customer postal code text field.
     * */
    @FXML
    private TextField cxPostalCodeTxt;
    /**
     * FLD combo box
     * */
    @FXML
    private ComboBox<FirstLevelDivisions> cxStateCB;
    /**
     * save button.
     * */
    @FXML
    private Button saveCxBtn;
    /**
     * collects customer data from the selected customer.
     * this displays that collect information in the correct location.
     * */
    public void setCx(int index, Customers customers) {
        cxIdTxt.setText(String.valueOf(customers.getCustomerId()));
        cxNameTxt.setText(customers.getCustomerName());
        cxAddressTxt.setText(customers.getAddress());
        cxPostalCodeTxt.setText(customers.getPostalCode());
        cxPhoneTxt.setText(customers.getPhone());
        Countries c = DBCountries.getCountryByDivision(customers.getDivisionId());
        cxCountryCB.setValue(c);
        cxStateCB.setItems(DBFLD.getCountryDivisions(c.getCountryId()));
        cxStateCB.setValue(DBFLD.getDivisionById(customers.getDivisionId()));
    }

    /**
     * cancel button
     * selecting this will direct user to the customer page
     * this does not save any modified information
     * warning will appear if selected.
     * @param event
     * @throws IOException
     * */
    @FXML
    void cancelCx(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setContentText("Cancelling will not save any modified data");
        alert.showAndWait();

        Parent root = FXMLLoader.load(getClass().getResource("../View/AptSchedulerCxView.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    /**
     * save button
     * selecting this will save the updated information to the database
     * checks to ensure all options are selected and errors occur if information is missing.
     * @param event
     * @throws IOException
     * */
    @FXML
    void saveCx(ActionEvent event) throws IOException {
        try {
            int customerId = Integer.parseInt(cxIdTxt.getText());
            String customerName = cxNameTxt.getText();
            String address = cxAddressTxt.getText();
            String postalCode = cxPostalCodeTxt.getText();
            String phone = cxPhoneTxt.getText();
            Countries countries = cxCountryCB.getValue();
            FirstLevelDivisions firstLevelDivisions = cxStateCB.getValue();

            if (countries == null)
                return;
            if (firstLevelDivisions == null)
                return;

            DBCustomers.update(customerId, customerName, address, postalCode, phone, firstLevelDivisions.getDivisionId());
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        Parent root = FXMLLoader.load(getClass().getResource("../View/AptSchedulerCxView.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * FLD combo box.
     * this allows edits to be made to the combo box.
     * Connects FLD combo to countries combo, so options will change based on selected country.
     * @param event
     * */
    @FXML
    void selectFLD(ActionEvent event) {
        cxStateCB.setDisable(false);
        populateDivisions(cxCountryCB.getValue().getCountryId());
        }

    public void populateDivisions(int countryId){
        ObservableList<FirstLevelDivisions> alldivisions = DBFLD.getAllDivisions();

        ObservableList<FirstLevelDivisions> filteredDivisons = FXCollections.observableArrayList();
        for(FirstLevelDivisions fld: alldivisions){
            if(fld.getCountryId() == countryId){
                filteredDivisons.add(fld);
            }
        }
        cxStateCB.setItems(filteredDivisons);
    }

    /**
     * populates the countries for the countries combo box.
     * */
    public void populateCountries(){
        //ObservableList<Countries> countryList = DBCountries.getAllCountries();
        cxCountryCB.setItems(DBCountries.getAllCountriesId());


    }

    /**
     * populates the countries combo box when screen is opened.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateCountries();

    }

}
