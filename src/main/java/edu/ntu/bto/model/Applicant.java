package edu.ntu.bto.model;

import java.util.Scanner;

import edu.ntu.bto.service.BTOManagementSystem;

/**
 * Represents an Applicant.
 */
public class Applicant extends User {
	private Application application;

	public Applicant(String nric, int age, String maritalStatus) {
        super(nric, age, maritalStatus);
		this.application = null; //default
    }

	public Applicant(String nric, int age, String maritalStatus, String password) {
        super(nric, age, maritalStatus, password);
		this.application = null; //default
    }

	public Application getApplication() { 
		return application; 
	}

    public void setApplication(Application app) { 
		this.application = app; 
	}
	
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
					System.out.println("Projects available to you:");
					system.getProjectControl().getVisibleProjectsForApplicant(this).forEach(p ->
						System.out.println(p.toString()));
					break;
				case 2:
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
					Application app = system.getApplicationControl().getApplicationByApplicant(this);
					if (app != null) System.out.println("Status: " + app.getStatus());
					else System.out.println("No application.");
					break;
				case 4:
					system.getApplicationControl().withdraw(this);
					break;
				case 5:
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
					system.getEnquiryControl().getEnquiriesByApplicant(this).forEach(e ->
					System.out.println("[" + e.getId() + "] " + e.getQuestion()));
					break;
				case 7:
					System.out.print("Enquiry ID: ");
					String eidEdit = scanner.nextLine();
					System.out.print("New question: ");
					system.getEnquiryControl().editEnquiry(eidEdit, scanner.nextLine(), this);
					break;
				case 8:
					System.out.print("Enquiry ID: ");
					system.getEnquiryControl().deleteEnquiry(scanner.nextLine(), this);
					break;
				case 9:
					System.out.print("Enter new password: ");
					String newPw = scanner.nextLine();
					changePassword(newPw);
					System.out.println("Password changed.");
					break;
				case 10:
					logout = true; 
					break;
				default:
					System.out.println("Invalid option.");
			}
		}
	}
}
