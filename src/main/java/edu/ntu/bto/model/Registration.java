package edu.ntu.bto.model;

/**
 * Represents a registration request by an HDB Officer to handle a BTO project.
 * A Registration links a {@link HDBOfficer} with a {@link Project} and has an
 * approval {@link Status} indicating if the officer has been approved to manage
 * the project.
 * 
 * @author SC2002 Assignment Group
 */
public class Registration {
	/**
	 * Enumeration of possible statuses for an officer's registration request.
	 */
	public enum Status {
		PENDING, APPROVED, REJECTED
	}

	private HDBOfficer officer;
	private Project project;
	private Status status;

	/**
	 * Constructs a new Registration for an officer and a project. The status is
	 * initialized to {@link Status#PENDING}.
	 * 
	 * @param officer The {@link HDBOfficer} applying to handle the project.
	 * @param project The {@link Project} the officer wants to handle.
	 */
	public Registration(HDBOfficer officer, Project project) {
		this.officer = officer;
		this.project = project;
		this.status = Status.PENDING;
	}

	/**
	 * Returns the current status of this registration.
	 * 
	 * @return The registration {@link Status}.
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Sets the status of this registration request. This is called when a manager
	 * approves or rejects the officer's request.
	 * 
	 * @param status The new {@link Status} for this registration (APPROVED or
	 *               REJECTED).
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Returns the officer who submitted this registration.
	 * 
	 * @return The {@link HDBOfficer} for this registration.
	 */
	public HDBOfficer getOfficer() {
		return officer;
	}

	/**
	 * Returns the project that the officer requested to handle.
	 * 
	 * @return The {@link Project} in this registration.
	 */
	public Project getProject() {
		return project;
	}
}
