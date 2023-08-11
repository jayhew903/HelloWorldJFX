package Controller;
/**Controller class for the Appointments Scheduler GUI. */
import Database.DBAppointment;
import Model.Appointments;
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
import javafx.scene.control.RadioButton;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.sql.*;


public class AptSchedulerController implements Initializable {
    /**
     * This sets the radio buttons to a specific toggle group, so only one can be selected at a time.
     * */
    @FXML
    private ToggleGroup AptTG;
    /**
     * This is the button to view the customer page.
     * */
    @FXML
    private Button viewCxButton;
    /**
     * This is the add appointment button.
     * */
    @FXML
    private Button addAptBtn;
    /**
     * This is the delete appointment button
     * */
    @FXML
    private Button deleteAptBtn;
    /**
     * This is the log out button
     * */
    @FXML
    private Button logoutBtn;
    /**
     * This is the modify appointment button
     * */
    @FXML
    private Button modifyAptBtn;
    /**
     * This is the reports button.
     * */
    @FXML
    private Button reportsBtn;
    /**
     * This is the table view for the appointments.
     * Sets the table view to the data from the appointments class.
     * */
    @FXML
    private TableView<Appointments> aptTable;
    /**
     * This is the appointment Id table column.
     * Sets the data type as an Integer
     * */
    @FXML
    private TableColumn<Appointments, Integer> aptIDTC;
    /**
     * This is the contact Id table column.
     * Sets the data type as an Integer.
     * */
    @FXML
    private TableColumn<Appointments, Integer> contactTC;
    /**
     * This is the customer Id table column.
     * Sets the data type as an integer.
     * */
    @FXML
    private TableColumn<Appointments, Integer> customerIDTC;
    /**
     * This is the description table column.
     * Sets the data type as a string.
     * */
    @FXML
    private TableColumn<Appointments, String> descTC;
    /**
     * This is the end date table column
     * Sets the data type as a local date time.
     * */
    @FXML
    private TableColumn<Appointments, LocalDateTime> endDateTC;
    /**
     * This is the end time table column
     * sets the data type as a local date time
     * */
    @FXML
    private TableColumn<Appointments, LocalDateTime> endTimeTC;
    /**
     * this is the location table column.
     * sets the data type as a string.
     * */
    @FXML
    private TableColumn<Appointments, String> locationTC;
    /**
     * this is the start date table column.
     * sets the data type as a local date time
     * */
    @FXML
    private TableColumn<Appointments, LocalDateTime> startDateTC;
    /**
     * This is the start time table column.
     * sets the data type as a local date time.
     * */
    @FXML
    private TableColumn<Appointments, LocalDateTime> startTimeTC;
    /**
     * This is the title table column.
     * sets the data type as a string.
     * */
    @FXML
    private TableColumn<Appointments, String> titleTC;
    /**
     * This is the type table column.
     * sets the data type as a string.
     * */
    @FXML
    private TableColumn<Appointments, String> typeTC;
    /**
     * this is the user id table column
     * sets the data type as an integer.
     * */
    @FXML
    private TableColumn<Appointments, Integer> userIDTC;
    /**
     * this is the view all radio button.
     * To view all the appointments from the database
     * */
    @FXML
    private RadioButton viewAllRB;
    /**
     * this is the view by month radio button
     * to view the appointments in this month only from the database
     * */
    @FXML
    private RadioButton viewByMonthRB;
    /**
     * this is the view by week radio button.
     * to view the appointments in the week only from the database.
     * */
    @FXML
    private RadioButton viewByWeekRB;

    public AptSchedulerController() throws IOException {
    }
    /**
     * Delete appointment button
     * Using this will delete/cancel the appointment data.
     * There is a confirmation alert, to prevent accidentally cancelling an appointment.
     * Cancelling appointment will confirm the appointment Id and type that was cancelled.
     * @param event
     * @throws IOException
     * */
    @FXML
    void DeleteApt(ActionEvent event) throws SQLException, IOException {

            if (aptTable.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please select an Appointment to cancel.");
                alert.showAndWait();
                return;
            }

        else{
                Appointments appointment = aptTable.getSelectionModel().getSelectedItem();
                int rowsAffected = DBAppointment.delete(appointment.getAppointmentId());

                aptTable.setItems(DBAppointment.getAllAppointments());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Appointment: " + appointment.getAppointmentId() + " - " + appointment.getType() + " was cancelled.");
                alert.showAndWait();
                return;

            }

        }

