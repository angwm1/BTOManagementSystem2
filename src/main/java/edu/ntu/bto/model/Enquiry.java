package edu.ntu.bto.model;

/**
 * Represents an enquiry submitted by a user.
 */
public class Enquiry {
    private String text;
    private String userNric;
    
    public Enquiry(String text, String userNric) {
        this.text = text;
        this.userNric = userNric;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getUserNric() {
        return userNric;
    }
    
    @Override
    public String toString() {
        return "Enquiry from " + userNric + ": " + text;
    }
}
