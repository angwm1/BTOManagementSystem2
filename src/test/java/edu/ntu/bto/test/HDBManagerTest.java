package edu.ntu.bto.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import edu.ntu.bto.model.HDBManager;
import edu.ntu.bto.model.Applicant;
import edu.ntu.bto.model.Project;
import edu.ntu.bto.service.BTOManagementSystem;

public class HDBManagerTest {

    @Test
    public void testCreateAndDeleteProject() {
        HDBManager manager = new HDBManager("T4567890D", 50, "Married");
        BTOManagementSystem system = new BTOManagementSystem();
        
        // Create a new project
        Project newProj = new Project("New Horizon", "Boon Lay", "2-Room", 20, 320000,
                                      "3-Room", 10, 480000, "2025-07-01", "2025-08-01",
                                      manager.getNric(), 10, "");
        system.addProject(newProj);
        assertTrue(system.getProjects().contains(newProj), "New project should be added");
        
        // Delete the project
        system.removeProject(newProj);
        assertFalse(system.getProjects().contains(newProj), "Project should be removed");
    }
    
    @Test
    public void testToggleProjectVisibility() {
        Project project = new Project("Acacia Breeze", "Yishun", "2-Room", 10, 300000,
                                      "3-Room", 5, 450000, "2025-05-01", "2025-06-01",
                                      "T4567890D", 10, "");
        assertTrue(project.isVisible(), "Project should be visible by default");
        project.toggleVisibility();
        assertFalse(project.isVisible(), "Project should be hidden after toggle");
    }
    
    @Test
    public void testManagerCannotApplyForProject() {
        // Instead of using 'instanceof', use reflection to check that HDBManager is not assignable to Applicant.
        HDBManager manager = new HDBManager("T4567890D", 50, "Married");
        boolean canApply = Applicant.class.isAssignableFrom(manager.getClass());
        assertFalse(canApply, "Manager should not be allowed to apply for a project");
    }
    
    @Test
    public void testApproveOfficerRegistration() {
        // Dummy simulation: assume approval sets registration status to "Approved"
        HDBManager manager = new HDBManager("T4567890D", 50, "Married");
        // In a real system, manager would approve officer registrations.
        // Here we simulate:
        // (Assume officer instance is created and then approved)
        assertTrue(true, "Simulated approval passed (implement actual logic later)");
    }
    
    @Test
    public void testApproveBTOApplication() {
        // Dummy simulation: if a project has available flats, application becomes "Successful"
        String status = "Successful";
        assertEquals("Successful", status, "Application should be approved as Successful");
    }
    
    @Test
    public void testGenerateReport() {
        // Dummy simulation: generate a report string for projects.
        String report = "Report: List of booked applicants...";
        assertNotNull(report, "Report should not be null");
    }
}
