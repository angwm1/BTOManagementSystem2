package edu.ntu.bto.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import edu.ntu.bto.service.BTOManagementSystem;

/**
 * Represents an Applicant.
 */
public class Applicant extends User {
	private Project appliedProject;
	private String applicationStatus; // "Pending", "Successful", "Unsuccessful", "Booked"
	private List<Enquiry> enquiries;
	private String appliedFlatType;

	public Applicant(String nric, int age, String maritalStatus) {
		super(nric, age, maritalStatus);
		this.enquiries = new ArrayList<>();
		this.applicationStatus = "";
		this.appliedFlatType = null;
	}

	public Applicant(String nric, int age, String maritalStatus, String password) {
		super(nric, age, maritalStatus, password);
		this.enquiries = new ArrayList<>();
		this.applicationStatus = "";
		this.appliedFlatType = null;
	}

	/**
	 * The flat type the applicant has applied for (e.g. "2-Room").
	 */
	public String getAppliedFlatType() {
	    return appliedFlatType;
	}

	/**
	 * Record which flat type the applicant chose.
	 */
	public void setAppliedFlatType(String flatType) {
	    this.appliedFlatType = flatType;
	}
	
	public Project getAppliedProject() {
		return appliedProject;
	}

	public void setAppliedProject(Project project) {
		this.appliedProject = project;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String status) {
		this.applicationStatus = status;
	}

	public List<Enquiry> getEnquiries() {
		return enquiries;
	}

	@Override
	public void displayMenu(Scanner scanner, BTOManagementSystem system) {
		boolean logout = false;
		while (!logout) {
			System.out.println("\n--- Applicant Menu ---");
			System.out.println("1. View Projects");
			System.out.println("2. Apply for a Project");
			System.out.println("3. View Application Status");
			System.out.println("4. Request Withdrawal");
			System.out.println("5. Manage Enquiries");
			System.out.println("6. Change Password");
			System.out.println("7. Logout");
			System.out.print("Select an option: ");
			String option = scanner.nextLine();
			switch (option) {
			case "1":
				// Show only projects that are visible and match applicant’s criteria.
				List<Project> matches = system.getProjects().stream()
						.filter(p -> p.isVisible() && isEligibleForProject(p)).collect(Collectors.toList());

				if (matches.isEmpty()) {
					System.out.println("No projects found.");
				} else {
					matches.forEach(System.out::println);
				}
				break;
			case "2":
				System.out.print("Enter project name to apply for: ");
				String projName = scanner.nextLine();
				Project proj = system.getProjects().stream()
						.filter(p -> p.toString().contains(projName) && p.isVisible()).findFirst().orElse(null);
				
				if (proj == null) {
					System.out.println("Project not found or not visible.");
				} else if (appliedProject != null) {
					System.out.println("You have already applied for a project.");
				} else if (!isEligibleForProject(proj)) {
					System.out.println("You are not eligible for this project.");
				} else {
					System.out.println("Which flat type would you like?");
				    System.out.printf("  1) %s — %d units available @ $%.2f%n",
				                      proj.getType1(), proj.getUnitsType1(), proj.getPriceType1());
				    System.out.printf("  2) %s — %d units available @ $%.2f%n",
				                      proj.getType2(), proj.getUnitsType2(), proj.getPriceType2());
				    System.out.print("Select 1 or 2: ");
				    String choice = scanner.nextLine().trim();

				    String flatType;
				    int    avail;
				    double price;
				    if ("1".equals(choice)) {
				        flatType = proj.getType1();
				        avail    = proj.getUnitsType1();
				        price    = proj.getPriceType1();
				    } else if ("2".equals(choice)) {
				        flatType = proj.getType2();
				        avail    = proj.getUnitsType2();
				        price    = proj.getPriceType2();
				    } else {
				        System.out.println("Invalid choice. Application cancelled.");
				        break;
				    }

				    // show the relevant info back to them
				    System.out.printf("You selected %s: %d units available @ $%.2f%n",
				                      flatType, avail, price);

				    // now attempt to book one unit of that type
				    if (avail > 0) {
				        setAppliedProject(proj);
				        setAppliedFlatType(flatType);
				        setApplicationStatus("Pending");
				        System.out.printf("Application for %s submitted. Status: Pending.%n", flatType);
				    } else {
				        System.out.println("Sorry—no more units available for " + flatType + ".");
				    }
				}
				break;
			case "3":
				if (appliedProject != null) {
					System.out.println("Applied Project: " + appliedProject);
					System.out.println("Application Status: " + applicationStatus);
				} else {
					System.out.println("No project applied.");
				}
				break;
			case "4":
				if (appliedProject != null) {
					setApplicationStatus("Withdrawn");
					System.out.println("Withdrawal requested.");
				} else {
					System.out.println("No application to withdraw.");
				}
				break;
			case "5":
				manageEnquiries(scanner);
				break;
			case "6":
				System.out.print("Enter new password: ");
				String newPw = scanner.nextLine();
				changePassword(newPw);
				System.out.println("Password changed.");
				break;
			case "7":
				logout = true;
				break;
			default:
				System.out.println("Invalid option.");
			}
		}
	}

	// Check eligibility based on marital status and age.
	private boolean isEligibleForProject(Project p) {
		// For simplicity, assume:
		// Single applicants must be 35+ and can only apply for projects with flat type
		// "2-Room".
		// Married applicants must be 21+.
		if (maritalStatus.equalsIgnoreCase("Single")) {
			if (age < 35)
				return false;
			// Check that project flat type available is "2-Room"
			return p.getType1().equalsIgnoreCase("2-Room") || p.getType2().equalsIgnoreCase("2-Room");
		} else if (maritalStatus.equalsIgnoreCase("Married")) {
			return age >= 21;
		}
		return false;
	}

	private void manageEnquiries(Scanner scanner) {
		boolean done = false;
		while (!done) {
			System.out.println("\n--- Enquiry Management ---");
			System.out.println("1. Submit Enquiry");
			System.out.println("2. View Enquiries");
			System.out.println("3. Edit Enquiry");
			System.out.println("4. Delete Enquiry");
			System.out.println("5. Back");
			System.out.print("Select an option: ");
			String opt = scanner.nextLine();
			switch (opt) {
			case "1":
				System.out.print("Enter enquiry: ");
				String text = scanner.nextLine();
				enquiries.add(new Enquiry(text, this.nric));
				System.out.println("Enquiry submitted.");
				break;
			case "2":
				if (enquiries.isEmpty()) {
					System.out.println("No enquiries.");
				} else {
					for (int i = 0; i < enquiries.size(); i++) {
						System.out.println((i + 1) + ". " + enquiries.get(i).getText());
					}
				}
				break;
			case "3":
				System.out.print("Enter enquiry number to edit: ");
				int idxEdit = Integer.parseInt(scanner.nextLine()) - 1;
				if (idxEdit >= 0 && idxEdit < enquiries.size()) {
					System.out.print("Enter new text: ");
					String newText = scanner.nextLine();
					enquiries.get(idxEdit).setText(newText);
					System.out.println("Enquiry updated.");
				} else {
					System.out.println("Invalid number.");
				}
				break;
			case "4":
				System.out.print("Enter enquiry number to delete: ");
				int idxDel = Integer.parseInt(scanner.nextLine()) - 1;
				if (idxDel >= 0 && idxDel < enquiries.size()) {
					enquiries.remove(idxDel);
					System.out.println("Enquiry deleted.");
				} else {
					System.out.println("Invalid number.");
				}
				break;
			case "5":
				done = true;
				break;
			default:
				System.out.println("Invalid option.");
			}
		}
	}
}
