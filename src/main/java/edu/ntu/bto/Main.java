package edu.ntu.bto;

import java.util.Scanner;
import edu.ntu.bto.service.BTOManagementSystem;
import edu.ntu.bto.model.User;

/**
 * Main class for the BTO Management System CLI application.
 */
public class Main {
    public static void main(String[] args) {
        BTOManagementSystem system = new BTOManagementSystem();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Welcome to the BTO Management System ---");
            System.out.print("Enter NRIC: ");
            String nric = scanner.nextLine();
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
