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
        this.visible = true; // default
    }
    
    public String getProjectName() {
        return projectName;
    }
    
    public String getNeighborhood() {
        return neighborhood;
    }
    
    public String getType1() {
        return type1;
    }
    
    public int getUnitsType1() {
        return unitsType1;
    }
    
    public double getPriceType1() {
        return priceType1;
    }
    
    public String getType2() {
        return type2;
    }
    
    public int getUnitsType2() {
        return unitsType2;
    }
    
    public double getPriceType2() {
        return priceType2;
    }
    
    public String getOpenDate() {
        return openDate;
    }
    
    public String getCloseDate() {
        return closeDate;
    }
    
    public String getManager() {
        return manager;
    }
    
    public int getOfficerSlot() {
        return officerSlot;
    }
    
    public String getOfficer() {
        return officer;
    }
    
    public boolean isVisible() {
        return visible;
    }
    
    public void toggleVisibility() {
        visible = !visible;
    }
    
    // Simulate flat booking: deduct one unit if available
    public boolean bookFlat(String flatType) {
        if (flatType.equalsIgnoreCase(type1)) {
            if (unitsType1 > 0) {
                unitsType1--;
                return true;
            }
        } else if (flatType.equalsIgnoreCase(type2)) {
            if (unitsType2 > 0) {
                unitsType2--;
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "Project: " + projectName + ", Neighborhood: " + neighborhood + ", Visible: " + visible;
    }
}
