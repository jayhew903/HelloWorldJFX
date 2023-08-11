package Model;
/**This is the Countries Class. */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;

public class Countries {
    private int countryId;
    private String countryName;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    /**Constructor for countries- creates instance for all countries data. */
    public Countries(int countryId, String countryName, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }
/**Constructor for the combo boxes for countries. */
    public Countries(int countryID, String countryName) {
        this.countryName = countryName;
        this.countryId = countryID;
    }

/**Getters to retrieve data. */
/**
 @return returns countryId.
 * */
    public int getCountryId() { return countryId; }
    /**
     * @return returns countryName.
     * */
    public String getCountryName() {return countryName; }
    /**
     * @return returns createDate.
     * */
    public LocalDateTime getCreateDate() {return createDate; }
    /**
     * @return returns createdBy.
     * */
    public String getCreatedBy() {return createdBy; }
    /**
     * @return returns lastUpdate
     * */
    public LocalDateTime getLastUpdate() {return lastUpdate; }
    /**
     * @return returns lastUpdatedBy.
     * */
    public String getLastUpdatedBy() {return lastUpdatedBy; }


/**Override to display only the country name in the combo box. */
    @Override
    public String toString(){
        return countryName;
    }


}
