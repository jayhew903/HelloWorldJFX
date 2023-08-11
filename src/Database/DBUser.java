package Database;
/**This is the database user class. Used to collect information from the database.*/
import Model.Users;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUser {
    /**
     * method to call to collect list of users
     * @return returns list of users
     * */
     public static ObservableList<Users> getAllUsers() {
        ObservableList<Users> userList = FXCollections.observableArrayList();
        String sql = "SELECT User_Name, User_ID FROM users";
        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String userName = rs.getString("User_Name");
                int userId = rs.getInt("User_ID");

                Users u = new Users(userId, userName);
                System.out.println(u.getUserName());
                userList.add(u);

            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

        return userList;
    }
/**
 * method used to authenticate user for login page.
 * @return returns list of user based on inputted username and password.
 * */
    public static ObservableList<Users> getAuthenticatedUser(String userName, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
        ObservableList<Users> users = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        int userId;
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            userId = rs.getInt("User_ID");
            Users user = new Users(userId, userName);
            users.add(user);
        }
        return users;

    }

}
