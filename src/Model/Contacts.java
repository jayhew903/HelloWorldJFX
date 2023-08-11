package Model;
/**This is the Contacts class. */
public class Contacts {
    private int contactId;
    private String contactName;
    private String email;
/**Constructor for all data in contacts class. */
    public Contacts(int contactId, String contactName, String email){
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }
/**Constructor for specific data for the contacts combo boxes. */
    public Contacts(int contactId, String contactName) {
        this.contactName = contactName;
        this.contactId = contactId;
    }
/**Getters to retrieve the data. */
/**
 * @return returns contactId.
 * */
    public int getContactId() {return contactId; }
    /**
     * @return returns contactName.
     * */
    public String getContactName() {return contactName; }
    /**
     * @return returns email.
     * */
    public String getEmail() {return email; }
/**Override to display only the contact name in the combo box. */
    @Override
    public String toString(){
        return contactName;
    }

}
