package Model;
/**This is the Appointments Class. */
import java.time.LocalDateTime;

public class  Appointments {
    private  int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int customerId;
    private int userId;
    private int contactId;
/**Constructors for Appointments- this creates the instance to call all the appointment information. */
    public Appointments(int appointmentId, String title, String description, String location, String type, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId){
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }
/**Constructor for the appointments. This selects the instance for the appointment information displayed in the table. */
    public Appointments(int appointmentId, String title, String description, String location, int contact, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactId = contact;
        this.type = type;
        this.startTime = start;
        this.endTime = end;
        this.customerId = customerId;
        this.userId = userId;
    }
    /**Contructor for the appointment types only- this is used to display the types in the combo box. */
    public Appointments(String type){
        this.type = type;
    }
/**Getters for Appointment data- this is to retrieve data. */
/**
 * @return returns appointmentId.
 * */
    public int getAppointmentId() {return appointmentId; }
    /**
     * @return returns title.
     * */
    public String getTitle() {return title; }
    /**
     * @return returns description
     * */
    public String getDescription() {return description; }
    /**
     * @return returns location
     * */
    public String getLocation() {return location; }
    /**
     * @return returns type
     * */
    public  String getType() {return type; }
    /**
     * @return returns start time
     * */
    public LocalDateTime getStartTime() {return startTime; }
    /**
     * @return returns end time
     * */
    public LocalDateTime getEndTime() {return endTime; }
    /**
     * @return returns created date
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
  /**
   * @return returns customer Id
   * */
    public int getCustomerId() {return customerId; }
    /**
     * @return returns user Id
     * */
    public int getUserId() {return userId; }
    /**
     * @return returns contact Id
     * */
    public int getContactId() {return contactId; }

    /**Override to display only the type in the combo box. */
    @Override
    public String toString(){
        return type;
    }

}
