package edu.ntu.bto.control;

import java.util.ArrayList;

import edu.ntu.bto.model.Applicant;
import edu.ntu.bto.model.Project;
import java.util.List;

/**
 * Control class for managing projects. ProjectControl provides functionalities
 * to filter projects by applicant eligibility or criteria, toggle project
 * visibility, and retrieve projects lists for various purposes.
 * 
 * @author SC2002 Assignment Group
 */
public class ProjectControl {
	private List<Project> projects;

	/**
	 * Constructs a new ProjectControl with the given list of projects.
	 * 
	 * @param projects The list of {@link Project}s to manage.
	 */
	public ProjectControl(List<Project> projects) {
		this.projects = projects;
	}

	/**
	 * Retrieves the list of projects visible to a given applicant. Only projects
	 * that are marked visible and open to the applicant's demographic (based on
	 * marital status and age requirements) are included.
	 * 
	 * @param applicant The applicant for whom to fetch visible projects.
	 * @return A list of visible {@link Project}s that the applicant is eligible to
	 *         view/apply for.
	 */
	public List<Project> getVisibleProjectsForApplicant(Applicant applicant) {
		List<Project> result = new ArrayList<>();
		for (Project p : projects) {
			if (!p.isVisible())
				continue;
			if (applicant.getMaritalStatus().equalsIgnoreCase("Single") && applicant.getAge() >= 35) {
				if (p.getUnitsType1() != 0)
					result.add(p);
			} else if (applicant.getMaritalStatus().equalsIgnoreCase("Married") && applicant.getAge() >= 21) {
				result.add(p);
			}
		}
		return result;
	}

	/**
	 * Filters projects by a keyword in their neighborhood.
	 * 
	 * @param keyword The neighborhood keyword to search for.
	 * @return A list of {@link Project}s whose neighborhood contains the given
	 *         keyword (case-insensitive).
	 */
	public List<Project> filterByNeighborhood(List<Project> projList, String keyword) {
		List<Project> result = new ArrayList<>();
		for (Project p : projList) {
			if (p.getNeighborhood().equalsIgnoreCase(keyword))
				result.add(p);
		}
		return result;
	}

	/**
	 * Filters projects by a given flat type.
	 * 
	 * @param flatType The flat type to filter by (e.g., "2-Room" or "3-Room").
	 * @return A list of {@link Project}s that offer the specified flat type.
	 */
	public List<Project> filterByFlatType(List<Project> projList, String flatType) {
		List<Project> result = new ArrayList<>();
		for (Project p : projList) {
			if (flatType.equalsIgnoreCase(p.getType1())) {
				if (p.getUnitsType1() != 0) {
					result.add(p);
				}
			} else {
				if (p.getUnitsType2() != 0) {
					result.add(p);
				}
			}
		}
		return result;
	}

	/**
	 * Toggles the visibility of a given project. If the project is currently
	 * visible to applicants, it will be hidden, and vice versa.
	 * 
	 * @param project The {@link Project} whose visibility is to be toggled.
	 */
	public void toggleProjectVisibility(Project project) {
		project.toggleVisibility();
		System.out.println("Project visibility successfully toggled to " + project.isVisible());
	}

	/**
	 * Returns the list of all projects.
	 * 
	 * @return A list of all {@link Project}s in the system.
	 */
	public List<Project> getAllProjects() {
		return projects;
	}

	/**
	 * Retrieves all projects managed by a specific manager.
	 * 
	 * @param manager The identifier (name or NRIC) of the manager.
	 * @return A list of {@link Project}s where the manager field matches the given
	 *         identifier.
	 */
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
