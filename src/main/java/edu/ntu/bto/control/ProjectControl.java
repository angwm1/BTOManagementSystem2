package edu.ntu.bto.control;

import java.util.ArrayList;

import edu.ntu.bto.model.Applicant;
import edu.ntu.bto.model.Project;
import java.util.List;

public class ProjectControl {
    private List<Project> projects;

    public ProjectControl(List<Project> projects) {
        this.projects = projects;
    }

    public List<Project> getVisibleProjectsForApplicant(Applicant applicant) {
        List<Project> result = new ArrayList<>();
        for (Project p : projects) {
            if (!p.isVisible()) continue;
            if (applicant.getMaritalStatus().equalsIgnoreCase("Single") && applicant.getAge() >= 35) {
                if (p.getUnitsType1() != 0) result.add(p);
            } else if (applicant.getMaritalStatus().equalsIgnoreCase("Married") && applicant.getAge() >= 21) {
                result.add(p);
            }
        }
        return result;
    }

    public List<Project> filterByNeighborhood(List<Project> projList, String keyword) {
        List<Project> result = new ArrayList<>();
        for (Project p : projList) {
            if (p.getNeighborhood().equalsIgnoreCase(keyword)) result.add(p);
        }
        return result;
    }

    public List<Project> filterByFlatType(List<Project> projList, String flatType) {
        List<Project> result = new ArrayList<>();
        for (Project p : projList) {
            if (flatType.equalsIgnoreCase(p.getType1())) {
                if (p.getUnitsType1() != 0) {
                    result.add(p);
                }
            }
            else {
                if (p.getUnitsType2() != 0) {
                    result.add(p);
                }
            }
        }
        return result;
    }

    public void toggleProjectVisibility(Project project) {
        project.toggleVisibility();
        System.out.println("Project visibility successfully toggled to " + project.isVisible());
    }

    public List<Project> getAllProjects() {
        return projects;
    }

    public List<Project> getProjectsByManager(String manager) {
        List<Project> result = new ArrayList<>();
        for (Project p : projects) {
            if (p.getManager().equals(manager)) {
                result.add(p);
            }
        }
        return result;
    }
}
