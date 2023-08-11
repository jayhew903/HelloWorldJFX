package Model;
/**This is the first level division class. */
import java.time.LocalDateTime;

public class FirstLevelDivisions {
    private int divisionId;
    private String division;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int countryId;
/**constructor for all the first level division information. */
    public FirstLevelDivisions(int divisionId, String division,LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int countryId){
        this.divisionId = divisionId;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
    }
    /**constructor for the information in the FLD combo box. */
    public FirstLevelDivisions(int divisionId, String division, int countryId){
        this.division = division;
        this.divisionId = divisionId;
        this.countryId = countryId;
    }
/**getters to retrieve information. */
/**
 * @return returns division Id
 * */
    public int getDivisionId() {return divisionId; }
    /**
     * @return returns division
     * */
    public String getDivision() {return division; }
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
    /**
     * @return returns country Id
     * */
    public int getCountryId() {return countryId; }

    /**override to display the division in the combo box. */
    @Override
    public String toString(){
        return division;
    }
}
