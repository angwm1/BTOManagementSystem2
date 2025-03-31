package edu.ntu.bto.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import edu.ntu.bto.service.BTOManagementSystem;
import edu.ntu.bto.model.User;
import edu.ntu.bto.model.Applicant;

public class BTOManagementSystemTest {
    
    private static BTOManagementSystem system;
    
    @BeforeAll
    public static void setup() {
        system = new BTOManagementSystem();
    }
    
    @Test
    public void testValidUserLogin() {
        // Ensure "S1234567A" exists in your ApplicantList.xlsx.
        User user = system.login("S1234567A", "password");
        assertNotNull(user, "Valid login should return a user");
        assertTrue(user instanceof Applicant, "User should be an Applicant");
    }
    
    @Test
    public void testInvalidPassword() {
        User user = system.login("S1234567A", "wrongpassword");
        assertNull(user, "Login with an incorrect password should return null");
    }
    
    @Test
    public void testPasswordChange() {
        User user = system.login("S1234567A", "password");
        assertNotNull(user, "User should login with default password");
        user.changePassword("newpass123");
        User userNew = system.login("S1234567A", "newpass123");
        assertNotNull(userNew, "User should login with new password");
        User userOld = system.login("S1234567A", "password");
        assertNull(userOld, "User should not login with old password after change");
    }
    
    @Test
    public void testDataLoaded() {
        assertFalse(system.getProjects().isEmpty(), "Projects should be loaded");
    }
}
