package edu.ntu.bto.control;

import edu.ntu.bto.model.Applicant;
import edu.ntu.bto.model.Application;
import edu.ntu.bto.model.HDBOfficer;
import edu.ntu.bto.model.Project;
import edu.ntu.bto.model.Registration;

import java.util.List;

/**
 * Control class for managing applications. ApplicationControl handles the logic
 * for applicants (and officers acting as applicants) applying for projects,
 * withdrawing applications, and booking flats after successful applications.
 * 
 * @author SC2002 Assignment Group
 */
public class ApplicationControl {
	/** The list of all applications in the system. */
	private List<Application> applications;

	/**
	 * Constructs a new ApplicationControl with the given list of applications.
	 * 
	 * @param applications The list of {@link Application} records to manage.
	 */
	public ApplicationControl(List<Application> applications) {
		this.applications = applications;
	}

	/**
	 * Submits a new application for an applicant to a project. This method checks
	 * eligibility constraints (such as one application per applicant and age
	 * requirements for flat types) before creating the application.
	 * 
	 * @param applicant The applicant applying for the project.
	 * @param project   The project the applicant wants to apply to.
	 * @param flatType  The flat type the applicant chooses to apply for.
	 * @return {@code true} if the application was successfully submitted, or
	 *         {@code false} if the applicant does not meet the requirements or
	 *         already has an active application.
	 */
	public boolean apply(Applicant applicant, Project project, String flatType) {
		// Check constraints
		if (applicant.getApplication() != null
				&& !applicant.getApplication().getStatus().equals(Application.Status.UNSUCCESSFUL)) {
			System.out.println("You have already applied or booked a flat. Withdraw or wait for result.");
			return false;
		}

		if (flatType.equalsIgnoreCase("2-Room")
				&& !(applicant.getMaritalStatus().equals("Single") && applicant.getAge() >= 35)) {
			System.out.println("Only single applicants aged 35+ can apply for 2-Room.");
			return false;
		} else if (flatType.equalsIgnoreCase("3-Room")
				&& !(applicant.getMaritalStatus().equals("Married") && applicant.getAge() >= 21)) {
			System.out.println("Only married applicants aged 21+ can apply for 3-Room.");
			return false;
		}

		if (flatType.equalsIgnoreCase("2-Room") || flatType.equalsIgnoreCase("3-Room")) {
			Application app = new Application(applicant, project, flatType);
			applicant.setApplication(app);
			applications.add(app);
			return true;
		}
		System.out.println("Invalid flat type. Application failed.");
		return false;
	}

	/**
	 * Submits a new application for an HDB Officer acting as an applicant. This
	 * method ensures the officer is not applying to a project they are handling as
	 * an officer before delegating to the regular applicant apply logic.
	 * 
	 * @param officer  The officer applying as an applicant.
	 * @param project  The project the officer wants to apply to.
	 * @param flatType The flat type the officer chooses to apply for.
	 * @return {@code true} if the application was successfully submitted, or
	 *         {@code false} if the officer is not eligible to apply for this
	 *         project.
	 */
	public boolean apply(HDBOfficer officer, Project project, String flatType) {
		// Check if officer is already handling the project
		for (Registration reg : officer.getRegistrations()) {
			if (reg.getProject().equals(project) && reg.getStatus() == Registration.Status.APPROVED) {
				System.out.println("You cannot apply for a project you are handling as an officer.");
				return false;
			}
		}
		// Proceed with normal application logic
		return apply((Applicant) officer, project, flatType);
	}

	/**
	 * Processes an applicant's request to withdraw their application. The
	 * application status is marked as UNSUCCESSFUL (treated as canceled) so the
	 * applicant can apply again in the future.
	 * 
	 * @param applicant The applicant who wishes to withdraw their application.
	 */
	public void withdraw(Applicant applicant) {
		Application app = applicant.getApplication();
		if (app == null) {
			System.out.println("No application found.");
			return;
		}

		applications.remove(app);
		app.setStatus(Application.Status.UNSUCCESSFUL);
		applications.add(app);
		applicant.setApplication(app);
		System.out.println("Application withdrawn.");
	}

