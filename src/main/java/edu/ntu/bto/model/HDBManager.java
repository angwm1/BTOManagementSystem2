package edu.ntu.bto.model;

import java.util.Scanner;
import edu.ntu.bto.service.BTOManagementSystem;

/**
 * Represents an HDB Manager.
 * Managers can create, edit, delete projects; toggle project visibility;
 * manage registrations and applications; and generate reports.
 */
public class HDBManager extends User {
	protected String name;
	
    public HDBManager(String name, String nric, int age, String maritalStatus) {
        super(nric, age, maritalStatus);
        this.name = name;
    }
    
    public HDBManager(String name, String nric, int age, String maritalStatus, String password) {
        super(nric, age, maritalStatus, password);
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public void displayMenu(Scanner scanner, BTOManagementSystem system) {
        boolean logout = false;
        while (!logout) {
            System.out.println("\n=== Manager Menu ===");
            System.out.println("1. View All Projects");
            System.out.println("2. View Projects Created by You");
            System.out.println("3. Create Project");
            System.out.println("4. Edit Project");
            System.out.println("5. Delete Project");
            System.out.println("6. Toggle Visibility of Your Project");
            System.out.println("7. View All Officer Registrations");
            System.out.println("8. Approve/Reject Officer Registrations");
            System.out.println("9. Approve/Reject Applicant Applications");
            System.out.println("10. Approve/Reject Applicant Withdrawal");
            System.out.println("11. View All Enquiries");
            System.out.println("12. Reply to Your Project Enquiries");
            System.out.println("13. Generate Report");
            System.out.println("14. Change Password");
            System.out.println("15. Logout");
            System.out.print("Select option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    system.getProjectControl().getAllProjects().forEach(p ->
                        System.out.println(p.getProjectName() + " - " + p.getNeighborhood()));
                    break;
                case 2:
                    system.getProjectControl().getProjectsByManager(this.name).forEach(p ->
                        System.out.println(p.getProjectName() + " - " + p.getNeighborhood()));
                    break;
                case 3:
                    System.out.print("Enter project name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter neighborhood: ");
                    String neighborhood = scanner.nextLine();
                    System.out.print("Enter Type 1 (e.g., 2-Room): ");
                    String type1 = scanner.nextLine();
                    System.out.print("Enter number of units for Type 1: ");
                    int unitsType1 = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter selling price for Type 1: ");
                    double priceType1 = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter Type 2 (e.g., 3-Room): ");
                    String type2 = scanner.nextLine();
                    System.out.print("Enter number of units for Type 2: ");
                    int unitsType2 = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter selling price for Type 2: ");
                    double priceType2 = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter application opening date (yyyy-MM-dd): ");
                    String openDate = scanner.nextLine();
                    System.out.print("Enter application closing date (yyyy-MM-dd): ");
                    String closeDate = scanner.nextLine();
                    system.getManagerControl().createProject(name, neighborhood, type1, unitsType1, priceType1, type2, unitsType2, priceType2, openDate, closeDate, this.name);
                    break;
                case 4: // For simplicity, editing neighborhood is shown
                    System.out.print("Enter project name to edit: ");
                    String toEdit = scanner.nextLine();
                    Project project = system.getProjectControl().getProjectsByManager(this.name).stream()
                            .filter(p -> p.toString().contains(toEdit))
                            .findFirst().orElse(null);
                    if (project == null) {
                        System.out.println("Project not found in your list.");
                        return;
                    }
                    System.out.print("Enter new neighborhood: ");
                    String newNeighborhood = scanner.nextLine();
                    // For simplicity, create a new project instance with updated neighborhood.
                    system.getManagerControl().editProject(project, project.getProjectName(), newNeighborhood, project.getType1(), project.getUnitsType1(), project.getPriceType1(), 
                    project.getType2(), project.getUnitsType2(), project.getPriceType2(), project.getOpenDate(), project.getCloseDate(), this.name, project.getOfficerSlot(), project.getOfficer());
                    
                    break;
                case 5:
                    System.out.print("Project name to delete: ");
                    String toDelete = scanner.nextLine();
                    system.getProjectControl().getProjectsByManager(this.name).stream()
                        .filter(p -> p.getProjectName().equalsIgnoreCase(toDelete))
                        .findFirst().ifPresent(system.getManagerControl()::deleteProject);
                    break;
                case 6:
                    System.out.print("Project name to toggle: ");
                    String toggle = scanner.nextLine();
                    system.getProjectControl().getProjectsByManager(this.name).stream()
                        .filter(p -> p.getProjectName().equalsIgnoreCase(toggle))
                        .findFirst().ifPresent(system.getProjectControl()::toggleProjectVisibility);
                    break;
                case 7:
                    system.getOfficerControl().getRegistrations().forEach(r ->
                        System.out.println(r.getOfficer().getNric() + " -> " + r.getProject().getProjectName() + " - " + r.getStatus()));
                    break;
                case 8:
                    system.getOfficerControl().getRegistrations().stream()
                        .filter(r -> r.getProject().getManager().equals(this.name))
                        .forEach(r -> {
                            System.out.println("Officer: " + r.getOfficer().getNric() + " wants to handle " + r.getProject().getProjectName());
                            System.out.print("Approve (y/n)? ");
                            String ans = scanner.nextLine();
                            if (ans.equalsIgnoreCase("y")) system.getManagerControl().approveOfficer(r);
                            else system.getManagerControl().rejectOfficer(r);
                        });
                    break;
                case 9:
                    for (Application a : system.getApplicationControl().getApplications()) {
                        if (a.getProject().getManager().equals(this.name)) {
                            System.out.println(a.getApplicant().getNric() + " - " + a.getFlatType());
                            System.out.print("Approve (y/n)? ");
                            if (scanner.nextLine().equalsIgnoreCase("y")) system.getManagerControl().approveApplication(a);
                            else system.getManagerControl().rejectApplication(a);
                        }
                    }
                    break;
                case 10:
                    for (Application a : system.getApplicationControl().getApplications()) {
                        if (a.getProject().getManager().equals(this.name) && a.getStatus() != Application.Status.BOOKED) {
                            System.out.print("Approve withdrawal for " + a.getApplicant().getNric() + " (y/n)? ");
                            if (scanner.nextLine().equalsIgnoreCase("y")) system.getManagerControl().approveWithdrawal(a.getApplicant());
                            else system.getManagerControl().rejectWithdrawal(a.getApplicant());
                        }
                    }
                    break;
                case 11:
                    system.getEnquiryControl().getAllEnquiries().forEach(e ->
                        System.out.println(e.getApplicant().getNric() + ": " + e.getQuestion()));
                    break;
                case 12:
                    system.getProjectControl().getProjectsByManager(this.name).forEach(proj ->
                        system.getEnquiryControl().getEnquiriesByProject(proj).forEach(e -> {
                            if (e.getResponse() == null) {
                                System.out.println("Q: " + e.getQuestion());
                                System.out.print("Reply: ");
                                system.getEnquiryControl().replyToEnquiry(e.getId(), scanner.nextLine());
                            }
                        }));
                    break;
                case 13:
                    System.out.print("Filter (All/Married): ");
                    system.getManagerControl().generateReport(scanner.nextLine());
                    break;
                case 14:
                    System.out.print("Enter new password: ");
                    String newPw = scanner.nextLine();
                    changePassword(newPw);
                    System.out.println("Password changed.");
                    break;
                case 15:
                    logout = true; 
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
    
}
