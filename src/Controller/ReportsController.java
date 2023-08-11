package Controller;
/**Controller class for the reports GUI. */
import Database.DBAppointment;
import Database.DBContacts;
import Database.DBCustomers;
import Model.Appointments;
import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.ResourceBundle;


public class ReportsController implements Initializable {
    /**
     * Observable list to display the available month options.
     * */
    ObservableList<String> Months = FXCollections.observableList(Arrays.asList("January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"));
    /**
     * Appointments table view
     * */
    @FXML
    private TableView<Appointments> AptTV;
    /**
     * customer ID table column.
     * */
    @FXML
    private TableColumn<Appointments, Integer> aptCxId;
    /**
     * appointment description table column.
     * */
    @FXML
    private TableColumn<Appointments, String> aptDesc;
    /**
     * appointment end time table column
     * */
    @FXML
    private TableColumn<Appointments, LocalDateTime> aptEnd;
    /**
     * appointment Id table column
     * */
    @FXML
    private TableColumn<Appointments, Integer> aptId;
    /**
     * appointment start table column.
     * */
    @FXML
    private TableColumn<Appointments, LocalDateTime> aptStart;
    /**
     * appointment title table column
     * */
    @FXML
    private TableColumn<Appointments, String> aptTitle;
    /**
     * appointment type table column.
     * */
    @FXML
    private TableColumn<Appointments, String> aptType;
    /**
     * Label to display the count based on appointment type and month
     * */
    @FXML
    private Label aptCountMTLbl;
    /**
     * label to display the total number of appointments.
     * */
    @FXML
    private Label countAptLbl;
    /**
     * label to display the total number of customers.
     * */
    @FXML
    private Label countCXLbl;
    /**
     * appointment button
     * */
    @FXML
    private Button backToAptBtn;
    /**
     * customer button
     * */
    @FXML
    private Button backToCxBtn;
    /**
     * logout button
     * */
    @FXML
    private Button backToLoginBtn;
    /**
     * combo box to select the month
     * */
    @FXML
    private ComboBox<String> selectMonthCB;
    /**
     * combo box to select the type
     * */
    @FXML
    private ComboBox<Appointments> selectTypeCB;
    /**
     * combo box to select the contact
     * */
    @FXML
    private ComboBox<Contacts> contactCB;
    /**
     * appointments button
     * this will direct user back to the appointments page
     * @param event
     * @throws IOException
     * */
    @FXML
    void backToApt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/AptSchedulerView.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * customers button
     * this will direct user back to the customers page
     * @param event
     * @throws IOException
     * */
    @FXML
    void backToCx(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/AptSchedulerCxView.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * logout button.
     * This will direct user back to the login page
     * user will have to login again
     * @param event
     * @throws IOException
     * */
    @FXML
    void backToLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/LoginView.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Type combo box
     * This will allow user to select the type to display the number of appointments based on type and month
     * @param event
     * @throws SQLException
     * */
    @FXML
    void selectTypeCB(ActionEvent event) throws SQLException {
      String selectedMonth = selectMonthCB.getValue();
      Appointments selectedType = selectTypeCB.getValue();
      aptCountMTLbl.setText(String.valueOf(DBAppointment.aptTypeMonth(selectedMonth, selectedType)));
    }
    /**
     * month combo box
     * This will allow user to select the month to display the number of appointments based on type and month
     * @param event
     * @throws SQLException
     * */
    @FXML
    void selectMonthCB(ActionEvent event) throws SQLException {
        String selectedMonth = selectMonthCB.getValue();
        Appointments selectedType = selectTypeCB.getValue();
        aptCountMTLbl.setText(String.valueOf(DBAppointment.aptTypeMonth(selectedMonth, selectedType)));
    }
    /**
     * contact combo box
     * This will allow user to select a contact.
     * This filters the table view based on the contact selected
     * table view will only display the appointments for the selected contact
     * @param event
     * @throws SQLException
     * */
    @FXML
    void selectContactCB(ActionEvent event) throws SQLException {
        AptTV.getItems().removeAll();
        Contacts selectedContact = contactCB.getValue();
        int selectedContactId = selectedContact.getContactId();
            AptTV.setItems(DBAppointment.getAptContact(selectedContactId));
        }

        /**
         * This sets all the combo boxes correctly
         * sets the labels to display the current appointment or customer count when page is loaded
         * sets the table columns correctly.
         * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactCB.setItems(DBContacts.getAllContacts());
        selectMonthCB.setItems(Months);
        selectTypeCB.setItems(DBAppointment.getAptType());
        try {
            countCXLbl.setText(String.valueOf(DBCustomers.countNumCx()));
            countAptLbl.setText(String.valueOf(DBAppointment.countNumApt()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        aptId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        aptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        aptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        aptDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        aptStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        aptEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        aptCxId.setCellValueFactory(new PropertyValueFactory<>("customerId"));


    }
}