	/**
	 * Allows an HDB Officer to book a flat for an applicant after the application
	 * is successful. This method marks the applicant's application as BOOKED
	 * (finalized) and updates the project's available unit count for the flat type.
	 * 
	 * @param officer   The HDB Officer processing the flat booking.
	 * @param applicant The applicant who will book the flat.
	 * @param flatType  The flat type the applicant is booking.
	 */
	public void bookFlat(HDBOfficer officer, Applicant applicant, String flatType) {
		Application app = applicant.getApplication();
		if (app == null) {
			System.out.println("No application to consider.");
			return;
		} else if (app.getStatus() == Application.Status.BOOKED) {
			System.out.println("Booking not allowed. Only one flat can be booked at a time.");
			return;
		} else if (app.getStatus() != Application.Status.SUCCESSFUL) {
			System.out.println("Booking not allowed. Application must be successful.");
			return;
		}

		Project project = app.getProject();
		if (flatType.equalsIgnoreCase(project.getType1())) {
			if (project.getUnitsType1() == 0) {
				System.out.println("No units left for this flat type. Booking unsuccessful.");
				app.setStatus(Application.Status.UNSUCCESSFUL);
				return;
			}
			project.decrementUnitsType1(); // decrement accordingly
			app.setStatus(Application.Status.BOOKED);
			System.out.println("Flat booked successfully. Generating receipt...");
			System.out.println("===== BTO Flat Booking Receipt =====");
			System.out.println("Applicant's NRIC: " + applicant.getNric());
			System.out.println("Applicant's Age: " + applicant.getAge());
			System.out.println("Applicant's Marital Status: " + applicant.getMaritalStatus());
			System.out.println("Flat Type Chosen: " + app.getFlatType());
			System.out.println("Project Details:");
			System.out.println(app.getProject().toString());
			System.out.println("Status: " + app.getStatus());
			System.out.println("Booking Officer's NRIC: " + officer.getNric());
			System.out.println("====================================\n");
			return;
		} else if (flatType.equalsIgnoreCase(project.getType2())) {
			if (project.getUnitsType2() == 0) {
				System.out.println("No units left for this flat type. Booking unsuccessful.");
				app.setStatus(Application.Status.UNSUCCESSFUL);
				return;
			}
			project.decrementUnitsType2(); // decrement accordingly
			app.setStatus(Application.Status.BOOKED);
			System.out.println("Flat booked successfully. Generating receipt...");
			System.out.println("===== BTO Flat Booking Receipt =====");
			System.out.println("Applicant's NRIC: " + applicant.getNric());
			System.out.println("Applicant's Age: " + applicant.getAge());
			System.out.println("Applicant's Marital Status: " + applicant.getMaritalStatus());
			System.out.println("Flat Type Chosen: " + app.getFlatType());
			System.out.println("Project Details:");
			System.out.println(app.getProject().toString());
			System.out.println("Status: " + app.getStatus());
			System.out.println("Booking Officer's NRIC: " + officer.getNric());
			System.out.println("====================================\n");
			return;
		}
		System.out.println("Invalid flat type. Booking unsuccessful.");
		app.setStatus(Application.Status.UNSUCCESSFUL);
		return;
	}

	/**
	 * Returns the list of all applications being managed.
	 * 
	 * @return A list of {@link Application} objects in the system.
	 */
	public List<Application> getApplications() {
		return applications;
	}

	/**
	 * Retrieves the application associated with a given applicant.
	 * 
	 * @param applicant The applicant whose application is to be retrieved.
	 * @return The {@link Application} of the given applicant, or {@code null} if
	 *         none exists.
	 */
	public Application getApplicationByApplicant(Applicant applicant) {
		return applicant.getApplication();
	}
}
