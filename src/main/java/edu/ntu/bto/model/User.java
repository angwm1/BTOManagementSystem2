package edu.ntu.bto.model;

import java.util.Scanner;
import edu.ntu.bto.service.BTOManagementSystem;

/**
 * Abstract class representing a system user in the BTO Management System. This
 * class holds common attributes for all users (NRIC, age, marital status,
 * password) and defines the interface for user menu display.
 * 
 * @author SC2002 Assignment Group
 */
public abstract class User {
	/** NRIC (unique identifier) of this user. */
	protected String nric;
	/** Age of this user. */
	protected int age;
	/** Marital status of this user (e.g., "Single" or "Married"). */
	protected String maritalStatus;
	/** Password for this user's account (default is "password"). */
	protected String password;

	/**
	 * Constructs a new User with the given NRIC, age, and marital status. The
	 * password is initialized to a default value.
	 * 
	 * @param nric          The NRIC of the user.
	 * @param age           The age of the user.
	 * @param maritalStatus The marital status of the user.
	 */
	public User(String nric, int age, String maritalStatus) {
		this.nric = nric;
		this.age = age;
		this.maritalStatus = maritalStatus;
		this.password = "password"; // default
	}

	/**
	 * Constructs a new User with the given NRIC, age, marital status, and password.
	 * 
	 * @param nric          The NRIC of the user.
	 * @param age           The age of the user.
	 * @param maritalStatus The marital status of the user.
	 * @param password      The account password for the user.
	 */
	public User(String nric, int age, String maritalStatus, String password) {
		this.nric = nric;
		this.age = age;
		this.maritalStatus = maritalStatus;
		this.password = password;
	}

	/**
	 * Gets the National Registration Identity Card (NRIC) number.
	 *
	 * @return the NRIC number of the person as a {@code String}.
	 */
	public String getNric() {
		return nric;
	}

	/**
	 * Gets the age of the person in years.
	 *
	 * @return the age of the person as an {@code int}.
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Gets the marital status of the person.
	 *
	 * @return the marital status of the person as a {@code String}, for example
	 *         "Single", "Married", or "Divorced".
	 */
	public String getMaritalStatus() {
		return maritalStatus;
	}

	/**
	 * Checks if the given password matches this user's password.
	 * 
	 * @param pw The password to verify.
	 * @return {@code true} if the password matches this user's password,
	 *         {@code false} otherwise.
	 */
	public boolean checkPassword(String pw) {
		return password.equals(pw);
	}

	/**
	 * Sets a new password for this user.
	 * 
	 * @param newPw The new password to be set for the user.
	 */
	public void changePassword(String newPw) {
		this.password = newPw;
	}

	/**
	 * Display the CLI menu for this user and handle user interactions. Each
	 * concrete subclass provides its own menu options and logic for user actions.
	 * 
	 * @param scanner A {@link Scanner} for reading user input.
	 * @param system  The {@link edu.ntu.bto.service.BTOManagementSystem} instance
	 *                to interact with core system functions.
	 */
	public abstract void displayMenu(Scanner scanner, BTOManagementSystem system);
}
