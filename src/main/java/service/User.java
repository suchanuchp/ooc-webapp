package service;

public class User {
    String firstName;
    String lastName;
    String userName;
    String hashedPass;

    public User(String firstName, String lastName, String userName, String hashedPass) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.hashedPass = hashedPass;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getHashedPass() {
        return hashedPass;
    }
}