        /**
         * Add appointment button.
         * Selecting this will direct the user to the add appointment screen.
         * @param event
         * @throws IOException
         * */
    @FXML
    void openAddApt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/AddAptView.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    /**
     * Logout button.
     * Selecting this will direct user back to the login screen.
     * If user logs out, they will have to log in again.
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
     * Modify appointment button.
     * Selecting this will direct the user to the modify appointment screen, with prefilled data.
     * Error will occur if no appointment is selected prior to pressing this button.
     * @param event
     * @throws IOException
     * */
    @FXML
    void openModifyApt(ActionEvent event) throws IOException {
        if(aptTable.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select an Appointment to modify");
            alert.showAndWait();
            return;
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/ModifyAptView.fxml"));
            loader.load();

            ModifyAptController MAController = loader.getController();
            MAController.setApt(aptTable.getSelectionModel().getSelectedIndex(), aptTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        }
        /**
         * Open reports button.
         * Selecting this button will direct user to the reports page.
         * @param event
         * @throws IOException
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
     * Customer button.
     * Selecting this will direct the user to the customers page with customer info
     * @param event
     * @throws IOException
     * */
    @FXML
    void openCXSched(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/AptSchedulerCxView.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    /**
     * View all radio button.
     * Selecting this will display all the appointments in the database.
     * @param event
     * @throws SQLException
     * */
    @FXML
    void viewAllRB(ActionEvent event) throws SQLException {
        viewAllRB.setSelected(true);
        viewByMonthRB.setSelected(false);
        viewByWeekRB.setSelected(false);
        aptTable.getItems().clear();
        aptTable.setItems(DBAppointment.getAllAppointments());

    }
    /**
     * View by month radio button.
     * Selecting this will view all the appointments in the current month.
     * @param event
     * @throws  SQLException
     * */
    @FXML
    void viewByMonthRB(ActionEvent event) throws SQLException {
        viewAllRB.setSelected(false);
        viewByMonthRB.setSelected(true);
        viewByWeekRB.setSelected(false);
        aptTable.getItems().clear();
        aptTable.setItems(DBAppointment.getMonthCurrent());

    }
    /**
     * View by week radio button.
     * Selecting this will display the appointments only in the current week.
     * @param event
     * @throws SQLException
     * */
    @FXML
    void viewByWeekRB(ActionEvent event) throws SQLException {
        viewAllRB.setSelected(false);
        viewByMonthRB.setSelected(false);
        viewByWeekRB.setSelected(true);
        aptTable.getItems().clear();
        aptTable.setItems(DBAppointment.getWeekCurrent());

    }
    /**
     * This method checks the overlap of appointments by checking the start and end time of the customer id.
     * Error will occur if there is an overlap of appointments for the customers.
     * This method will be called in the add and modify appointment controller.
     *
     * */
    public static boolean checkAptOverlap(int customerId, int appointmentId, LocalDateTime start, LocalDateTime end){
        ObservableList<Appointments> aptList = DBAppointment.getAllAppointments();
        LocalDateTime checkStartTime;
        LocalDateTime checkEndTime;
        for(Appointments a: aptList){
            checkStartTime = a.getStartTime();
            checkEndTime = a.getEndTime();
            if(customerId != a.getCustomerId()){
                continue;
            }
            if(appointmentId == a.getAppointmentId()) {
                continue;
            } else if (checkStartTime.isEqual(start) || checkStartTime.isEqual(end) || checkEndTime.isEqual(start) || checkEndTime.isEqual(end)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Error: Customer Appointment Overlap. Start and End must be outside of customer's current appointment time.");
                alert.showAndWait();
                return true;
            } else if(start.isAfter(checkStartTime) && start.isBefore(checkEndTime)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Error: Customer Appointment Overlap. Start and End must be outside of customer's current appointment time.");
                alert.showAndWait();
                return true;
            } else if(end.isAfter(checkStartTime) && end.isBefore(checkEndTime)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Error: Customer Appointment Overlap. Start and End must be outside of customer's current appointment time.");
                alert.showAndWait();
                return true;
            }

        }
        return false;
    }

/**
 * This initialize sets the view all radio button as selected when the page is loaded.
 * Sets the appointment table to the database appointment list.
 * Sets all the table columns so data is correctly displayed.
 *
 * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewAllRB.setSelected(true);

        //set appointments in the Table
        aptTable.setItems(DBAppointment.getAllAppointments());

        //set the table columns
        aptIDTC.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleTC.setCellValueFactory(new PropertyValueFactory<>("title"));
        descTC.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationTC.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactTC.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeTC.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeTC.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeTC.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIDTC.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIDTC.setCellValueFactory(new PropertyValueFactory<>("userId"));

    }

}
