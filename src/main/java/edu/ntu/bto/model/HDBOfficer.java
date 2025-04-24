package edu.ntu.bto.model;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import edu.ntu.bto.service.BTOManagementSystem;

/**
 * Represents an HDB Officer user. An HDB Officer is a type of Applicant with
 * additional capabilities: they can register to handle a project, view and
 * respond to enquiries for their project, and have all functionalities of an
 * Applicant. This class extends {@link Applicant} to inherit applicant behavior
 * and adds management of officer registrations.
 * 
 * @author SC2002 Assignment Group
 */
public class HDBOfficer extends Applicant {
	private List<Registration> Registrations = new ArrayList<>();

	/**
	 * Constructs a new HDBOfficer with the given NRIC, age, and marital status.
	 * Inherits the applicant attributes and sets up an empty list of officer
	 * registrations.
	 * 
	 * @param nric          The NRIC of the officer.
	 * @param age           The age of the officer.
	 * @param maritalStatus The marital status of the officer.
	 */
	public HDBOfficer(String nric, int age, String maritalStatus) {
		super(nric, age, maritalStatus);
	}

	/**
	 * Constructs a new HDBOfficer with the given NRIC, age, marital status, and
	 * password.
	 * 
	 * @param nric          The NRIC of the officer.
	 * @param age           The age of the officer.
	 * @param maritalStatus The marital status of the officer.
	 * @param password      The account password for the officer.
	 */
	public HDBOfficer(String nric, int age, String maritalStatus, String password) {
		super(nric, age, maritalStatus, password);
	}

	/**
	 * Returns the list of project registration requests made by this officer.
	 * 
	 * @return A list of {@link Registration} instances that this officer has
	 *         submitted to become a project handler.
	 */
	public List<Registration> getRegistrations() {
		return Registrations;
	}

