package edu.ntu.bto.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import edu.ntu.bto.service.BTOManagementSystem;

/**
 * Represents an Applicant.
 * Applicants can view projects, apply for one project, view application status,
 * request withdrawal, and manage enquiries.
 */
public class Applicant extends User {
    private Project appliedProject;
    private String applicationStatus; // "Pending", "Successful", "Unsuccessful", "Booked"
    private List<Enquiry> enquiries;
    
    public Applicant(String nric, int age, String maritalStatus) {
        super(nric, age, maritalStatus);
        this.enquiries = new ArrayList<>();
        this.applicationStatus = "";
    }
    
    public Applicant(String nric, int age, String maritalStatus, String password) {
        super(nric, age, maritalStatus, password);
        this.enquiries = new ArrayList<>();
        this.applicationStatus = "";
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
                    if (appliedProject != null) {
                        System.out.println("Applied Project: " + appliedProject);
                        System.out.println("Application Status: " + applicationStatus);
                    } else {
                        System.out.println("No project applied.");
                    }
                    break;
                case "4":
                    if (appliedProject != null) {
                        this.applicationStatus = "Withdrawn";
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
