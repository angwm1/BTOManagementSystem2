package edu.ntu.bto.control;

import java.util.ArrayList;
import java.util.UUID;

import edu.ntu.bto.model.Applicant;
import edu.ntu.bto.model.Enquiry;
import edu.ntu.bto.model.Project;
import java.util.List;

/**
 * Control class for managing enquiries. EnquiryControl handles submission of
 * new enquiries by applicants, retrieval of enquiries by applicant or project,
 * and operations to reply, edit, or delete enquiries.
 * 
 * @author SC2002 Assignment Group
 */
public class EnquiryControl {
	private List<Enquiry> enquiries;

	/**
	 * Constructs a new EnquiryControl with the given list of enquiries.
	 * 
	 * @param enquiries The list of {@link Enquiry} records to manage.
	 */
	public EnquiryControl(List<Enquiry> enquiries) {
		this.enquiries = enquiries;
	}

	/**
	 * Submits a new enquiry on behalf of an applicant regarding a project. A unique
	 * ID is generated for the enquiry, and it is added to the system's enquiry
	 * list.
	 * 
	 * @param applicant The applicant submitting the enquiry.
	 * @param project   The project the enquiry is about.
	 * @param question  The question the applicant wants to ask.
	 */
	public void submitEnquiry(Applicant applicant, Project project, String question) {
		String id = UUID.randomUUID().toString().substring(0, 10);
		Enquiry e = new Enquiry(id, applicant, project, question);
		enquiries.add(e);
		System.out.println("Enquiry submitted.");
	}

	/**
	 * Retrieves all enquiries submitted by a specific applicant.
	 * 
	 * @param applicant The applicant whose enquiries to retrieve.
	 * @return A list of {@link Enquiry} submitted by the given applicant.
	 */
	public List<Enquiry> getEnquiriesByApplicant(Applicant applicant) {
		List<Enquiry> result = new ArrayList<>();
		for (Enquiry e : enquiries) {
			if (e.getApplicant().equals(applicant)) {
				result.add(e);
			}
		}
		return result;
	}

	/**
	 * Retrieves all enquiries related to a specific project.
	 * 
	 * @param project The project for which to get enquiries.
	 * @return A list of {@link Enquiry} for the given project.
	 */
	public List<Enquiry> getEnquiriesByProject(Project project) {
		List<Enquiry> result = new ArrayList<>();
		for (Enquiry e : enquiries) {
			if (e.getProject().equals(project)) {
				result.add(e);
			}
		}
		return result;
	}

	/**
	 * Records a response to an enquiry. This method finds the enquiry by its ID and
	 * sets the response text. It is typically called by an HDB Officer or Manager
	 * when replying to an enquiry.
	 * 
	 * @param enquiryId The unique ID of the enquiry to respond to.
	 * @param response  The response text to attach to the enquiry.
	 */
	public void replyEnquiry(String enquiryId, String response) {
		for (Enquiry e : enquiries) {
			if (e.getId().equals(enquiryId)) {
				e.setResponse(response);
				System.out.println("Response recorded.");
				return;
			}
		}
		System.out.println("Enquiry not found.");
	}

	/**
	 * Deletes an enquiry submitted by a given applicant. This will remove the
	 * enquiry from the system if the ID matches and the enquiry was created by the
	 * specified applicant.
	 * 
	 * @param enquiryId The unique ID of the enquiry to delete.
	 * @param applicant The applicant who originally posted the enquiry (for
	 *                  ownership verification).
	 */
	public void deleteEnquiry(String enquiryId, Applicant applicant) {
		for (Enquiry e : enquiries) {
			if (e.getId().equals(enquiryId) && e.getApplicant().equals(applicant)) {
				enquiries.remove(e);
				System.out.println("Enquiry successfully deleted.");
				return;
			}
		}
		System.out.println("Enquiry not found. Deletion not successful.");
	}

	/**
	 * Edits the question of an enquiry submitted by a given applicant. If the
	 * enquiry is found and belongs to the applicant, its question text is updated.
	 * (Note: Any existing response remains and may need to be updated separately if
	 * the question changes significantly.)
	 * 
	 * @param enquiryId   The unique ID of the enquiry to edit.
	 * @param newQuestion The new question text.
	 * @param applicant   The applicant who originally posted the enquiry.
	 */
	public void editEnquiry(String enquiryId, String newQuestion, Applicant applicant) {
		for (Enquiry e : enquiries) {
			if (e.getId().equals(enquiryId) && e.getApplicant().equals(applicant)) {
				e.setQuestion(newQuestion);
				System.out.println("Enquiry updated.");
				return;
			}
		}
		System.out.println("Unable to edit enquiry.");
	}

	/**
	 * Returns the list of all enquiries in the system.
	 * 
	 * @return A list of all {@link Enquiry} objects.
	 */
	public List<Enquiry> getAllEnquiries() {
		return enquiries;
	}
}
