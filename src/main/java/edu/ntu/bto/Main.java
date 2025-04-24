package edu.ntu.bto;

import java.util.Scanner;
import edu.ntu.bto.service.BTOManagementSystem;
import edu.ntu.bto.model.User;

/**
 * Main class for the BTO Management System CLI application. This class contains
 * the entry point and manages the high-level login loop for users of different
 * roles.
 * 
 * @author SC2002 Assignment Group
 */
public class Main {
	/**
	 * The entry point of the BTO Management System application. It initializes the
	 * system, then continuously prompts for user login and directs the user to
	 * their respective menu until the program is terminated.
	 * 
	 * @param args Command-line arguments (not used).
	 */
	public static void main(String[] args) {
		BTOManagementSystem system = new BTOManagementSystem();
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("\n--- Welcome to the BTO Management System ---");
			System.out.print("Enter NRIC: ");
			String nric = scanner.nextLine().trim().toUpperCase();
			while (!nric.matches("^[STFG]\\d{7}[A-Z]$")) {
				System.out.println("Invalid NRIC. Please try again.");
				System.out.print("Enter NRIC: ");
				nric = scanner.nextLine().trim().toUpperCase();
			}
			System.out.print("Enter Password: ");
			String password = scanner.nextLine();
			User user = system.login(nric, password);
			if (user != null) {
				System.out.println("Login successful! Welcome, " + user.getNric());
				user.displayMenu(scanner, system);
			} else {
				System.out.println("Invalid credentials. Please try again.");
			}
		}
	}
}
