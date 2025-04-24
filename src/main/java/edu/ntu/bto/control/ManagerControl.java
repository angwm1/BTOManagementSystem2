package edu.ntu.bto.control;

import java.time.LocalDate;

import edu.ntu.bto.model.Applicant;
import edu.ntu.bto.model.Application;
import edu.ntu.bto.model.Project;
import edu.ntu.bto.model.Registration;

import java.util.List;

/**
 * Control class for HDB Manager operations. ManagerControl allows managers to
 * create/edit/delete projects, approve or reject applications and withdrawal
 * requests, approve or reject officer registrations, and generate summary
 * reports.
 * 
 * @author SC2002 Assignment Group
 */
public class ManagerControl {
	/** The list of all projects in the system. */
	private List<Project> projects;
	/** The list of all applications in the system. */
	private List<Application> applications;
	/** The list of all officer registration requests in the system. */
	private List<Registration> registrations;

	/**
	 * Constructs a new ManagerControl with references to the master lists of
	 * projects, applications, and registrations.
	 * 
	 * @param projects      The list of {@link Project}s to manage.
	 * @param applications  The list of {@link Application}s to manage.
	 * @param registrations The list of {@link Registration}s to manage.
	 */
	public ManagerControl(List<Project> projects, List<Application> applications, List<Registration> registrations) {
		this.projects = projects;
		this.applications = applications;
		this.registrations = registrations;
	}

	/**
	 * Creates a new project with the given details and adds it to the system.
	 * 
	 * @param name         The project name.
	 * @param neighborhood The project neighborhood.
	 * @param type1        The first flat type.
	 * @param unitsType1   Number of units for flat type 1.
	 * @param priceType1   Price for flat type 1.
	 * @param type2        The second flat type (can be empty or "N/A" if none).
	 * @param unitsType2   Number of units for flat type 2.
	 * @param priceType2   Price for flat type 2.
	 * @param openDate     Application opening date.
	 * @param closeDate    Application closing date.
	 * @param manager      The manager identifier (name or NRIC) for this project.
	 */
	public void createProject(String name, String neighborhood, String type1, int unitsType1, double priceType1,
			String type2, int unitsType2, double priceType2, String openDate, String closeDate, String manager) {
		// Check that manager is not handling another overlapping project
		for (Project p : projects) {
			if (p.getManager().equals(manager) && overlaps(p.getOpenDate(), p.getCloseDate(), openDate, closeDate)) {
				System.out.println("You are already managing another project in this period.");
				return;
			}
		}

		Project newProject = new Project(name, neighborhood, type1, unitsType1, priceType1, type2, unitsType2,
				priceType2, openDate, closeDate, manager);
		projects.add(newProject);
		System.out.println("Project created successfully.");
	}

	/**
	 * Edits the details of an existing project. All provided new values will
	 * replace the current attributes of the project.
	 * 
	 * @param project      The {@link Project} to edit.
	 * @param name         The new project name.
	 * @param neighborhood The new neighborhood.
	 * @param type1        The new flat type 1 name.
	 * @param unitsType1   The new number of units for flat type 1.
	 * @param priceType1   The new price for flat type 1.
	 * @param type2        The new flat type 2 name.
	 * @param unitsType2   The new number of units for flat type 2.
	 * @param priceType2   The new price for flat type 2.
	 * @param openDate     The new opening date.
	 * @param closeDate    The new closing date.
	 * @param manager      The manager identifier (likely unchanged).
	 * @param officerSlot  The new officer slot count.
	 * @param officer      The officer identifier (likely unchanged).
	 */
	public void editProject(Project project, String name, String neighborhood, String type1, int unitsType1,
			double priceType1, String type2, int unitsType2, double priceType2, String openDate, String closeDate,
			String manager, int officerSlot, String officer) {
		if (project.getManager().equalsIgnoreCase(manager)) { // Demonstrate new neighborhood for demo
			projects.remove(project);
			Project newProj = new Project(name, neighborhood, type1, unitsType1, priceType1, type2, unitsType2,
					priceType2, openDate, closeDate, manager, officerSlot, officer);
			projects.add(newProj);
			System.out.println("Project updated.");
			return;
		}
		System.out.println("Failed to update project.");
	}

	/**
	 * Deletes a project from the system. All associated data (applications,
	 * registrations, etc.) should be considered invalid after removal.
	 * 
	 * @param project The {@link Project} to delete.
	 */
	public void deleteProject(Project project) {
		projects.remove(project);
		System.out.println("Project deleted.");
	}

