package io.froilanandfriends.atm;

import java.util.ArrayList;

public class User {
    private boolean isAdmin = false;
    private boolean flagged = false;
    private int userID,pin;
    private String userName, firstName, lastName, email, securityQuestion, securityAnswer;

    boolean authenticate(int pin){
        if(pin==this.pin){
            return true;
        }
        return false;
    }
    public User(String[] x){

    }
    public User(){

    }

    public User(String userName, String firstName, String lastName, String email, int pin, String securityQuestion, String securityAnswer){
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pin = pin;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    public void setFlagged(){
        flagged = true;
    }

    public void removeFlagged(){
        flagged = false;
    }
    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getPin(){
        return this.pin;
    }
    public void setAsAdmin(){
        isAdmin = true;
    }

    public void removeAdminStatus(){
        isAdmin = false;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }
}