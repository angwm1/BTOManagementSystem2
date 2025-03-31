package edu.ntu.bto.test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import edu.ntu.bto.model.Applicant;
import edu.ntu.bto.model.HDBManager;
import edu.ntu.bto.model.HDBOfficer;
import edu.ntu.bto.model.Project;
import edu.ntu.bto.util.DataLoader;

public class DataLoaderTest {

    @Test
    public void testLoadApplicants() {
        List<Applicant> applicants = DataLoader.loadApplicants("ApplicantList.xlsx");
        assertNotNull(applicants, "Applicants list should not be null");
        assertFalse(applicants.isEmpty(), "Applicants list should not be empty");
    }
    
    @Test
    public void testLoadManagers() {
        List<HDBManager> managers = DataLoader.loadManagers("ManagerList.xlsx");
        assertNotNull(managers, "Managers list should not be null");
        assertFalse(managers.isEmpty(), "Managers list should not be empty");
    }
    
    @Test
    public void testLoadOfficers() {
        List<HDBOfficer> officers = DataLoader.loadOfficers("OfficerList.xlsx");
        assertNotNull(officers, "Officers list should not be null");
        assertFalse(officers.isEmpty(), "Officers list should not be empty");
    }
    
    @Test
    public void testLoadProjects() {
        List<Project> projects = DataLoader.loadProjects("ProjectList.xlsx");
        assertNotNull(projects, "Projects list should not be null");
        assertFalse(projects.isEmpty(), "Projects list should not be empty");
    }
}
