package edu.ntu.bto.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import edu.ntu.bto.model.Project;

public class ProjectTest {

    @Test
    public void testToggleVisibility() {
        Project project = new Project("Acacia Breeze", "Yishun", "2-Room", 10, 300000,
                                      "3-Room", 5, 450000, "2025-05-01", "2025-06-01",
                                      "T4567890D", 10, "");
        assertTrue(project.isVisible(), "Project should be visible by default");
        project.toggleVisibility();
        assertFalse(project.isVisible(), "Project should not be visible after toggle");
        project.toggleVisibility();
        assertTrue(project.isVisible(), "Project should be visible again after second toggle");
    }
}
