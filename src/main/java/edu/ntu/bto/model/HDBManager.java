package edu.ntu.bto.model;

import java.util.Scanner;
import edu.ntu.bto.service.BTOManagementSystem;

/**
 * Represents an HDB Manager.
 * Managers can create, edit, delete projects; toggle project visibility;
 * manage officer registrations and applicant applications; and generate reports.
 */
public class HDBManager extends User {
    
    public HDBManager(String nric, int age, String maritalStatus) {
        super(nric, age, maritalStatus);
    }
    
    public HDBManager(String nric, int age, String maritalStatus, String password) {
        super(nric, age, maritalStatus, password);
    }
    
    @Override
    public void displayMenu(Scanner scanner, BTOManagementSystem system) {
        boolean logout = false;
        while (!logout) {
            System.out.println("\n--- HDB Manager Menu ---");
            System.out.println("1. Create Project");
            System.out.println("2. Edit Project");
            System.out.println("3. Delete Project");
            System.out.println("4. Toggle Project Visibility");
            System.out.println("5. View Officer Registrations");
            System.out.println("6. Approve/Reject Applications");
            System.out.println("7. Generate Report");
            System.out.println("8. Change Password");
            System.out.println("9. Logout");
            System.out.print("Select an option: ");
            String opt = scanner.nextLine();
            switch (opt) {
                case "1":
                    System.out.println("Creating a new project (dummy implementation).");
                    // Here you would collect details and call system.addProject(...)
                    break;
                case "2":
                    System.out.println("Editing a project (dummy implementation).");
                    break;
                case "3":
                    System.out.println("Deleting a project (dummy implementation).");
                    break;
                case "4":
                    System.out.println("Toggling project visibility (dummy implementation).");
                    break;
                case "5":
                    System.out.println("Viewing officer registrations (dummy implementation).");
                    break;
                case "6":
                    System.out.println("Approving/Rejecting applications (dummy implementation).");
                    break;
                case "7":
                    System.out.println("Generating report (dummy implementation).");
                    break;
                case "8":
                    System.out.print("Enter new password: ");
                    String newPw = scanner.nextLine();
                    changePassword(newPw);
                    System.out.println("Password changed.");
                    break;
                case "9":
                    logout = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