	/**
	 * Approves an applicant's application for a flat. The application status is set
	 * to SUCCESSFUL if there are flats available for the requested flat type, and
	 * one unit is reserved (decremented from available count).
	 * 
	 * @param app The {@link Application} to approve.
	 */
	public void approveApplication(Application app) {
		String flatType = app.getFlatType();
		Project proj = app.getProject();
		if (flatType.equalsIgnoreCase(proj.getType1())) { // Approve as long as there are flats available, decrement
															// accordingly
			if (proj.getUnitsType1() > 0) {
				app.setStatus(Application.Status.SUCCESSFUL);
				System.out.println("Application approved.");
				return;
			}
		} else if (flatType.equalsIgnoreCase(proj.getType2())) {
			if (proj.getUnitsType2() > 0) {
				app.setStatus(Application.Status.SUCCESSFUL);
				System.out.println("Application approved.");
				return;
			}
		}

		app.setStatus(Application.Status.UNSUCCESSFUL);
		System.out.println("Insufficient units. Application rejected.");
	}

	/**
	 * Rejects an applicant's application. The application status is set to
	 * UNSUCCESSFUL, allowing the applicant to possibly apply for another project.
	 * 
	 * @param app The {@link Application} to reject.
	 */

	public void rejectApplication(Application app) {
		app.setStatus(Application.Status.UNSUCCESSFUL);
		System.out.println("Application rejected.");
	}

	/**
	 * Approves an applicant's request to withdraw their application. The
	 * application is marked as UNSUCCESSFUL (canceled). If the application was
	 * previously successful and a unit was reserved, that unit could be made
	 * available again.
	 * 
	 * @param applicant The applicant requesting withdrawal.
	 */
	public void approveWithdrawal(Applicant applicant) {
		System.out.println("Withdrawal approved.");
	}

	/**
	 * Rejects an applicant's request to withdraw their application. The application
	 * remains in its current state (e.g., still active or successful).
	 * 
	 * @param applicant The applicant requesting withdrawal.
	 */
	public void rejectWithdrawal(Applicant applicant) {
		System.out.println("Withdrawal request rejected.");
	}

	/**
	 * Approves an HDB Officer's registration request to handle a project. The
	 * registration status is updated to APPROVED, the project is updated to assign
	 * the officer, and the officer slot count is decremented.
	 * 
	 * @param reg The {@link Registration} to approve.
	 */
	public void approveOfficer(Registration reg) {
		reg.setStatus(Registration.Status.APPROVED);
		reg.getProject().decrementOfficerSlot();
		System.out.println("Officer registration approved.");
	}

	/**
	 * Rejects an HDB Officer's registration request to handle a project. The
	 * registration status is updated to REJECTED.
	 * 
	 * @param reg The {@link Registration} to reject.
	 */
	public void rejectOfficer(Registration reg) {
		reg.setStatus(Registration.Status.REJECTED);
		System.out.println("Officer registration rejected.");
	}

	/**
	 * Generates a report of applicants with booked flats. Currently, this method
	 * prints out a list of all applications that have status BOOKED. The filterBy
	 * parameter is not dynamically used in this implementation.
	 * 
	 * @param filterBy A filter criteria for the report (e.g., "BOOKED" to filter
	 *                 applications that are booked).
	 */
	public void generateReport(String filterBy) {
		System.out.println("-- Report: Applicants with Booked Flats --");
		for (Application app : applications) {
			if (app.getStatus() == Application.Status.BOOKED) {
				Applicant a = app.getApplicant();
				if (filterBy.equalsIgnoreCase("Married") && !a.getMaritalStatus().equalsIgnoreCase("Married"))
					continue;
				System.out.printf("NRIC: %s, Age: %d, Marital Status: %s, Project: %s, Flat Booked: %s\n", a.getNric(),
						a.getAge(), a.getMaritalStatus(), app.getProject().getProjectName(), app.getFlatType());
			}
		}
	}

	/**
	 * Determines whether two date ranges overlap.
	 * <p>
	 * Each date string must be in ISO-8601 format (YYYY-MM-DD). The method parses
	 * the strings into {@link LocalDate} instances and checks if the ranges
	 * [start1, end1] and [start2, end2] have any intersection.
	 * </p>
	 *
	 * @param s1 the start date of the first range, in ISO-8601 format (YYYY-MM-DD)
	 * @param e1 the end date of the first range, in ISO-8601 format (YYYY-MM-DD)
	 * @param s2 the start date of the second range, in ISO-8601 format (YYYY-MM-DD)
	 * @param e2 the end date of the second range, in ISO-8601 format (YYYY-MM-DD)
	 * @return {@code true} if the two date ranges overlap; {@code false} otherwise
	 * @throws DateTimeParseException if any of the date strings cannot be parsed
	 */
	private boolean overlaps(String s1, String e1, String s2, String e2) {
		LocalDate start1 = LocalDate.parse(s1);
		LocalDate end1 = LocalDate.parse(e1);
		LocalDate start2 = LocalDate.parse(s2);
		LocalDate end2 = LocalDate.parse(e2);

		return !(end1.isBefore(start2) || start1.isAfter(end2));
	}
}
