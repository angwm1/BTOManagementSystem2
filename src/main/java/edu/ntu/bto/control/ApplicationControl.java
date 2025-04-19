package edu.ntu.bto.control;

import edu.ntu.bto.model.Applicant;
import edu.ntu.bto.model.Application;
import edu.ntu.bto.model.HDBOfficer;
import edu.ntu.bto.model.Project;
import java.util.List;

public class ApplicationControl {
    private List<Application> applications;

    public ApplicationControl(List<Application> applications) {
        this.applications = applications;
    }

    public boolean apply(Applicant applicant, Project project, String flatType) {
        // Check constraints
        if (applicant.getApplication() != null && 
            !applicant.getApplication().getStatus().equals(Application.Status.UNSUCCESSFUL)) {
            System.out.println("You have already applied or booked a flat. Withdraw or wait for result.");
            return false;
        }

        if (flatType.equals("2-Room") && !(applicant.getMaritalStatus().equals("Single") && applicant.getAge() >= 35)) {
            System.out.println("Only single applicants aged 35+ can apply for 2-Room.");
            return false;
        } else if (flatType.equals("3-Room") && !(applicant.getMaritalStatus().equals("Married") && applicant.getAge() >= 21)) {
            System.out.println("Only married applicants aged 21+ can apply for 3-Room.");
            return false;
        }

        Application app = new Application(applicant, project, flatType);
        applicant.setApplication(app);
        applications.add(app);
        System.out.println("Application submitted successfully.");
        return true;
    }

    public void withdraw(Applicant applicant) {
        Application app = applicant.getApplication();
        if (app == null) {
            System.out.println("No application found.");
            return;
        }
        applications.remove(app);
        applicant.setApplication(null);
        System.out.println("Application withdrawn.");
    }

    public void updateStatus(Application app, Application.Status status) {
        app.setStatus(status);
    }

    public void bookFlat(HDBOfficer officer, Applicant applicant, String flatType) {
        Application app = applicant.getApplication();
        if (app == null || app.getStatus() != Application.Status.SUCCESSFUL) {
            System.out.println("Booking not allowed. Application must be successful.");
            return;
        }

        Project project = app.getProject();
        if (flatType.equalsIgnoreCase(project.getType1())) {
            if (project.getUnitsType1() == 0) {
                System.out.println("No units left for this flat type.");
                return;
            }
        }
        else {
            if (project.getUnitsType2() == 0) {
                System.out.println("No units left for this flat type.");
                return;
            }
        }

        // Decrement no. of units accordingly
        if (flatType.equalsIgnoreCase(project.getType1())) {
            project.decrementUnitsType1();
        }
        else {
            project.decrementUnitsType2();
        }

        app.setStatus(Application.Status.BOOKED);
        System.out.println("Flat booked successfully for " + applicant.getNric());
    }

    public List<Application> getApplications() {
        return applications;
    }

    public Application getApplicationByApplicant(Applicant applicant) {
        return applicant.getApplication();
    }
}

