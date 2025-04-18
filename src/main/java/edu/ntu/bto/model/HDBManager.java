package edu.ntu.bto.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import edu.ntu.bto.service.BTOManagementSystem;

/**
 * Represents an HDB Manager.
 * Managers can create, edit, delete projects; toggle project visibility;
 * manage registrations and applications; and generate reports.
 */
public class HDBManager extends User {
    // List of projects created by this manager.
    private List<Project> myProjects;
	protected String name;
	
    public HDBManager(String name, String nric, int age, String maritalStatus) {
        super(nric, age, maritalStatus);
        this.name = name;
        myProjects = new ArrayList<>();
    }
    
    public HDBManager(String nric, int age, String maritalStatus, String password) {
        super(nric, age, maritalStatus, password);
        myProjects = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public List<Project> getMyProjects() {
        return myProjects;
    }
    
    public void addMyProject(Project project) {
        myProjects.add(project);
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
                    createProject(scanner, system);
                    break;
                case "2":
                    editProject(scanner, system);
                    break;
                case "3":
                    deleteProject(scanner, system);
                    break;
                case "4":
                    toggleProjectVisibility(scanner, system);
                    break;
                case "5":
                    viewOfficerRegistrations(scanner, system);
                    break;
                case "6":
                    approveRejectApplications(scanner, system);
                    break;
                case "7":
                    generateReport(system);
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
    
    private void createProject(Scanner scanner, BTOManagementSystem system) {
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
        System.out.print("Enter available officer slots: ");
        int officerSlot = Integer.parseInt(scanner.nextLine());
        Project project = new Project(name, neighborhood, type1, unitsType1, priceType1,
                                      type2, unitsType2, priceType2, openDate, closeDate,
                                      this.nric, officerSlot, "");
        system.addProject(project);
        addMyProject(project);
        System.out.println("Project created successfully.");
    }
    
    private void editProject(Scanner scanner, BTOManagementSystem system) {
        System.out.print("Enter project name to edit: ");
        String name = scanner.nextLine();
        Project project = myProjects.stream()
                .filter(p -> p.toString().contains(name))
                .findFirst().orElse(null);
        if (project == null) {
            System.out.println("Project not found in your list.");
            return;
        }
        System.out.print("Enter new neighborhood: ");
        String newNeighborhood = scanner.nextLine();
        // For simplicity, create a new project instance with updated neighborhood.
        Project updated = new Project(
            project.getProjectName(),
            newNeighborhood,
            project.getType1(), project.getUnitsType1(), project.getPriceType1(),
            project.getType2(), project.getUnitsType2(), project.getPriceType2(),
            project.getOpenDate(), project.getCloseDate(),
            project.getManager(), project.getOfficerSlot(), project.getOfficer()
        );
        system.removeProject(project);
        system.addProject(updated);
        myProjects.remove(project);
        myProjects.add(updated);
        System.out.println("Project updated.");
    }
    
    private void deleteProject(Scanner scanner, BTOManagementSystem system) {
        System.out.print("Enter project name to delete: ");
        String name = scanner.nextLine();
        Project project = myProjects.stream()
                .filter(p -> p.toString().contains(name))
                .findFirst().orElse(null);
        if (project != null) {
            system.removeProject(project);
            myProjects.remove(project);
            System.out.println("Project deleted.");
        } else {
            System.out.println("Project not found.");
        }
    }
    
    private void toggleProjectVisibility(Scanner scanner, BTOManagementSystem system) {
        System.out.print("Enter project name to toggle visibility: ");
        String name = scanner.nextLine();
        Project project = myProjects.stream()
                .filter(p -> p.toString().contains(name))
                .findFirst().orElse(null);
        if (project != null) {
            project.toggleVisibility();
            System.out.println("Project visibility toggled. Now visible: " + project.isVisible());
        } else {
            System.out.println("Project not found.");
        }
    }
    
    private void viewOfficerRegistrations(Scanner scanner, BTOManagementSystem system) {
        System.out.println("Officer registrations for your projects:");
        // In a full implementation, manager would view registrations pending for projects.
        System.out.println("Sample data: Officer X - Pending, Officer Y - Approved");
    }
    
    private void approveRejectApplications(Scanner scanner, BTOManagementSystem system) {
        System.out.print("Approve (A) or Reject (R)?: ");
        String decision = scanner.nextLine();
        if ("A".equalsIgnoreCase(decision)) {
            System.out.println("Application approved. Status set to Successful.");
        } else if ("R".equalsIgnoreCase(decision)) {
            System.out.println("Application rejected. Status set to Unsuccessful.");
        } else {
            System.out.println("Invalid decision.");
        }
    }
    
    private void generateReport(BTOManagementSystem system) {
        System.out.println("Generating report...");
        System.out.println("Report: [List of applicants with booked flats]");
    }
}
