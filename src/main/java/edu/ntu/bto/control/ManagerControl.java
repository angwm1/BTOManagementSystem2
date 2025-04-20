package edu.ntu.bto.control;

import java.time.LocalDate;

import edu.ntu.bto.model.Applicant;
import edu.ntu.bto.model.Application;
import edu.ntu.bto.model.Project;
import edu.ntu.bto.model.Registration;

import java.util.List;

public class ManagerControl {
    private List<Project> projects;
    private List<Application> applications;
    private List<Registration> registrations;

    public ManagerControl(List<Project> projects, List<Application> applications,
                             List<Registration> registrations) {
        this.projects = projects;
        this.applications = applications;
        this.registrations = registrations;
    }

    public void createProject(String name, String neighborhood, String type1, int unitsType1, double priceType1, 
                            String type2, int unitsType2, double priceType2, String openDate, String closeDate, 
                            String manager) {
        // Check that manager is not handling another overlapping project
        for (Project p : projects) {
            if (p.getManager().equals(manager) && overlaps(p.getOpenDate(), p.getCloseDate(), openDate, closeDate)) {
                System.out.println("You are already managing another project in this period.");
                return;
            }
        }

        Project newProject = new Project(name, neighborhood, type1, unitsType1, priceType1,
                                      type2, unitsType2, priceType2, openDate, closeDate,
                                      manager);
        projects.add(newProject);
        System.out.println("Project created successfully.");
    }

    public void editProject(Project project, String name, String neighborhood, String type1, int unitsType1, double priceType1, 
                            String type2, int unitsType2, double priceType2, String openDate, String closeDate, 
                            String manager, int officerSlot, String officer) {
        if (project.getManager().equalsIgnoreCase(manager)) { // Demonstrate new neighborhood for demo
            projects.remove(project);
            Project newProj = new Project(name, neighborhood, type1, unitsType1, priceType1, 
                            type2, unitsType2, priceType2, openDate, closeDate, manager, officerSlot, officer);
            projects.add(newProj);
            System.out.println("Project updated.");
            return;
        }
        System.out.println("Failed to update project.");
    }

    public void deleteProject(Project project) {
        projects.remove(project);
        System.out.println("Project deleted.");
    }

    public void approveApplication(Application app) {
        String flatType = app.getFlatType();
        Project proj = app.getProject();
        if (flatType.equalsIgnoreCase(proj.getType1())) { // Approve as long as there are flats available, decrement accordingly
            if (proj.getUnitsType1() > 0) {
                proj.decrementUnitsType1();
                app.setStatus(Application.Status.SUCCESSFUL);
                System.out.println("Application approved.");
                return;
            }
        }
        else if (flatType.equalsIgnoreCase(proj.getType2())) {
            if (proj.getUnitsType2() > 0) {
                proj.decrementUnitsType2();
                app.setStatus(Application.Status.SUCCESSFUL);
                System.out.println("Application approved.");
                return;
            }
        }

        app.setStatus(Application.Status.UNSUCCESSFUL);
        System.out.println("Insufficient units. Application rejected.");
    }

    public void rejectApplication(Application app) {
        app.setStatus(Application.Status.UNSUCCESSFUL);
        System.out.println("Application rejected.");
    }

    public void approveWithdrawal(Applicant applicant) {
        System.out.println("Withdrawal approved.");
    }

    public void rejectWithdrawal(Applicant applicant) {
        System.out.println("Withdrawal request rejected.");
    }

    public void approveOfficer(Registration reg) {
        reg.setStatus(Registration.Status.APPROVED);
        reg.getProject().decrementOfficerSlot();
        System.out.println("Officer registration approved.");
    }

    public void rejectOfficer(Registration reg) {
        reg.setStatus(Registration.Status.REJECTED);
        System.out.println("Officer registration rejected.");
    }

    public void generateReport(String filterBy) {
        System.out.println("-- Report: Applicants with Booked Flats --");
        for (Application app : applications) {
            if (app.getStatus() == Application.Status.BOOKED) {
                Applicant a = app.getApplicant();
                if (filterBy.equalsIgnoreCase("Married") && !a.getMaritalStatus().equalsIgnoreCase("Married")) continue;
                System.out.printf("NRIC: %s, Age: %d, Marital Status: %s, Project: %s, Flat Booked: %s\n",
                                  a.getNric(), a.getAge(), a.getMaritalStatus(),
                                  app.getProject().getProjectName(), app.getFlatType());
            }
        }
    }

    private boolean overlaps(String s1, String e1, String s2, String e2) {
        LocalDate start1 = LocalDate.parse(s1);
        LocalDate end1 = LocalDate.parse(e1);
        LocalDate start2 = LocalDate.parse(s2);
        LocalDate end2 = LocalDate.parse(e2);

        return !(end1.isBefore(start2) || start1.isAfter(end2));
    }
}