	/**
	 * Displays the menu for an HDB Officer and handles menu choices. The Officer
	 * menu includes all applicant options (view projects, apply, withdraw,
	 * enquiries) plus officer-specific options: registering to handle a project,
	 * viewing registration status, viewing details of the project they handle (if
	 * any), viewing project enquiries, replying to enquiries, and logout.
	 * 
	 * @param scanner A {@link Scanner} for reading user input.
	 * @param system  The {@link BTOManagementSystem} to interact with for
	 *                performing operations (project applications, officer
	 *                registrations, enquiries, etc.).
	 */
	@Override
	public void displayMenu(Scanner scanner, BTOManagementSystem system) {
		boolean logout = false;
		while (!logout) {
			System.out.println("\n=== Officer Menu ===");
			System.out.println("1. View Projects");
			System.out.println("2. Apply for Project");
			System.out.println("3. View Application Status");
			System.out.println("4. Withdraw Application");
			System.out.println("5. Submit Enquiry");
			System.out.println("6. View Submitted Enquiries");
			System.out.println("7. Edit Submitted Enquiry");
			System.out.println("8. Delete Submitted Enquiry");
			System.out.println("9. Register for Project as Officer");
			System.out.println("10. View Registration Status");
			System.out.println("11. View Handled Project Details");
			System.out.println("12. View Handled Project Enquiries");
			System.out.println("13. Reply to Project Enquiries");
			System.out.println("14. Book Flat for Applicant");
			System.out.println("15. Change Password");
			System.out.println("16. Logout");
			System.out.print("Select option: ");
			int choice = Integer.parseInt(scanner.nextLine());

			switch (choice) {
			case 1:
				// View projects (same as applicant)
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
				// Apply for project (same as applicant)
				System.out.print("Project name: ");
				String pname = scanner.nextLine();
				boolean projFound = false;
				for (Project p : system.getProjectControl().getAllProjects()) {
					if (p.getProjectName().equalsIgnoreCase(pname)) {
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
				// View application status (same as applicant)
				Application app = system.getApplicationControl().getApplicationByApplicant((Applicant) this);
				if (app != null)
					System.out.println("Status: " + app.getStatus());
				else
					System.out.println("No application.");
				break;
			case 4:
				// Withdraw application (same as applicant)
				system.getApplicationControl().withdraw((Applicant) this);
				break;
			case 5:
				// Submit enquiry (same as applicant)
				System.out.print("Project name: ");
				String prj = scanner.nextLine();
				boolean eFound = false;
				for (Project p : system.getProjectControl().getAllProjects()) {
					if (p.getProjectName().equalsIgnoreCase(prj)) {
						eFound = true;
						System.out.print("Question: ");
						String q = scanner.nextLine();
						system.getEnquiryControl().submitEnquiry((Applicant) this, p, q);
						break;
					}
				}
				if (!eFound) {
					System.out.println("No such project found.");
				}
				break;
			case 6:
				// View submitted enquiries (same as applicant)
				system.getEnquiryControl().getEnquiriesByApplicant(this).forEach(e -> {
					System.out.println("[" + e.getId() + "] " + e.getQuestion());
					if (e.getResponse() == null)
						System.out.println("Response: -");
					else
						System.out.println("Response: " + e.getResponse());
				});
				break;
			case 7:
				// Edit enquiry (same as applicant)
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
				// Delete enquiry (same as applicant)
				System.out.print("Enquiry ID: ");
				system.getEnquiryControl().deleteEnquiry(scanner.nextLine(), (Applicant) this);
				break;
			case 9:
				// Register for project as officer
				System.out.print("Project to register: ");
				String pName = scanner.nextLine();
				boolean pFound = false;
				for (Project p : system.getProjectControl().getAllProjects()) {
					if (p.getProjectName().equalsIgnoreCase(pName)) {
						pFound = true;
						boolean outcome = system.getOfficerControl().registerToProject(this, p);
						if (outcome) {
							System.out.println("Registration submitted for approval.");
							break;
						} else {
							System.out.println("Registration attempt voided.");
							break;
						}
					}
				}
				if (!pFound) {
					System.out.println("No such project found. Unsuccessful.");
				}
				break;
			case 10:
				// View registration status
				getRegistrations()
						.forEach(r -> System.out.println(r.getProject().getProjectName() + " - " + r.getStatus()));
				break;
			case 11:
				// View details of the project this officer is handling (if any approved)
				System.out.println("Current Handled Project:");
				getRegistrations().stream().filter(r -> r.getStatus() == Registration.Status.APPROVED)
						.map(Registration::getProject).forEach(p -> {
							System.out.println(p.toString());
							System.out.println("All applications for this project:");
							for (Application a : system.getApplicationControl().getApplications()) {
								if (a.getProject().equals(p)) {
									System.out.println(
											"Applicant " + a.getApplicant().getNric() + " applying for flat type: "
													+ a.getFlatType() + " - Status: " + a.getStatus());
								}
							}
						});
				break;
			case 12:
				// View enquiries of the project this officer is handling
				getRegistrations().stream().filter(r -> r.getStatus() == Registration.Status.APPROVED)
						.flatMap(r -> system.getEnquiryControl().getEnquiriesByProject(r.getProject()).stream())
						.forEach(e -> {
							System.out.println(e.getApplicant().getNric() + ": " + e.getQuestion());
							if (e.getResponse() == null)
								System.out.println("Response: -");
							else
								System.out.println("Response: " + e.getResponse());
						});
				break;
			case 13:
				// Reply to an enquiry for the officer's project
				getRegistrations().stream().filter(r -> r.getStatus() == Registration.Status.APPROVED)
						.forEach(r -> system.getEnquiryControl().getEnquiriesByProject(r.getProject()).forEach(e -> {
							if (e.getResponse() == null) {
								System.out.println("Question: " + e.getQuestion());
								System.out.print("Reply: ");
								system.getEnquiryControl().replyEnquiry(e.getId(), scanner.nextLine());
							}
						}));
				break;
			case 14:
				// Book Flat for Applicant
				System.out.print("Applicant NRIC: ");
				String nric = scanner.nextLine();
				System.out.print("Flat type: ");
				String flat = scanner.nextLine();
				boolean uFound = false;
				for (User u : system.getUsers()) {
					if ((u instanceof Applicant || u instanceof HDBOfficer) && u.getNric().equalsIgnoreCase(nric)) {
						uFound = true;
						system.getApplicationControl().bookFlat(this, (Applicant) u, flat);
					}
				}
				if (!uFound) {
					System.out.println("No such user. Booking attempt voided.");
				}
				break;
			case 15:
				// Change Password
				System.out.print("Enter new password: ");
				String newPw = scanner.nextLine();
				changePassword(newPw);
				System.out.println("Password changed.");
				break;
			case 16:
				// Logout
				logout = true;
				break;
			default:
				System.out.println("Invalid option.");
			}
		}
	}

}
