package edu.ntu.bto.model;

import java.util.Scanner;
import edu.ntu.bto.service.BTOManagementSystem;

/**
 * Abstract class representing a system user.
 */
public abstract class User {
    protected String nric;
    protected int age;
    protected String maritalStatus;
    protected String password;
    
    public User(String nric, int age, String maritalStatus) {
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = "password"; // default
    }
    
    public User(String nric, int age, String maritalStatus, String password) {
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
    }
    
    public String getNric() {
        return nric;
    }
    
    public int getAge() {
        return age;
    }
    
    public String getMaritalStatus() {
        return maritalStatus;
    }
    
    public boolean checkPassword(String pw) {
        return password.equals(pw);
    }
    
    public void changePassword(String newPw) {
        this.password = newPw;
    }
    
    /**
     * Displays the CLI menu for the user.
     */
    public abstract void displayMenu(Scanner scanner, BTOManagementSystem system);
}
