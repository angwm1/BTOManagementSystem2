package edu.ntu.bto.model;

/**
 * Represents a BTO project with housing units for application. A Project
 * contains details such as the project name, location (neighborhood), two flat
 * types with their unit counts and prices, application open/close dates, the
 * manager in charge, information about HDB officer assignment, and whether the
 * project is visible to applicants.
 * 
 * @author SC2002 Assignment Group
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
	private boolean visible;

	/**
	 * Constructs a new Project with full details.
	 * 
	 * @param projectName  The project name.
	 * @param neighborhood The neighborhood of the project.
	 * @param type1        The first flat type name.
	 * @param unitsType1   Number of units for the first flat type.
	 * @param priceType1   Selling price for the first flat type.
	 * @param type2        The second flat type name.
	 * @param unitsType2   Number of units for the second flat type.
	 * @param priceType2   Selling price for the second flat type.
	 * @param openDate     Application opening date (YYYY-MM-DD).
	 * @param closeDate    Application closing date (YYYY-MM-DD).
	 * @param manager      The manager identifier (name or NRIC) for this project.
	 * @param officerSlot  Number of officer positions available for this project.
	 * @param officer      The officer identifier (name or NRIC) assigned to this
	 *                     project (or "None" if none yet).
	 */
	public Project(String projectName, String neighborhood, String type1, int unitsType1, double priceType1,
			String type2, int unitsType2, double priceType2, String openDate, String closeDate, String manager) {
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
		this.officerSlot = 10; // assume 10
		this.officer = "";
		this.visible = true; // default
	}

	/**
	 * Constructs a new Project with a single flat type (no second type). The second
	 * flat type fields are set to defaults (zero units and price, and type2 set to
	 * "N/A").
	 * 
	 * @param projectName  The project name.
	 * @param neighborhood The neighborhood of the project.
	 * @param type1        The flat type name.
	 * @param unitsType1   Number of units for this flat type.
	 * @param priceType1   Selling price for this flat type.
	 * @param openDate     Application opening date (YYYY-MM-DD).
	 * @param closeDate    Application closing date (YYYY-MM-DD).
	 * @param manager      The manager identifier for this project.
	 */
	public Project(String projectName, String neighborhood, String type1, int unitsType1, double priceType1,
			String type2, int unitsType2, double priceType2, String openDate, String closeDate, String manager,
			int officerSlot, String officer) {
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

	/**
	 * Gets the project name.
	 * 
	 * @return The name of the project.
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * Gets the neighborhood of the project.
	 * 
	 * @return The neighborhood/location of this project.
	 */
	public String getNeighborhood() {
		return neighborhood;
	}

	/**
	 * Gets the name of the first flat type.
	 * 
	 * @return The first flat type name.
	 */
	public String getType1() {
		return type1;
	}

	/**
	 * Gets the number of units available for the first flat type.
	 * 
	 * @return The number of units for flat type 1.
	 */
	public int getUnitsType1() {
		return unitsType1;
	}

	/**
	 * Gets the selling price for the first flat type.
	 * 
	 * @return The price of each unit of flat type 1.
	 */
	public double getPriceType1() {
		return priceType1;
	}

	/**
	 * Gets the name of the second flat type.
	 * 
	 * @return The second flat type name.
	 */
	public String getType2() {
		return type2;
	}

	/**
	 * Gets the number of units available for the second flat type.
	 * 
	 * @return The number of units for flat type 2.
	 */
	public int getUnitsType2() {
		return unitsType2;
	}

	/**
	 * Gets the selling price for the second flat type.
	 * 
	 * @return The price of each unit of flat type 2.
	 */
	public double getPriceType2() {
		return priceType2;
	}

	/**
	 * Gets the application opening date of the project.
	 * 
	 * @return The opening date (YYYY-MM-DD).
	 */
	public String getOpenDate() {
		return openDate;
	}

	/**
	 * Gets the application closing date of the project.
	 * 
	 * @return The closing date (YYYY-MM-DD).
	 */
	public String getCloseDate() {
		return closeDate;
	}

	/**
	 * Gets the identifier of the manager for this project.
	 * 
	 * @return The manager's identifier (e.g., name or NRIC).
	 */
	public String getManager() {
		return manager;
	}

	/**
	 * Gets the number of remaining officer slots for this project.
	 * 
	 * @return The count of available HDB Officer positions for this project.
	 */
	public int getOfficerSlot() {
		return officerSlot;
	}

	/**
	 * Gets the identifier of the officer assigned to this project.
	 * 
	 * @return The officer's identifier, or "None" if no officer is assigned.
	 */
	public String getOfficer() {
		return officer;
	}

	/**
	 * Sets the officer for this project and reduces the available officer slots by
	 * one.
	 * 
	 * @param officer The identifier of the officer to assign to this project.
	 */
	public void setOfficer(String officer) {
		this.officer = officer;
	}

	/**
	 * Checks if the project is visible to applicants.
	 * 
	 * @return {@code true} if the project is visible, {@code false} if it is
	 *         hidden.
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Sets the visibility of the project to applicants.
	 * 
	 * @param visible {@code true} to make the project visible, {@code false} to
	 *                hide it.
	 */
	public void toggleVisibility() {
		visible = !visible;
	}

	/**
	 * Decrements the available officer slot count by one. This is typically called
	 * when an officer is approved to handle the project.
	 */
	public void decrementOfficerSlot() {
		officerSlot--;
	}

	public void decrementUnitsType1() {
		unitsType1--;
	}

	public void decrementUnitsType2() {
		unitsType2--;
	}

	/**
	 * Returns a string representation of the project, including all its details.
	 * 
	 * @return A multi-line string describing the project name, neighborhood, flat
	 *         types with units and prices, application dates, manager, officer slot
	 *         status, assigned officer, and visibility.
	 */
	@Override
	public String toString() {
		return String.format(
				"Project: %s%n" + "  Neighborhood: %s%n" + "  Flat Types:%n" + "    • %s – %d units @ $%.2f%n"
						+ "    • %s – %d units @ $%.2f%n" + "  Application Window: %s to %s%n" + "  Manager: %s%n"
						+ "  Officer Slot: %d – %s%n" + "  Visible: %b",
				projectName, neighborhood, type1, unitsType1, priceType1, type2, unitsType2, priceType2, openDate,
				closeDate, manager, officerSlot, officer, visible);
	}
	
	public String toStringSingle() {
		return String.format(
				"Project: %s%n" + "  Neighborhood: %s%n" + "  Flat Types:%n" + "    • %s – %d units @ $%.2f%n"
						+ "  Application Window: %s to %s%n" + "  Manager: %s%n"
						+ "  Officer Slot: %d – %s%n" + "  Visible: %b",
				projectName, neighborhood, type1, unitsType1, priceType1, openDate,
				closeDate, manager, officerSlot, officer, visible);
	}
}
