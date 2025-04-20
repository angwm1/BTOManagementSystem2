package edu.ntu.bto.model;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import edu.ntu.bto.service.BTOManagementSystem;

/**
 * Represents an HDB Officer.
 * HDB Officers have all the capabilities of Applicants plus additional officer-specific operations.
 */
public class HDBOfficer extends Applicant {
    private List<Registration> Registrations = new ArrayList<>();
    
    public HDBOfficer(String nric, int age, String maritalStatus) {
        super(nric, age, maritalStatus);
    }
    
    public HDBOfficer(String nric, int age, String maritalStatus, String password) {
        super(nric, age, maritalStatus, password);
    }
    
    public List<Registration> getRegistrations() {
        return Registrations;
    }
    
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
                    System.out.println("Projects available to you:");
                    system.getProjectControl().getVisibleProjectsForApplicant((Applicant) this).forEach(p ->
                        System.out.println(p.toString()));
                    break;
                case 2: 
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
                    Application app = system.getApplicationControl().getApplicationByApplicant((Applicant) this);
                    if (app != null) System.out.println("Status: " + app.getStatus());
                    else System.out.println("No application.");
                    break;
                case 4: 
                    system.getApplicationControl().withdraw((Applicant) this);
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
                            system.getEnquiryControl().submitEnquiry((Applicant) this, p, q);
                            break;
                        }
                    }
                    if (!eFound) {
                        System.out.println("No such project found.");
                    }
                    break;
                case 6: 
                    system.getEnquiryControl().getEnquiriesByApplicant((Applicant) this).forEach(e ->
                    System.out.println("[" + e.getId() + "] " + e.getQuestion()));
                    break;
                case 7: 
                    System.out.print("Enquiry ID: ");
                    String eidEdit = scanner.nextLine();
                    System.out.print("New question: ");
                    system.getEnquiryControl().editEnquiry(eidEdit, scanner.nextLine(), (Applicant) this);
                    break;
                case 8:
                    System.out.print("Enquiry ID: ");
                    system.getEnquiryControl().deleteEnquiry(scanner.nextLine(), (Applicant) this);
                    break;
                case 9:
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
                            }
                            else {
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
                    getRegistrations().forEach(r ->
                        System.out.println(r.getProject().getProjectName() + " - " + r.getStatus()));
                    break;
                case 11:
                    System.out.println("Current Handled Project:");
                    getRegistrations().stream()
                        .filter(r -> r.getStatus() == Registration.Status.APPROVED)
                        .map(Registration::getProject)
                        .forEach(p -> {
                            System.out.println(p.toString());
                            System.out.println("All applications for this project:");
                            for (Application a : system.getApplicationControl().getApplications()) {
                                if (a.getProject().equals(p)) {
                                    System.out.println("Applicant " + a.getApplicant().getNric() 
                                    + " applying for flat type: " + a.getFlatType() 
                                    + " - Status: " + a.getStatus());
                                }
                            }
                        });
                    break;
                case 12:
                    getRegistrations().stream()
                        .filter(r -> r.getStatus() == Registration.Status.APPROVED)
                        .flatMap(r -> system.getEnquiryControl().getEnquiriesByProject(r.getProject()).stream())
                        .forEach(e -> System.out.println(e.getApplicant().getNric() + ": " + e.getQuestion()));
                    break;
                case 13:
                    getRegistrations().stream()
                        .filter(r -> r.getStatus() == Registration.Status.APPROVED)
                        .forEach(r -> system.getEnquiryControl().getEnquiriesByProject(r.getProject()).forEach(e -> {
                            if (e.getResponse() == null) {
                                System.out.println("Question: " + e.getQuestion());
                                System.out.print("Reply: ");
                                system.getEnquiryControl().replyEnquiry(e.getId(), scanner.nextLine());
                            }
                        }));
                    break;
                case 14:
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
                    System.out.print("Enter new password: ");
                    String newPw = scanner.nextLine();
                    changePassword(newPw);
                    System.out.println("Password changed.");
                    break;
                case 16:
                    logout = true; 
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

}
