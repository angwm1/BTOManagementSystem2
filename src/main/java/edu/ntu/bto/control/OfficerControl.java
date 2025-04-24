package edu.ntu.bto.control;

import java.time.LocalDate;
import java.util.ArrayList;

import edu.ntu.bto.model.HDBOfficer;
import edu.ntu.bto.model.Project;
import edu.ntu.bto.model.Application;
import edu.ntu.bto.model.Registration;

import java.util.List;

/**
 * Control class for HDB Officer operations related to project registrations.
 * OfficerControl allows an officer to register interest in handling a project
 * and provides access to registration records for viewing and approval.
 * 
 * @author SC2002 Assignment Group
 */
public class OfficerControl {
	/** The list of all officer registration requests in the system. */
	private List<Registration> registrations;

	/**
	 * Constructs a new OfficerControl with the given list of registrations.
	 * 
	 * @param registrations The list of {@link Registration} records to manage.
	 */
	public OfficerControl(List<Registration> registrations) {
		this.registrations = registrations;
	}

	/**
	 * Submits a registration request for an officer to handle a project. Before
	 * creating a new registration, it ensures the officer has not already
	 * registered for the project and is not handling another overlapping project.
	 * 
	 * @param officer The {@link HDBOfficer} who wants to register as a project
	 *                officer.
	 * @param project The {@link Project} the officer wants to handle.
	 * @return {@code true} if the registration request was successfully created, or
	 *         {@code false} if the officer is ineligible (already registered or
	 *         time conflict).
	 */
	public boolean registerToProject(HDBOfficer officer, Project project) {
		// Ensure officer isn't already handling another project during this period
		for (Registration reg : officer.getRegistrations()) {
			if (reg.getProject().equals(project)) {
				System.out.println("You have already registered for this project.");
				return false;
			}
			if (overlaps(reg.getProject(), project) && reg.getStatus() == Registration.Status.APPROVED) {
				System.out.println("Cannot register for multiple projects within same application period.");
				return false;
			}
		}
		if (officer.getApplication() != null && officer.getApplication().getProject().equals(project)
				&& officer.getApplication().getStatus() != Application.Status.UNSUCCESSFUL) {
			System.out.println("Cannot register for project if you are an applicant.");
			return false;
		}

		Registration registration = new Registration(officer, project);
		officer.getRegistrations().add(registration);
		registrations.add(registration);
		return true;
	}

	/**
	 * Determines whether the application windows of two projects overlap.
	 * <p>
	 * Parses the open and close date strings of {@code p1} and {@code p2} (in
	 * ISO-8601 format, "YYYY-MM-DD") into {@link LocalDate} instances and checks if
	 * the intervals [open1, close1] and [open2, close2] intersect.
	 * </p>
	 *
	 * @param p1 the first {@code Project} whose open and close dates will be parsed
	 * @param p2 the second {@code Project} whose open and close dates will be
	 *           parsed
	 * @return {@code true} if the two projects’ application windows overlap;
	 *         {@code false} otherwise
	 * @throws DateTimeParseException if any of the date strings cannot be parsed
	 */
	private boolean overlaps(Project p1, Project p2) {
		LocalDate open1 = LocalDate.parse(p1.getOpenDate());
		LocalDate close1 = LocalDate.parse(p1.getCloseDate());
		LocalDate open2 = LocalDate.parse(p2.getOpenDate());
		LocalDate close2 = LocalDate.parse(p2.getCloseDate());

		return !(close1.isBefore(open2) || open1.isAfter(close2));
	}

	/**
	 * Returns the list of all officer registrations.
	 * 
	 * @return A list of {@link Registration} requests.
	 */
	public List<Registration> getRegistrations() {
		return registrations;
	}

	/**
	 * Retrieves all registration requests made by a specific officer.
	 * 
	 * @param officer The officer whose registration requests to retrieve.
	 * @return A list of {@link Registration} associated with the given officer.
	 */
	public List<Registration> getRegistrationsByOfficer(HDBOfficer officer) {
		List<Registration> result = new ArrayList<>();
		for (Registration r : registrations) {
			if (r.getOfficer().equals(officer))
				result.add(r);
		}
		return result;
	}

	/**
	 * Approves an officer's registration request. The registration status is set to
	 * APPROVED. (In practice, a manager would call this method.)
	 * 
	 * @param reg The {@link Registration} to approve.
	 */
	public void approveRegistration(Registration reg) {
		reg.setStatus(Registration.Status.APPROVED);
		reg.getProject().decrementOfficerSlot();
		System.out.println("Officer registration approved.");
	}

	/**
	 * Rejects an officer's registration request. The registration status is set to
	 * REJECTED.
	 * 
	 * @param reg The {@link Registration} to reject.
	 */
	public void rejectRegistration(Registration reg) {
		reg.setStatus(Registration.Status.REJECTED);
		System.out.println("Officer registration rejected.");
	}
}
