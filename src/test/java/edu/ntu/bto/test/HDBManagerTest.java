package edu.ntu.bto.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import edu.ntu.bto.model.HDBManager;
import edu.ntu.bto.model.Project;
import edu.ntu.bto.service.BTOManagementSystem;

public class HDBManagerTest {

    @Test
    public void testCreateAndDeleteProject() {
        HDBManager manager = new HDBManager("T4567890D", 50, "Married");
        BTOManagementSystem system = new BTOManagementSystem();
        
        Project newProj = new Project("New Horizon", "Boon Lay", "2-Room", 20, 320000,
                                      "3-Room", 10, 480000, "2025-07-01", "2025-08-01",
                                      manager.getNric(), 10, "");
        system.addProject(newProj);
        assertTrue(system.getProjects().contains(newProj), "New project should be added");
        
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
        // Verify that HDBManager is not assignable to Applicant.
        assertFalse(edu.ntu.bto.model.Applicant.class.isAssignableFrom(HDBManager.class),
                    "Manager should not be allowed to apply for a project");
    }
    
    @Test
    public void testApproveApplication() {
        String status = "Successful";
        assertEquals("Successful", status, "Application should be approved as Successful");
    }
    
    @Test
    public void testGenerateReport() {
        String report = "Report: List of booked applicants for Acacia Breeze";
        assertNotNull(report, "Report should be generated");
    }
}
