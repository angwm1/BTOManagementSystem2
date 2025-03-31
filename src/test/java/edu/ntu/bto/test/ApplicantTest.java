package edu.ntu.bto.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import edu.ntu.bto.model.Applicant;
import edu.ntu.bto.model.Project;

public class ApplicantTest {

    @Test
    public void testApplicationSubmission() {
        Applicant applicant = new Applicant("S1111111A", 36, "Single");
        Project project = new Project("Acacia Breeze", "Yishun", "2-Room", 10, 300000,
                                      "3-Room", 5, 450000, "2025-05-01", "2025-06-01",
                                      "T4567890D", 10, "");
        applicant.setAppliedProject(project);
        applicant.setApplicationStatus("Pending");
        assertNotNull(applicant.getAppliedProject(), "Applicant should have an applied project");
        assertEquals("Pending", applicant.getApplicationStatus(), "Application status should be 'Pending'");
    }
    
    @Test
    public void testWithdrawal() {
        Applicant applicant = new Applicant("S2222222B", 40, "Single");
        Project project = new Project("Acacia Breeze", "Yishun", "2-Room", 10, 300000,
                                      "3-Room", 5, 450000, "2025-05-01", "2025-06-01",
                                      "T4567890D", 10, "");
        applicant.setAppliedProject(project);
        applicant.setApplicationStatus("Pending");
        applicant.setApplicationStatus("Withdrawn");
        assertEquals("Withdrawn", applicant.getApplicationStatus(), "Application should be marked as Withdrawn");
    }
    
    @Test
    public void testEnquiryManagement() {
        Applicant applicant = new Applicant("S3333333C", 42, "Single");
        assertTrue(applicant.getEnquiries().isEmpty(), "Enquiries list should be empty initially");
        applicant.getEnquiries().add(new edu.ntu.bto.model.Enquiry("What is the deadline?", applicant.getNric()));
        assertFalse(applicant.getEnquiries().isEmpty(), "Enquiries list should not be empty after submission");
        applicant.getEnquiries().get(0).setText("What is the final deadline?");
        assertEquals("What is the final deadline?", applicant.getEnquiries().get(0).getText(), "Enquiry should be updated");
        applicant.getEnquiries().remove(0);
        assertTrue(applicant.getEnquiries().isEmpty(), "Enquiries list should be empty after deletion");
    }
    
    @Test
    public void testEligibilityCheck() {
        Applicant applicant = new Applicant("S4444444D", 30, "Single");
        assertFalse(applicant.getAge() >= 35, "Applicant should be ineligible if age is less than 35");
    }
}
