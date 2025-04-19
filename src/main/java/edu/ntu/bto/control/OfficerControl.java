package edu.ntu.bto.control;

import java.time.LocalDate;
import java.util.ArrayList;

import edu.ntu.bto.model.HDBOfficer;
import edu.ntu.bto.model.Project;
import edu.ntu.bto.model.Registration;

import java.util.List;

public class OfficerControl {
    private List<Registration> registrations;

    public OfficerControl(List<Registration> registrations) {
        this.registrations = registrations;
    }

    public boolean registerToProject(HDBOfficer officer, Project project) {
        // Ensure officer isn't already handling another project during this period
        for (Registration reg : officer.getRegistrations()) {
            if (reg.getProject().equals(project)) {
                System.out.println("You have already registered for this project.");
                return false;
            }
            if (overlaps(reg.getProject(), project) && reg.getStatus() == Registration.Status.APPROVED) {
                System.out.println("Cannot register for multiple projects within same application period.");
                return false;
            }
        }

        Registration registration = new Registration(officer, project);
        officer.getRegistrations().add(registration);
        registrations.add(registration);
        System.out.println("Registration submitted for approval.");
        return true;
    }

    private boolean overlaps(Project p1, Project p2) {
        LocalDate open1 = LocalDate.parse(p1.getOpenDate());
        LocalDate close1 = LocalDate.parse(p1.getCloseDate());
        LocalDate open2 = LocalDate.parse(p2.getOpenDate());
        LocalDate close2 = LocalDate.parse(p2.getCloseDate());

        return !(close1.isBefore(open2) || open1.isAfter(close2));
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public List<Registration> getRegistrationsByOfficer(HDBOfficer officer) {
        List<Registration> result = new ArrayList<>();
        for (Registration r : registrations) {
            if (r.getOfficer().equals(officer)) result.add(r);
        }
        return result;
    }

    public void approveRegistration(Registration reg) {
        reg.setStatus(Registration.Status.APPROVED);
        reg.getProject().decrementOfficerSlot();
        System.out.println("Officer registration approved.");
    }

    public void rejectRegistration(Registration reg) {
        reg.setStatus(Registration.Status.REJECTED);
        System.out.println("Officer registration rejected.");
    }
}

