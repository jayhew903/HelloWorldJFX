package Controller;
/**This is the controller call for the customer GUI. */
import Database.DBAppointment;
import Database.DBCustomers;
import Model.Appointments;
import Model.Customers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AptSchedulerCxController implements Initializable {
    /**
     * This is the table view to display the customers.
     * sets the table view to the customers class.
     * */
    @FXML
    private TableView<Customers> customerTV;
    /**
     * This is the customer ID table column.
     * sets the data type to an integer.
     * */
    @FXML
    private TableColumn<Customers, Integer> CustomerIdTC;
    /**
     * This is the first level division table column.
     * sets the data type as an integer.
     * */
    @FXML
    private TableColumn<Customers, Integer> fldIdTC;
    /**
     * This is the customer name table column.
     * sets the data type to a string.
     * */
    @FXML
    private TableColumn<Customers, String> customerNameTC;
    /**
     * This is the customer phone table column.
     * sets the data time to a string.
     * */
    @FXML
    private TableColumn<Customers, String> phoneTC;
    /**
     * This is the customer postal code table column
     * sets the data type to a string
     * */
    @FXML
    private TableColumn<Customers, String> postalCodeTC;
    /**
     * This is the customer address table column.
     * sets the data type to a string.
     * */
    @FXML
    private TableColumn<Customers, String> addressTC;
    /**
     * This is the add customer button.
     * */
    @FXML
    private Button addCxBtn;
    /**
     * This is the delete customer button
     * */
    @FXML
    private Button deleteCxBtn;
    /**
     * This is the logout button.
     * */
    @FXML
    private Button logoutBtn;
    /**
     * This is the back button.
     * */
    @FXML
    private Button backBtn;
    /**
     * This is the modify customer button.
     * */
    @FXML
    private Button modifyCxBtn;
    /**
     * This is the reports button.
     * */
    @FXML
    private Button reportsBtn;
    /**
     * This is the label that updates based on customer selected.
     * */
    @FXML
    private Label theLabel;
    /**
     * This is the delete customer button.
     * Error will occur is a customer is not selected before pressing this.
     * Once pressed customer data will be deleted from the database
     * This will cancel appointments associated with this customer as well.
     * @param event
     * @throws SQLException
     * */
    @FXML
    void DeleteCx(ActionEvent event) throws SQLException {
        if(customerTV.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a customer to delete.");
            alert.showAndWait();
            return;
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Are you sure you want to delete this customer?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                ObservableList<Appointments> aptList = DBAppointment.getAllAppointments();
                for(Appointments a : aptList){
                    if(a.getCustomerId() == customerTV.getSelectionModel().getSelectedItem().getCustomerId()){
                        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                        alert1.setTitle("Must cancel all appointments first.");
                        alert1.setContentText("Are you sure you want to cancel all appointments for this customer?");
                        Optional<ButtonType> result1 = alert1.showAndWait();
                        if(result1.isPresent() && result1.get() == ButtonType.OK){
                            DBAppointment.deleteAptCxId(customerTV.getSelectionModel().getSelectedItem().getCustomerId(), a.getAppointmentId());
                        }
                    }
                }
            }
        }

         {
            Customers customers = customerTV.getSelectionModel().getSelectedItem();
            int rowsAffected = DBCustomers.delete(customers.getCustomerId());

            customerTV.setItems(DBCustomers.getAllCustomer());
        }
    }

    /**
     * Back button.
     * Selecting this will direct user back to the appointments screen
     * @param event
     * @throws IOException
     * */
    @FXML
    void backToAptScheduler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/AptSchedulerView.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Add customer button.
     * Selecting this will direct user to the add customer screen to add customer info
     * @param event
     * @throws IOException
     * */
    @FXML
    void openAddCx(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/AddCustomerView.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Logout button
     * Selecting this will direct user back to the login screen
     * User will have to log in again.
     * @param event
     * @throws IOException
     * */
    @FXML
    void openLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/LoginView.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    /**
     * Modify customer button.
     * Selecting this will direct user to the modify customer screen.
     * This collects the information from the selected customer to display in the modify screen/
     * If a customer is not selected before button is pressed an error will occur.
     * @param event
     * @throws IOException
     * */
    @FXML
    void openModifyCx(ActionEvent event) throws IOException {
        if(customerTV.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a customer to modify");
            alert.showAndWait();
            return;
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/ModifyCustomerView.fxml"));
            loader.load();

            ModifyCustomerController MCController = loader.getController();
            MCController.setCx(customerTV.getSelectionModel().getSelectedIndex(), customerTV.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    /**
     * Reports button
     *Selecting this will direct the user to the reports page.
     * */
    @FXML
    void openReports(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/ReportsView.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    /**
     * This obtains the lamba expression to display the customer name when it is highligted.
     * Sets the customer table and the table columns for the table view for when screen is loaded.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //lamba expression that displays the customer name when it is highlighted
        theLabel.setText("Select a customer");
        customerTV.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newCustomer) -> //lamba
                {
                    if(newCustomer != null){
                        System.out.println("Customer Selected: " + newCustomer.getCustomerName());

                        theLabel.setText("Customer Selected: " + newCustomer.getCustomerName());
                    }
                }
    );
        ObservableList<Customers> aList = DBCustomers.getAllCustomer();

        //set customer table
        customerTV.setItems(DBCustomers.getAllCustomer());

        //set columns
        CustomerIdTC.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameTC.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressTC.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeTC.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneTC.setCellValueFactory(new PropertyValueFactory<>("phone"));
        fldIdTC.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
    }
}
