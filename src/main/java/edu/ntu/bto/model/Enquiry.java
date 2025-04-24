package edu.ntu.bto.model;

/**
 * Represents an enquiry submitted by an applicant regarding a BTO project. An
 * Enquiry contains the question asked by the applicant and an optional response
 * from an HDB Officer.
 * 
 * @author SC2002 Assignment Group
 */
public class Enquiry {
	private String id;
	private Applicant applicant;
	private Project project;
	private String question;
	private String response;

	/**
	 * Constructs a new Enquiry.
	 * 
	 * @param id        A unique identifier for the enquiry.
	 * @param applicant The {@link Applicant} who submitted the enquiry.
	 * @param project   The {@link Project} the enquiry is regarding.
	 * @param question  The question asked by the applicant.
	 */
	public Enquiry(String id, Applicant applicant, Project project, String question) {
		this.id = id;
		this.applicant = applicant;
		this.project = project;
		this.question = question;
		this.response = null;
	}

	/**
	 * Returns the unique ID of this enquiry.
	 * 
	 * @return The enquiry ID string.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the question text of this enquiry.
	 * 
	 * @return The enquiry question.
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * Sets a new question text for this enquiry. This is used when an applicant
	 * edits their enquiry.
	 * 
	 * @param newQuestion The new question content.
	 */
	public void setQuestion(String newQ) {
		question = newQ;
	}

	/**
	 * Returns the applicant who submitted this enquiry.
	 * 
	 * @return The {@link Applicant} for this enquiry.
	 */
	public Applicant getApplicant() {
		return applicant;
	}

	/**
	 * Returns the project that this enquiry is about.
	 * 
	 * @return The {@link Project} associated with this enquiry.
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * Returns the response to this enquiry.
	 * 
	 * @return The response text, or {@code null} if no response has been given yet.
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * Sets the response for this enquiry. This is called when an HDB Officer
	 * replies to the enquiry.
	 * 
	 * @param response The response text to set.
	 */
	public void setResponse(String response) {
		this.response = response;
	}

	/**
	 * Returns a brief string representation of the enquiry.
	 * 
	 * @return A string indicating the enquiry ID, applicant NRIC, and question.
	 */
	@Override
	public String toString() {
		return "Enquiry from " + applicant.getNric() + ": " + question;
	}
}
