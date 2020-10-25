package models;

public class User {

    private String UID;

    public User(String UID) {
        this.UID = UID;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
