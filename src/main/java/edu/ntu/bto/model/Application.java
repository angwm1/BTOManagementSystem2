package edu.ntu.bto.model;

/**
 * Represents an application by an applicant for a BTO project. An Application
 * ties an {@link Applicant} to a chosen {@link Project} and flat type, and
 * tracks the application {@link Status}.
 * 
 * @author SC2002 Assignment Group
 */
public class Application {
	/**
	 * Enumeration of possible statuses for an application.
	 */
	public enum Status {
		/** Entry status upon application – outcome not yet decided. */
		PENDING,
		/**
		 * Outcome of the application is successful – applicant is invited to book a
		 * flat.
		 */
		SUCCESSFUL,
		/**
		 * Outcome of the application is unsuccessful – applicant did not get a flat.
		 */
		UNSUCCESSFUL,
		/** Applicant has booked a flat after a successful application. */
		BOOKED
	}

	/** The applicant who submitted this application. */
	private Applicant applicant;
	/** The project that the applicant applied to. */
	private Project project;
	/** The current status of this application. */
	private Status status;
	/**
	 * The flat type (e.g., "2-Room" or "3-Room") that the applicant applied for.
	 */
	private String flatType;

	/**
	 * Constructs a new Application for a given applicant and project with a chosen
	 * flat type. The application status is initialized to {@link Status#PENDING}.
	 * 
	 * @param applicant The applicant submitting the application.
	 * @param project   The project the applicant is applying to.
	 * @param flatType  The type of flat the applicant wants to apply for.
	 */
	public Application(Applicant applicant, Project project, String flatType) {
		this.applicant = applicant;
		this.project = project;
		this.flatType = flatType;
		this.status = Status.PENDING;
	}

	/**
	 * Returns the current status of this application.
	 * 
	 * @return The application {@link Status}.
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Sets the status of this application. This is used when the application
	 * outcome is decided or updated (e.g., approved, rejected, or marked as
	 * booked).
	 * 
	 * @param status The new {@link Status} for this application.
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Returns the flat type that was applied for in this application.
	 * 
	 * @return The flat type string of this application.
	 */
	public String getFlatType() {
		return flatType;
	}

	/**
	 * Returns the applicant who submitted this application.
	 * 
	 * @return The {@link Applicant} associated with this application.
	 */
	public Applicant getApplicant() {
		return applicant;
	}

	/**
	 * Returns the project that was applied for.
	 * 
	 * @return The {@link Project} that the applicant applied to.
	 */
	public Project getProject() {
		return project;
	}
}
