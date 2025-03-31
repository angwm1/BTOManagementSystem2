package edu.ntu.bto.model;

/**
 * Represents a BTO project.
 */
public class Project {
    private String projectName;
    private String neighborhood;
    private String type1;
    private int unitsType1;
    private double priceType1;
    private String type2;
    private int unitsType2;
    private double priceType2;
    private String openDate;
    private String closeDate;
    private String manager;
    private int officerSlot;
    private String officer;
    private boolean visible; // visibility flag

    public Project(String projectName, String neighborhood, String type1, int unitsType1, double priceType1,
                   String type2, int unitsType2, double priceType2, String openDate, String closeDate,
                   String manager, int officerSlot, String officer) {
        this.projectName = projectName;
        this.neighborhood = neighborhood;
        this.type1 = type1;
        this.unitsType1 = unitsType1;
        this.priceType1 = priceType1;
        this.type2 = type2;
        this.unitsType2 = unitsType2;
        this.priceType2 = priceType2;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.manager = manager;
        this.officerSlot = officerSlot;
        this.officer = officer;
        this.visible = true; // default visible
    }
    
    public boolean isVisible() {
        return visible;
    }
    
    public void toggleVisibility() {
        visible = !visible;
    }
    
    @Override
    public String toString() {
        return "Project: " + projectName + ", Neighborhood: " + neighborhood + ", Visible: " + visible;
    }
}
