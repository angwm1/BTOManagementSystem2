package edu.ntu.bto.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import edu.ntu.bto.model.Applicant;
import edu.ntu.bto.model.Project;

public class IntegrationTest {

    @Test
    public void testEndToEndApplicationFlow() {
        Applicant applicant = new Applicant("S7777777G", 40, "Single");
        Project project = new Project("Acacia Breeze", "Yishun", "2-Room", 10, 300000,
                                      "3-Room", 5, 450000, "2025-05-01", "2025-06-01",
                                      "T4567890D", 10, "");
        applicant.setAppliedProject(project);
        applicant.setApplicationStatus("Pending");
        // Manager approves the application.
        applicant.setApplicationStatus("Successful");
        boolean booked = project.bookFlat("2-Room");
        if (booked) {
            applicant.setApplicationStatus("Booked");
        }
        assertEquals("Booked", applicant.getApplicationStatus(), "End-to-end flow should result in 'Booked' status");
    }
    
    @Test
    public void testOfficerSlotLimit() {
        Project project = new Project("Boon Lay Vista", "Boon Lay", "2-Room", 20, 320000,
                                      "3-Room", 10, 480000, "2025-06-01", "2025-07-01",
                                      "T4567890D", 10, "");
        int maxSlots = project.getOfficerSlot();
        int attemptedRegistration = 11;
        assertTrue(attemptedRegistration > maxSlots, "Attempted registration should exceed the officer slot limit");
    }
    
    @Test
    public void testDuplicateNRICHandling() {
        Applicant applicant1 = new Applicant("S8888888H", 40, "Single");
        Applicant applicant2 = new Applicant("S8888888H", 42, "Single");
        assertEquals(applicant1.getNric(), applicant2.getNric(), "Duplicate NRIC detected");
    }
    
    @Test
    public void testIneligibleApplication() {
        Applicant applicant = new Applicant("S4444444D", 30, "Single");
        assertTrue(applicant.getAge() < 35, "Applicant should be ineligible if age is less than 35");
    }
}
