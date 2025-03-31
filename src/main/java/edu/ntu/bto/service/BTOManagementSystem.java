package edu.ntu.bto.service;

import java.util.ArrayList;
import java.util.List;
import edu.ntu.bto.model.*;
import edu.ntu.bto.util.DataLoader;

/**
 * Main system class that ties together users and projects.
 */
public class BTOManagementSystem {
    private List<User> users;
    private List<Project> projects;
    
    public BTOManagementSystem() {
        users = new ArrayList<>();
        projects = new ArrayList<>();
        
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
        
        System.out.println("Data loaded: " + users.size() + " users, " + projects.size() + " projects.");
    }
    
    public List<Project> getProjects() {
        return projects;
    }
    
    public void addProject(Project proj) {
        projects.add(proj);
    }
    
    public void removeProject(Project proj) {
        projects.remove(proj);
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
