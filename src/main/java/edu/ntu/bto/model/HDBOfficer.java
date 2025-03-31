package edu.ntu.bto.model;

import java.util.Scanner;
import edu.ntu.bto.service.BTOManagementSystem;

/**
 * Represents an HDB Officer.
 * HDB Officers have all the capabilities of Applicants plus additional officer-specific operations.
 */
public class HDBOfficer extends Applicant {
    private String registrationStatus; // e.g., "Pending", "Approved", "Rejected"
    private Project handledProject; // The project the officer is registered for
    
    public HDBOfficer(String nric, int age, String maritalStatus) {
        super(nric, age, maritalStatus);
        registrationStatus = "";
    }
    
    public HDBOfficer(String nric, int age, String maritalStatus, String password) {
        super(nric, age, maritalStatus, password);
        registrationStatus = "";
    }
    
    public String getRegistrationStatus() {
        return registrationStatus;
    }
    
    public void setRegistrationStatus(String status) {
        this.registrationStatus = status;
    }
    
    public Project getHandledProject() {
        return handledProject;
    }
    
    public void setHandledProject(Project project) {
        this.handledProject = project;
    }
    
    @Override
    public void displayMenu(Scanner scanner, BTOManagementSystem system) {
        boolean logout = false;
        while (!logout) {
            System.out.println("\n--- HDB Officer Menu ---");
            System.out.println("1. View Projects");
            System.out.println("2. Apply for a Project");
            System.out.println("3. Register as Officer");
            System.out.println("4. Book Flat");
            System.out.println("5. Generate Receipt");
            System.out.println("6. Change Password");
            System.out.println("7. Logout");
            System.out.print("Select an option: ");
            String opt = scanner.nextLine();
            switch (opt) {
                case "1":
                    system.getProjects().forEach(System.out::println);
                    break;
                case "2":
                    System.out.print("Enter project name to apply for: ");
                    String projName = scanner.nextLine();
                    Project proj = system.getProjects().stream()
                            .filter(p -> p.toString().contains(projName))
                            .findFirst().orElse(null);
                    if (proj != null) {
                        setAppliedProject(proj);
                        setApplicationStatus("Pending");
                        System.out.println("Application submitted. Status: Pending.");
                    } else {
                        System.out.println("Project not found.");
                    }
                    break;
                case "3":
                    System.out.print("Enter project name to register as officer: ");
                    String regProjName = scanner.nextLine();
                    Project regProj = system.getProjects().stream()
                            .filter(p -> p.toString().contains(regProjName))
                            .findFirst().orElse(null);
                    if (regProj != null) {
                        this.registrationStatus = "Pending";
                        System.out.println("Officer registration submitted. Status: Pending.");
                    } else {
                        System.out.println("Project not found.");
                    }
                    break;
                case "4":
                    if ("Successful".equalsIgnoreCase(getApplicationStatus())) {
                        System.out.print("Enter flat type to book: ");
                        String flatType = scanner.nextLine();
                        setApplicationStatus("Booked");
                        System.out.println("Flat booked successfully. Status set to Booked.");
                    } else {
                        System.out.println("Your application is not successful yet.");
                    }
                    break;
                case "5":
                    if ("Booked".equalsIgnoreCase(getApplicationStatus())) {
                        System.out.println("----- Receipt -----");
                        System.out.println("NRIC: " + getNric());
                        System.out.println("Project: " + (getAppliedProject() != null ? getAppliedProject() : "N/A"));
                        System.out.println("Flat booked: " + "Flat Type (dummy)");
                        System.out.println("-------------------");
                    } else {
                        System.out.println("No booking available.");
                    }
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
}
