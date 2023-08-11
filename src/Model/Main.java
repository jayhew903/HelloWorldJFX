package Model;
/**This class creates a program that displays appointments and customers from a database. */

import helper.JDBC;
import Database.DBAppointment;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**This is the main method. This method will be called when the program is ran. */
public class Main extends Application {

    /**This sets the start page as the login page. */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/LoginView.fxml"));
        primaryStage.setScene(new Scene(root, 340, 340));
        primaryStage.show();
    }



/**This launches the program, and connects to the database.
 * When the program is closed, it disconnects from the database. */
    public static void main(String[] args) throws SQLException {
        JDBC.makeConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
