package Model;

/**This is the customers class. */
import java.time.LocalDateTime;

public class Customers {
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int countryId;
    private int divisionId;
/**Constructor for specific data in customer combo box. */
    public Customers(int customerId, String customerName){
        this.customerId = customerId;
        this.customerName = customerName;
    }
/**Constructor for all the customer data displayed in the table. */
    public Customers(int customerId, String customerName, String address, String postalCode, String phone, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
    }
/**Getters to retrieve data. */
/**
 * @return returns customer Id
 * */
    public int getCustomerId() {return customerId; }
    /**
     * @return returns customer name
     * */
    public String getCustomerName() {return customerName; }
    /**
     * @return returns address
     * */
    public String getAddress() {return address; }
    /**
     * @return returns postalcode
     * */
    public String getPostalCode() {return postalCode; }
    /**
     * @return returns phone
     * */
    public String getPhone() {return phone; }
    /**
     * @return  returns created date
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
     * @return returns division Id
     * */
    public int getDivisionId() {return divisionId; }

/**This override displays only the customer name in the customer combo box. */
    @Override
    public String toString(){
        return customerName;
    }
}
