package Model;
/**This is the users class. */
import java.time.LocalDateTime;

public class Users {
    private int userId;
    private String userName;
    private String password;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
/**Constructor for all the users information. */
    public Users(int userId, String userName, String password, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }
/**contructor for the information for the users combo box. */
    public Users(int userId, String userName) {
        this.userName = userName;
        this.userId = userId;
    }
/**getters to retrieve information. */
/**
 * @return returns user Id
 * */
    public int getUserId() {return userId; }
    /**
     * @return returns user name.
     * */
    public String getUserName() {return userName; }
    /**
     * @return returns password
     * */
    public String getPassword() {return password; }
    /**
     * @return returns create date
     * */
    public LocalDateTime getCreateDate() {return createDate; }
    /**
     * @return returns created by
     * */
    public String getCreatedBy() {return createdBy; }
    /**
     * @return returns last update
     * */
    public LocalDateTime getLastUpdate() {return lastUpdate; }
    /**
     * @return returns last updated by
     * */
    public String getLastUpdatedBy() {return lastUpdatedBy; }
/**override to display the user name in the combo box. */
    @Override
    public String toString(){
        return userName;
    }

}
