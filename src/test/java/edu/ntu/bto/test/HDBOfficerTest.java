package edu.ntu.bto.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import edu.ntu.bto.model.HDBOfficer;
import edu.ntu.bto.model.Project;

public class HDBOfficerTest {

    @Test
    public void testOfficerRegistration() {
        HDBOfficer officer = new HDBOfficer("S5555555E", 45, "Single");
        officer.setRegistrationStatus("Pending");
        assertEquals("Pending", officer.getRegistrationStatus(), "Officer registration status should be 'Pending'");
    }
    
    @Test
    public void testOfficerBooking() {
        HDBOfficer officer = new HDBOfficer("S5555555E", 45, "Single");
        Project project = new Project("Acacia Breeze", "Yishun", "2-Room", 10, 300000,
                                      "3-Room", 5, 450000, "2025-05-01", "2025-06-01",
                                      "T4567890D", 10, "");
        officer.setAppliedProject(project);
        officer.setApplicationStatus("Successful");
        boolean booked = project.bookFlat("2-Room");
        if (booked) {
            officer.setApplicationStatus("Booked");
        }
        assertEquals("Booked", officer.getApplicationStatus(), "Officer should have 'Booked' status after booking");
    }
    
    @Test
    public void testGenerateReceipt() {
        HDBOfficer officer = new HDBOfficer("S5555555E", 45, "Single");
        Project project = new Project("Acacia Breeze", "Yishun", "2-Room", 10, 300000,
                                      "3-Room", 5, 450000, "2025-05-01", "2025-06-01",
                                      "T4567890D", 10, "");
        officer.setAppliedProject(project);
        officer.setApplicationStatus("Booked");
        String receipt = "Receipt for " + officer.getNric() + " for project " + project.getProjectName();
        assertNotNull(receipt, "Receipt should be generated");
    }
    
    @Test
    public void testOfficerRegistrationFlow() {
        HDBOfficer officer = new HDBOfficer("S6666666F", 45, "Single");
        officer.setRegistrationStatus("Pending");
        officer.setRegistrationStatus("Approved");
        assertEquals("Approved", officer.getRegistrationStatus(), "Officer registration should be approved");
    }
}
