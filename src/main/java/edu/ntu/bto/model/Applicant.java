package edu.ntu.bto.model;

import java.util.List;
import java.util.Scanner;

import edu.ntu.bto.service.BTOManagementSystem;

/**
 * Represents an Applicant user of the BTO Management System. An Applicant can
 * view available projects, apply for a project, withdraw an application, and
 * submit/edit/delete enquiries. This class extends {@link User} and adds an
 * application record for the applicant.
 * 
 * @author SC2002 Assignment Group
 */
public class Applicant extends User {
	private Application application;

	/**
	 * Constructs a new Applicant with the given NRIC, age, and marital status. The
	 * applicant initially has no application (set to {@code null}).
	 * 
	 * @param nric          The NRIC of the applicant.
	 * @param age           The age of the applicant.
	 * @param maritalStatus The marital status of the applicant.
	 */
	public Applicant(String nric, int age, String maritalStatus) {
		super(nric, age, maritalStatus);
		this.application = null; // default
	}

	/**
	 * Constructs a new Applicant with the given NRIC, age, marital status, and
	 * password.
	 * 
	 * @param nric          The NRIC of the applicant.
	 * @param age           The age of the applicant.
	 * @param maritalStatus The marital status of the applicant.
	 * @param password      The account password for the applicant.
	 */
	public Applicant(String nric, int age, String maritalStatus, String password) {
		super(nric, age, maritalStatus, password);
		this.application = null; // default
	}

	/**
	 * Returns the application submitted by this applicant.
	 * 
	 * @return The {@link Application} of this applicant, or {@code null} if the
	 *         applicant has not applied for any project.
	 */
	public Application getApplication() {
		return application;
	}

	/**
	 * Sets the application for this applicant. This is called when the applicant
	 * applies for a project or when their application status is updated.
	 * 
	 * @param app The {@link Application} to associate with this applicant.
	 */
	public void setApplication(Application app) {
		this.application = app;
	}

