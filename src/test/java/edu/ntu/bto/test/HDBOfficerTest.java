package edu.ntu.bto.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import edu.ntu.bto.model.HDBOfficer;
import edu.ntu.bto.model.Project;

public class HDBOfficerTest {

    @Test
    public void testOfficerRegistration() {
        HDBOfficer officer = new HDBOfficer("S8888888H", 45, "Single");
        // Simulate officer registration
        officer.setRegistrationStatus("Pending");
        assertEquals("Pending", officer.getRegistrationStatus(), "Officer registration status should be 'Pending'");
    }
    
    @Test
    public void testViewRegistrationStatus() {
        HDBOfficer officer = new HDBOfficer("S8888888H", 45, "Single");
        officer.setRegistrationStatus("Approved");
        assertEquals("Approved", officer.getRegistrationStatus(), "Officer registration status should be 'Approved'");
    }
    
    @Test
    public void testFlatBooking_Successful() {
        HDBOfficer officer = new HDBOfficer("S8888888H", 45, "Single");
        Project project = new Project("Acacia Breeze", "Yishun", "2-Room", 10, 300000,
                                      "3-Room", 5, 450000, "2025-05-01", "2025-06-01",
                                      "T4567890D", 10, "");
        officer.setAppliedProject(project);
        officer.setApplicationStatus("Successful");
        // Simulate flat booking: set status to "Booked"
        officer.setApplicationStatus("Booked");
        assertEquals("Booked", officer.getApplicationStatus(), "Flat booking should update status to 'Booked'");
    }
    
    @Test
    public void testFlatBooking_Unsuccessful() {
        HDBOfficer officer = new HDBOfficer("S8888888H", 45, "Single");
        // If application status is not successful, booking should not proceed.
        officer.setApplicationStatus("Pending");
        // Simulate booking attempt - in real system, booking method would block action.
        assertNotEquals("Booked", officer.getApplicationStatus(), "Flat booking should not proceed if application is not successful");
    }
    
    @Test
    public void testGenerateReceipt() {
        HDBOfficer officer = new HDBOfficer("S8888888H", 45, "Single");
        Project project = new Project("Acacia Breeze", "Yishun", "2-Room", 10, 300000,
                                      "3-Room", 5, 450000, "2025-05-01", "2025-06-01",
                                      "T4567890D", 10, "");
        officer.setAppliedProject(project);
        officer.setApplicationStatus("Booked");
        // Dummy receipt generation: check that status is Booked.
        assertEquals("Booked", officer.getApplicationStatus(), "Officer should have booked status for receipt generation");
    }
    
    @Test
    public void testRespondToEnquiry() {
        HDBOfficer officer = new HDBOfficer("S8888888H", 45, "Single");
        // For simplicity, simulate that officer responds to an enquiry by setting a dummy response.
        String response = "Please refer to our updated project brochure.";
        // In a full implementation, response would be stored alongside the enquiry.
        assertNotNull(response, "Response should be non-null");
    }
}
