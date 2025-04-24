package edu.ntu.bto.service;

import java.util.ArrayList;
import java.util.List;

import edu.ntu.bto.control.ProjectControl;
import edu.ntu.bto.control.ApplicationControl;
import edu.ntu.bto.control.OfficerControl;
import edu.ntu.bto.control.ManagerControl;
import edu.ntu.bto.control.EnquiryControl;
import edu.ntu.bto.model.*;
import edu.ntu.bto.util.DataLoader;

/**
 * Main system class that initializes and ties together users, projects, and
 * control classes. The BTOManagementSystem loads data for users and projects
 * from files, maintains central lists of these objects, and provides methods
 * for user login and access to control components.
 * 
 * @author SC2002 Assignment Group
 */
public class BTOManagementSystem {
	private List<User> users;
	private List<Project> projects;
	private List<Application> applications;
	private List<Registration> registrations;
	private List<Enquiry> enquiries;
	private final ProjectControl pc;
	private final ApplicationControl ac;
	private final OfficerControl oc;
	private final ManagerControl mc;
	private final EnquiryControl ec;

	/**
	 * Constructs the BTOManagementSystem, loading initial data and initializing
	 * control classes. User data (applicants, officers, managers) and project data
	 * are loaded from predefined files. The system then creates control objects for
	 * managing projects, applications, officer registrations, manager actions, and
	 * enquiries.
	 */
	public BTOManagementSystem() {
		users = new ArrayList<>();
		projects = new ArrayList<>();
		applications = new ArrayList<>();
		registrations = new ArrayList<>();
		enquiries = new ArrayList<>();

		// File names – DataLoader expects these in the "data" folder.
		String applicantFile = "ApplicantList.xlsx";
		String managerFile = "ManagerList.xlsx";
		String officerFile = "OfficerList.xlsx";
		String projectFile = "ProjectList.xlsx";

		// Load users.
		users.addAll(DataLoader.loadApplicants(applicantFile));
		users.addAll(DataLoader.loadManagers(managerFile));
		users.addAll(DataLoader.loadOfficers(officerFile));

		// Load projects.
		projects.addAll(DataLoader.loadProjects(projectFile));

		// Setup control
		pc = new ProjectControl(projects);
		ac = new ApplicationControl(applications);
		oc = new OfficerControl(registrations);
		mc = new ManagerControl(projects, applications, registrations);
		ec = new EnquiryControl(enquiries);

		System.out.println("Data loaded: " + users.size() + " users, " + projects.size() + " projects.");
	}

	/**
	 * Returns the list of all projects.
	 * 
	 * @return A list of {@link Project} objects in the system.
	 */
	public List<Project> getProjects() {
		return projects;
	}

	/**
	 * Returns the list of all users.
	 * 
	 * @return A list of {@link User} objects (including applicants, officers, and
	 *         managers).
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * Returns the ProjectControl instance.
	 * 
	 * @return The {@link ProjectControl} for this system.
	 */
	public ProjectControl getProjectControl() {
		return pc;
	}

	/**
	 * Returns the ApplicationControl instance.
	 * 
	 * @return The {@link ApplicationControl} for this system.
	 */
	public ApplicationControl getApplicationControl() {
		return ac;
	}

	/**
	 * Returns the OfficerControl instance.
	 * 
	 * @return The {@link OfficerControl} for this system.
	 */
	public OfficerControl getOfficerControl() {
		return oc;
	}

	/**
	 * Returns the ManagerControl instance.
	 * 
	 * @return The {@link ManagerControl} for this system.
	 */
	public ManagerControl getManagerControl() {
		return mc;
	}

	/**
	 * Returns the EnquiryControl instance.
	 * 
	 * @return The {@link EnquiryControl} for this system.
	 */
	public EnquiryControl getEnquiryControl() {
		return ec;
	}

	/**
	 * Authenticates a user given an NRIC and password. This method searches through
	 * all users and returns the matching user if the credentials are correct.
	 * 
	 * @param nric     The NRIC entered by the user.
	 * @param password The password entered by the user.
	 * @return The {@link User} object if login is successful, or {@code null} if no
	 *         matching user is found or password is incorrect.
	 */
	public User login(String nric, String password) {
		for (User u : users) {
			if (u.getNric().equalsIgnoreCase(nric) && u.checkPassword(password)) {
				return u;
			}
		}
		return null;
	}
}