	/**
	 * Displays the menu for an Applicant and handles the user's menu choices. The
	 * Applicant menu provides options to view available projects, apply for a
	 * project, view application status, withdraw an application, submit an enquiry,
	 * view/edit/delete enquiries, and logout. This method reads the user's input
	 * and calls the appropriate control functions in the system.
	 * 
	 * @param scanner A {@link Scanner} for reading user input.
	 * @param system  The {@link BTOManagementSystem} to interact with for
	 *                performing operations (e.g., applying for projects, managing
	 *                enquiries).
	 */
	@Override
	public void displayMenu(Scanner scanner, BTOManagementSystem system) {
		boolean logout = false;
		while (!logout) {
			System.out.println("\n=== Applicant Menu ===");
			System.out.println("1. View Projects");
			System.out.println("2. Apply for Project");
			System.out.println("3. View Application Status");
			System.out.println("4. Withdraw Application");
			System.out.println("5. Submit Enquiry");
			System.out.println("6. View Submitted Enquiries");
			System.out.println("7. Edit Submitted Enquiry");
			System.out.println("8. Delete Submitted Enquiry");
			System.out.println("9. Change Password");
			System.out.println("10. Logout");
			System.out.print("Select option: ");
			int choice = Integer.parseInt(scanner.nextLine());

			switch (choice) {
			case 1:
				// View projects visible to this applicant
				System.out.print("Choose filter option (All/Flat type/Neighbourhood): ");
				String option = scanner.nextLine();
				List<Project> projToPrint = system.getProjectControl().getVisibleProjectsForApplicant(this);
				if (option.equalsIgnoreCase("All")) {
					System.out.println("All projects available to you:");
					projToPrint.forEach(p -> { 
						if (this.maritalStatus.equalsIgnoreCase("Single"))
							System.out.println(p.toStringSingle());
						else 
							System.out.println(p.toString());
					});
					break;
				}
				else if (option.equalsIgnoreCase("Flat type")) {
					System.out.print("Enter flat type: ");
					String flatType = scanner.nextLine();
					while (!flatType.equalsIgnoreCase("2-room") && !flatType.equalsIgnoreCase("3-room")) {
						System.out.println("Invalid flat type. Please reenter.");
						System.out.print("Enter flat type: ");
						flatType = scanner.nextLine();
					}
					System.out.println("All projects available to you with available flat type - " + flatType + ":");
					system.getProjectControl().filterByFlatType(projToPrint, flatType)
						.forEach(p -> {
							if (this.maritalStatus.equalsIgnoreCase("Single"))
								System.out.println(p.toStringSingle());
							else 
								System.out.println(p.toString());
						});
					break;
				}
				else if (option.equalsIgnoreCase("Neighbourhood")) {
					System.out.print("Enter neighbourhood: ");
					String neighbourhood = scanner.nextLine();
					System.out.println("All projects available to you in " + neighbourhood + ":");
					system.getProjectControl().filterByNeighborhood(projToPrint, neighbourhood)
						.forEach(p -> {
							if (this.maritalStatus.equalsIgnoreCase("Single"))
								System.out.println(p.toStringSingle());
							else 
								System.out.println(p.toString());
						});
					break;
				}
				System.out.println("Invalid filter option.");
				break;
			case 2:
				// Apply for a project
				System.out.print("Project name: ");
				String pname = scanner.nextLine();
				boolean projFound = false;
				for (Project p : system.getProjectControl().getAllProjects()) {
					if (p.getProjectName().equalsIgnoreCase(pname) && p.isVisible()) {
						projFound = true;
						System.out.print("Flat type: ");
						String ftype = scanner.nextLine();
						boolean result = system.getApplicationControl().apply(this, p, ftype);
						if (result) {
							System.out.println("Application successful.");
							break;
						} else {
							break;
						}
					}
				}
				if (!projFound) {
					System.out.println("No such project found. Application failed.");
				}
				break;
			case 3:
				// View application status
				Application app = system.getApplicationControl().getApplicationByApplicant(this);
				if (app != null)
					System.out.println("Status: " + app.getStatus());
				else
					System.out.println("No application.");
				break;
			case 4:
				// Withdraw application
				system.getApplicationControl().withdraw(this);
				break;
			case 5:
				// Submit enquiry
				System.out.print("Project name: ");
				String prj = scanner.nextLine();
				boolean eFound = false;
				for (Project p : system.getProjectControl().getAllProjects()) {
					if (p.getProjectName().equalsIgnoreCase(prj)) {
						eFound = true;
						System.out.print("Question: ");
						String q = scanner.nextLine();
						system.getEnquiryControl().submitEnquiry(this, p, q);
						break;
					}
				}
				if (!eFound) {
					System.out.println("No such project found.");
				}
				break;
			case 6:
				// View submitted enquiries
				system.getEnquiryControl().getEnquiriesByApplicant(this).forEach(e -> {
					System.out.println("[" + e.getId() + "] " + e.getQuestion());
					if (e.getResponse() == null)
						System.out.println("Response: -");
					else
						System.out.println("Response: " + e.getResponse());
				});
				break;
			case 7:
				// Edit an enquiry
				System.out.print("Enquiry ID: ");
				String eidEdit = scanner.nextLine();
				boolean answered = false;
				for (Enquiry e : system.getEnquiryControl().getAllEnquiries()) {
					if (e.getId().equalsIgnoreCase(eidEdit) && e.getResponse() != null) {
						answered = true;
						System.out.print("Enquiry already answered. Please submit a new enquiry instead.");
						break;
					}
				}
				if (answered) {
					break;
				} else {
					System.out.print("New question: ");
					system.getEnquiryControl().editEnquiry(eidEdit, scanner.nextLine(), this);
					break;
				}
			case 8:
				// Delete an enquiry
				System.out.print("Enquiry ID: ");
				system.getEnquiryControl().deleteEnquiry(scanner.nextLine(), this);
				break;
			case 9:
				// Change password
				System.out.print("Enter new password: ");
				String newPw = scanner.nextLine();
				changePassword(newPw);
				System.out.println("Password changed.");
				break;
			case 10:
				// Logout
				logout = true;
				break;
			default:
				System.out.println("Invalid option.");
			}
		}
	}
}
