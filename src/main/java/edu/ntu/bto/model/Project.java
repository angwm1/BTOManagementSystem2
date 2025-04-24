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
	/** The name of the BTO project. */
	private String projectName;
	/** The neighborhood or location of the project. */
	private String neighborhood;
	/** The name of the first flat type available (e.g., "2-Room"). */
	private String type1;
	/** The number of units available for the first flat type. */
	private int unitsType1;
	/** The selling price for each unit of the first flat type. */
	private double priceType1;
	/** The name of the second flat type available (e.g., "3-Room"). */
	private String type2;
	/** The number of units available for the second flat type. */
	private int unitsType2;
	/** The selling price for each unit of the second flat type. */
	private double priceType2;
	/** The application opening date for this project (inclusive). */
	private String openDate;
	/** The application closing date for this project (inclusive). */
	private String closeDate;
	/**
	 * The identifier of the HDB Manager in charge of this project (e.g., manager's
	 * name or NRIC).
	 */
	private String manager;
	/**
	 * The number of HDB officer slots available for this project (how many officers
	 * can handle it).
	 */
	private int officerSlot;
	/**
	 * The identifier of the HDB Officer assigned to this project, or "None" if no
	 * officer assigned yet.
	 */
	private String officer;
	/**
	 * Visibility flag indicating if this project is visible to applicants (true =
	 * visible, false = hidden).
	 */
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
	 * @param priceType1   Selling price for this flat type. * @param priceType1
	 *                     Selling price for the first flat type.
	 * @param type2        The second flat type name.
	 * @param unitsType2   Number of units for the second flat type.
	 * @param priceType2   Selling price for the second flat type.
	 * @param openDate     Application opening date (YYYY-MM-DD).
	 * @param closeDate    Application closing date (YYYY-MM-DD).
	 * @param manager      The manager identifier for this project. * @param
	 *                     officerSlot Number of officer positions available for
	 *                     this project.
	 * @param officer      The officer identifier (name or NRIC) assigned to this
	 *                     project (or "None" if none yet). * @param officerSlot
	 *                     Number of officer positions available for this project.
	 * @param officerSlot  Slot for an officer
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

	/**
	 * Decrements the count of units of type 1 by one.
	 * <p>
	 * This method reduces the {@code unitsType1} field by 1. It does not perform
	 * any checks for negative values; if {@code unitsType1} is already zero or
	 * negative, the resulting value may be less than zero.
	 * </p>
	 */
	public void decrementUnitsType1() {
		unitsType1--;
	}

	/**
	 * Decrements the count of units of type 2 by one.
	 * <p>
	 * This method reduces the {@code unitsType2} field by 1. It does not perform
	 * any checks for negative values; if {@code unitsType2} is already zero or
	 * negative, the resulting value may be less than zero.
	 * </p>
	 */
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

	/**
	 * Returns a detailed, formatted string representation of the project for a
	 * single flat type. The returned string includes: Project name Neighborhood
	 * Flat type and number of units with price Application window (open and close
	 * dates) Manager name Officer slot number and officer name Visibility flag
	 *
	 * @return a {@code String} containing the project details formatted as:
	 * 
	 *         <pre>
	 * Project: &lt;projectName&gt;
	 *   Neighborhood: &lt;neighborhood&gt;
	 *   Flat Types:
	 *     • &lt;type1&gt; – &lt;unitsType1&gt; units @ $&lt;priceType1&gt;
	 *   Application Window: &lt;openDate&gt; to &lt;closeDate&gt;
	 *   Manager: &lt;manager&gt;
	 *   Officer Slot: &lt;officerSlot&gt; – &lt;officer&gt;
	 *   Visible: &lt;visible&gt;
	 *         </pre>
	 */
	public String toStringSingle() {
		return String.format(
				"Project: %s%n" + "  Neighborhood: %s%n" + "  Flat Types:%n" + "    • %s – %d units @ $%.2f%n"
						+ "  Application Window: %s to %s%n" + "  Manager: %s%n" + "  Officer Slot: %d – %s%n"
						+ "  Visible: %b",
				projectName, neighborhood, type1, unitsType1, priceType1, openDate, closeDate, manager, officerSlot,
				officer, visible);
	}
}
