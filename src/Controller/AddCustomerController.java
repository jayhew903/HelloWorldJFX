package Controller;
/**Controller class for the add customer GUI. */
import Database.DBCountries;
import Database.DBCustomers;
import Database.DBFLD;
import Model.*;
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

public class AddCustomerController implements Initializable {
    /**
     * Text field to input the cx address.
     * */
    @FXML
    private TextField cxAddressTxt;
    /**
     * button to cancel adding customer.
     * */
    @FXML
    private Button cxCancelBtn;
    /**
     * combo box for the countries.
     * */
    @FXML
    private ComboBox<Countries> cxCountryCB;
    /**
     * text field for the customer Id.
     * This is disabled and customer ID will be automatically provided by database.
     * */
    @FXML
    private TextField cxIdTxt;
    /**
     * text field to input the customers name.
     * */
    @FXML
    private TextField cxNameTxt;
    /**
     * text field to input the customer phone number.
     * */
    @FXML
    private TextField cxPhoneTxt;
    /**
     * text field to input the customers postal code.
     * */
    @FXML
    private TextField cxPostalCodeTxt;
    /**
     * combo box for the first level division
     * */
    @FXML
    private ComboBox<FirstLevelDivisions> cxStateCB;
    /**
     * button to save the inputted data
     * */
    @FXML
    private Button saveCxBtn;
    /**
     * this disables the FLD combo box until a country is selected
     * selecting a country will filter the available FLD options.
     * @param event
     * */
    @FXML
    void onSelectCountry(ActionEvent event) {
        cxStateCB.setDisable(false);
        populateDivisions(cxCountryCB.getValue().getCountryId());

    }
    /**
     * method to filter the available FLD based on the selected country
     * */
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
     * method to populate the countries to the country combo box.
     * */
    public void populateCountries(){
        ObservableList<Countries> countryList = DBCountries.getAllCountriesId();
        cxCountryCB.setItems(countryList);

    }
    /**
     * button to cancel adding a customer.
     * This will direct the user to the customer table page.
     * @param event
     * @throws IOException
     * */
    @FXML
    void cancelCx(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../View/AptSchedulerCxView.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    /**
     * Save butto to save the inputted data to the customer database.
     * Alerts to check data was inputted
     * Saving the data will direct user to the customer table page.
     * @param event
     * @throws IOException
     * */
    @FXML
    void saveCx(ActionEvent event) throws IOException {
        try {
            String customerName = cxNameTxt.getText();
            String address = cxAddressTxt.getText();
            String postalCode = cxPostalCodeTxt.getText();
            String phone = cxPhoneTxt.getText();
            Countries countries = cxCountryCB.getValue();
            FirstLevelDivisions firstLevelDivisions = cxStateCB.getValue();

            if (countries == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Countries is blank, please input data");
                alert.showAndWait();
                return;
            }
            if (firstLevelDivisions == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Country/Province is blank, please input data");
                alert.showAndWait();
                return;
            }
            if (customerName.isBlank()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Customer Name is blank, please input data");
                alert.showAndWait();
                return;
            }
            if (address.isBlank()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Address is blank, please input data");
                alert.showAndWait();
                return;
            }
            if (postalCode.isBlank()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Postal Code is blank, please input data");
                alert.showAndWait();
                return;
            }
            if(phone.isBlank()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Phone number is blank, please input data");
                alert.showAndWait();
                return;
            }

            DBCustomers.insert(customerName, address, postalCode, phone, firstLevelDivisions.getDivisionId());
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
 * override populates the countries in the combo box when screen is opened.
 * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateCountries();

    }
}
