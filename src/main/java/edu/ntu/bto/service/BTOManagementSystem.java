package edu.ntu.bto.service;

import java.util.ArrayList;
import java.util.List;

import edu.ntu.bto.control.ProjectControl;
import edu.ntu.bto.control.ApplicationControl;
import edu.ntu.bto.control.OfficerControl;
import edu.ntu.bto.control.ManagerControl;
import edu.ntu.bto.control.EnquiryControl;
import edu.ntu.bto.model.*;
import edu.ntu.bto.util.DataLoader;

/**
 * Main system class that ties together users and projects.
 */
public class BTOManagementSystem {
    private List<User> users;
    private List<Project> projects;
    private List<Application> applications;
    private List<Registration> registrations;
    private List<Enquiry> enquiries;
    private final ProjectControl pc;
    private final ApplicationControl ac;
    private final OfficerControl oc;
    private final ManagerControl mc;
    private final EnquiryControl ec;
    
    public BTOManagementSystem() {
        users = new ArrayList<>();
        projects = new ArrayList<>();
        applications = new ArrayList<>();
        registrations = new ArrayList<>();
        enquiries = new ArrayList<>();
        
        // File names – DataLoader expects these in the "data" folder.
        String applicantFile = "ApplicantList.xlsx";
        String managerFile = "ManagerList.xlsx";
        String officerFile = "OfficerList.xlsx";
        String projectFile = "ProjectList.xlsx";
        
        // Load users.
        users.addAll(DataLoader.loadApplicants(applicantFile));
        users.addAll(DataLoader.loadManagers(managerFile));
        users.addAll(DataLoader.loadOfficers(officerFile));
        
        // Load projects.
        projects.addAll(DataLoader.loadProjects(projectFile));

        // Setup control
        pc = new ProjectControl(projects);
        ac = new ApplicationControl(applications);
        oc = new OfficerControl(registrations);
        mc = new ManagerControl(projects, applications, registrations);
        ec = new EnquiryControl(enquiries);
        
        System.out.println("Data loaded: " + users.size() + " users, " + projects.size() + " projects.");
    }
    
    public List<Project> getProjects() {
        return projects;
    }

    public List<User> getUsers() {
        return users;
    }

    public ProjectControl getProjectControl() {
        return pc;
    }

    public ApplicationControl getApplicationControl() {
        return ac;
    }

    public OfficerControl getOfficerControl() {
        return oc;
    }

    public ManagerControl getManagerControl() {
        return mc;
    }

    public EnquiryControl getEnquiryControl() {
        return ec;
    }
    
    /**
     * Simple login method based on NRIC and password.
     */
    public User login(String nric, String password) {
        for (User u : users) {
            if (u.getNric().equalsIgnoreCase(nric) && u.checkPassword(password)) {
                return u;
            }
        }
        return null;
    }
}
