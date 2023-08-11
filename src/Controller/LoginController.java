package Controller;
/**Controller for the login page GUI. */
import Database.DBAppointment;
import Database.DBCountries;
import Database.DBUser;
import Model.Appointments;
import Model.Countries;
import Model.Users;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LoginController implements Initializable {
    /**
     * Appointment Management System Label
     * */
    @FXML
    private Label amsLbl;
    /**
     * Username label.
     * */
    @FXML
    private Label usernameLbl;
    /**
     * Exit button.
     * */
    @FXML
    private Button exitbtn;
    /**
     * Login button.
     * */
    @FXML
    private Button loginbtn;
    /**
     * Password text field.
     * */
    @FXML
    private PasswordField passwordtxt;
    /**
     * Reset button.
     * */
    @FXML
    private Button resetbtn;

    @FXML
    private Label timezonelbl;
    /**
     * Password label.
     * */
    @FXML
    private Label passwordLbl;
    /**
     * update lable
     * This label will update based on the timezone the OS is on.
     * */
    @FXML
    private Label updatelbl;
    /**
     * Username text field
     * */
    @FXML
    private TextField usernametxt;
    /**
     * reset button
     * selecting this will clear the username and password text field.
     * @param event
     * */
    @FXML
    void cleartext(ActionEvent event) {
        usernametxt.clear();
        passwordtxt.clear();

    }
    /**
     * exit button
     * selecting this will close the program
     * @param event
     * */
    @FXML
    void closeapp(ActionEvent event) {
        System.exit(0);

    }
    /**
     * Login button
     * Selecting this will check the username and password and ensure it is authenticated.
     * Calls the authenticate user method from the DBUser class
     * Displays error message if the username or password is incorrect
     * will log login attempts as well.
     * Logging in will display an upcoming appointment alert if there are appointments for the user in 15 minutes.
     * If there are no appointments, that information will be displayed as well.
     * set up to check the system default locale, so error is displayed in selected language.
     * @param event
     * @throws IOException
     * @throws SQLException
     * */
    @FXML
    void openaptscheduler(ActionEvent event) throws IOException, SQLException {
        String username = usernametxt.getText();
        String password = passwordtxt.getText();
        String filename = "login_activity.txt", item = null;
        File file = new File(filename);
        FileWriter fileWriter = new FileWriter(file, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime converLDT = ldt.atZone(ZoneId.systemDefault());
        ZonedDateTime ldtToUTC = converLDT.withZoneSameInstant(ZoneId.of("Etc/UTC"));

        ObservableList<Users> users = DBUser.getAuthenticatedUser(username, password);

        if (users.size() > 0) {
            printWriter.println("User: " + username + " successful login at " + ldtToUTC);
            printWriter.close();
            ObservableList<Appointments> aptList = DBAppointment.getAllAppointments();
            LocalDateTime now = LocalDateTime.now();
            boolean found = false;
            String aptMessage = new String();


            //login 15 minutes alert. Need to set it up for specific user logging in
            for (Appointments a : aptList) {
                System.out.println(a.getUserId());
                System.out.println(users.get(0).getUserId());
                System.out.println(LocalDateTime.now().plusMinutes(15));

                if (a.getStartTime().isAfter(now) && a.getStartTime().isBefore(LocalDateTime.now().plusMinutes(15)) && a.getUserId() == users.get(0).getUserId()) {
                    found = true;
                    aptMessage = "Appointments within 15 minutes: " + a.getAppointmentId() + "-" + a.getType() + " on " + a.getStartTime().toLocalDate() + " at " + a.getStartTime().toLocalTime();
                    break;

                }
            }
            if (found) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointments Alert");
                alert.setHeaderText("Upcoming Appointments");
                alert.setContentText(aptMessage);

                alert.showAndWait();
            }
            if (!found) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointments Alert");
                alert.setHeaderText("Upcoming Appointments");
                alert.setContentText("There are no upcoming appointments");

                alert.showAndWait();
            }
            Parent root = FXMLLoader.load(getClass().getResource("../View/AptSchedulerView.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            ResourceBundle rb = ResourceBundle.getBundle("lang", Locale.getDefault());
            printWriter.println("User: " + username + " failed login attempt at " + ldtToUTC);
            printWriter.close();
            if(Locale.getDefault().getLanguage().equals("fr")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("Invalid Login"));
                alert.setContentText(rb.getString("Invalid username or password"));
                alert.showAndWait();
                return;
        }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Login");
                alert.setContentText("Invalid username or password");
                alert.showAndWait();
                return;
            }
            }
    }
    /**
     * Sets the update label to display the OS timezone when login in initiated.
     * Checks the system's language to display all labels in the correct laguage upon opening scree.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updatelbl.setText(String.valueOf(ZoneId.systemDefault()));

        ResourceBundle rb = ResourceBundle.getBundle("lang", Locale.getDefault());
        if(Locale.getDefault().getLanguage().equals("fr")){
            amsLbl.setText(rb.getString("Appointment Management System"));
            usernameLbl.setText(rb.getString("Username"));
            loginbtn.setText(rb.getString("Login"));
            resetbtn.setText(rb.getString("Reset"));
            exitbtn.setText(rb.getString("Exit"));
            passwordLbl.setText(rb.getString("Password"));
            timezonelbl.setText(rb.getString("Time Zone"));

        }

    }
}
