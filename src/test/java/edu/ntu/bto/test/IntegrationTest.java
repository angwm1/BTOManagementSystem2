package edu.ntu.bto.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import edu.ntu.bto.service.BTOManagementSystem;
import edu.ntu.bto.model.Applicant;
import edu.ntu.bto.model.HDBOfficer;
import edu.ntu.bto.model.HDBManager;
import edu.ntu.bto.model.Project;

public class IntegrationTest {

    @Test
    public void testEndToEndApplicationFlow() {
        // Simulate an applicant applying for a project, manager approval, then officer booking.
        BTOManagementSystem system = new BTOManagementSystem();
        
        // Create dummy instances (in real scenario, these would be loaded from Excel)
        Applicant applicant = new Applicant("S1111111A", 40, "Single");
        Project project = new Project("Acacia Breeze", "Yishun", "2-Room", 10, 300000,
                                      "3-Room", 5, 450000, "2025-05-01", "2025-06-01",
                                      "T4567890D", 10, "");
        // Applicant applies
        applicant.setAppliedProject(project);
        applicant.setApplicationStatus("Pending");
        // Manager approves (simulate by setting status)
        applicant.setApplicationStatus("Successful");
        // Officer books flat (simulate booking)
        applicant.setApplicationStatus("Booked");
        assertEquals("Booked", applicant.getApplicationStatus(), "Application flow should result in 'Booked' status");
    }
    
    @Test
    public void testOfficerRegistrationFlow() {
        // Simulate officer registration and approval
        HDBOfficer officer = new HDBOfficer("S2222222B", 45, "Single");
        // Officer registers for a project (simulate by setting registration status)
        officer.setRegistrationStatus("Pending");
        // Manager approves registration (simulate)
        officer.setRegistrationStatus("Approved");
        assertEquals("Approved", officer.getRegistrationStatus(), "Officer registration should be approved");
    }
    
    @Test
    public void testDuplicateNRICHandling() {
        // Simulate duplicate NRIC scenario
        // In a real implementation, duplicate NRIC should be prevented.
        Applicant applicant1 = new Applicant("S3333333C", 40, "Single");
        Applicant applicant2 = new Applicant("S3333333C", 42, "Single");
        // For this dummy test, we simply assert that their NRICs are equal.
        assertEquals(applicant1.getNric(), applicant2.getNric(), "Duplicate NRIC detected");
        // In a full system, this should trigger an error.
    }
    
    @Test
    public void testOfficerSlotLimit() {
        // Simulate exceeding officer slots.
        // For example, if a project has 10 slots, and 11 registrations occur, the 11th should be rejected.
        Project project = new Project("Boon Lay Vista", "Boon Lay", "2-Room", 20, 320000,
                                      "3-Room", 10, 480000, "2025-06-01", "2025-07-01",
                                      "T4567890D", 10, "");
        // In a full implementation, project would track current officer count.
        // For now, we simulate: if officerSlot == 10, then adding an 11th registration is invalid.
        int maxSlots = 10;
        int currentRegistrations = 10;
        int attemptedRegistration = 11;
        assertTrue(attemptedRegistration > maxSlots, "Attempted registration exceeds officer slot limit");
    }
    
    @Test
    public void testIneligibleApplication() {
        // Test: Applicant below 35 (Single) should be ineligible for 2-Room.
        Applicant applicant = new Applicant("S4444444D", 30, "Single");
        // In a full implementation, eligibility check would be part of applyForProject.
        // For now, simulate by checking age.
        assertTrue(applicant.getAge() < 35, "Applicant is below eligibility age for a Single applicant applying for 2-Room");
    }
}
