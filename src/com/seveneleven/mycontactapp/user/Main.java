// Use Case-6: Edit Contact
// The user updates the details of an existing contact, applying changes with proper validation
// @author Developer
// @version 6.0


package com.seveneleven.mycontactapp.user;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Data Storage Infrastructure
        UserRepository userRepo = new UserRepository();
        ContactRepository contactRepo = new ContactRepository();

        // Services
        RegistrationService registration = new RegistrationService(userRepo);
        AuthenticationStrategy basicAuth = new BasicAuthStrategy(userRepo);
        AuthenticationStrategy oauthAuth = new OAuthStrategy(userRepo);
        ProfileService profileService = new ProfileService();
        ContactService contactService = new ContactService(contactRepo);

        // Session
        User currentUser = null;

        try (Scanner sc = new Scanner(System.in)) {
            boolean running = true;
            System.out.println("=== MyContacts (UC1-UC6) ===");

            while (running) {
                System.out.println();

                if (currentUser != null) {

                    System.out.println("Logged in: " + currentUser.getName()
                            + " <" + currentUser.getEmail() + "> [" + currentUser.getPlan() + "]");
                    System.out.println("Perks: " + currentUser.perks());

                    System.out.println("1) Change Display Name");
                    System.out.println("2) Change Email");
                    System.out.println("3) Change Password");
                    System.out.println("4) Create Person Contact");
                    System.out.println("5) Create Organization Contact");
                    System.out.println("6) List All Contacts");
                    System.out.println("7) View Contact by Name");
                    System.out.println("8) Edit Contact");
                    System.out.println("9) Logout");
                    System.out.println("10) Exit");
                    System.out.print("> ");
                    String choice = readTrim(sc);

                    switch (choice) {

                        case "1" -> {
                            System.out.print("New Display Name: ");
                            String newName = readTrim(sc);
                            try {
                                profileService.changeDisplayName(currentUser, newName);
                                System.out.println("Display name updated to: " + currentUser.getName());
                            } catch (Exception ex) {
                                System.out.println("Update failed: " + ex.getMessage());
                            }
                        }

                        case "2" -> {
                            System.out.print("New Email: ");
                            String newEmail = readTrim(sc);
                            try {
                                profileService.changeEmail(currentUser, newEmail, userRepo);
                                System.out.println("Email updated to: " + currentUser.getEmail());
                            } catch (Exception ex) {
                                System.out.println("Update failed: " + ex.getMessage());
                            }
                        }

                        case "3" -> {
                            System.out.println("\n-- Change Password --");
                            System.out.print("Current Password: ");
                            String oldPw = readTrim(sc);
                            System.out.print("New Password    : ");
                            String newPw = readTrim(sc);

                            boolean ok = profileService.changePassword(currentUser, oldPw, newPw);
                            System.out.println(ok ? "Password changed." : "Password change failed.");
                        }

                        case "4" -> {
                            System.out.println("\n-- Create Person Contact --");
                            System.out.print("Name : ");
                            String name = readTrim(sc);
                            System.out.print("Phone: ");
                            String phone = readTrim(sc);
                            System.out.print("Email: ");
                            String email = readTrim(sc);

                            try {
                                Contact c = contactService.createPerson(name, phone, email);
                                System.out.println("Created: " + c);
                                System.out.println("Total contacts: " + contactRepo.count());
                            } catch (Exception ex) {
                                System.out.println("Create failed: " + ex.getMessage());
                            }
                        }

                        case "5" -> {
                            System.out.println("\n-- Create Organization Contact --");
                            System.out.print("Organization Name: ");
                            String name = readTrim(sc);
                            System.out.print("Phone           : ");
                            String phone = readTrim(sc);
                            System.out.print("Email           : ");
                            String email = readTrim(sc);

                            try {
                                Contact c = contactService.createOrganization(name, phone, email);
                                System.out.println("Created: " + c);
                                System.out.println("Total contacts: " + contactRepo.count());
                            } catch (Exception ex) {
                                System.out.println("Create failed: " + ex.getMessage());
                            }
                        }

                        case "6" -> {
                            System.out.println("\n-- All Contacts --");
                            List<Contact> list = contactRepo.findAll();
                            if (list.isEmpty()) {
                                System.out.println("(no contacts yet)");
                            } else {
                                for (Contact c : list) {
                                    System.out.println("- " + c);
                                }
                            }
                        }

                        case "7" -> {
                            System.out.println("\n-- View Contact Details --");
                            System.out.print("Enter Contact Name: ");
                            String name = readTrim(sc);

                            try {
                                Contact c = contactService.viewContactByName(name);

                                System.out.println("\n===== CONTACT DETAILS =====");
                                System.out.println("Type    : " + c.getType());
                                System.out.println("Name    : " + c.getDisplayName());
                                System.out.println("Email   : " + 
                                        (c.getEmail() == null ? "-" : c.getEmail()));
                                System.out.println("Phone   : " + 
                                        (c.getPhone() == null ? "-" : c.getPhone()));
                                System.out.println("Created : " + c.getCreatedAt());
                                System.out.println("============================");

                            } catch (Exception ex) {
                                System.out.println("Error: " + ex.getMessage());
                            }
                        }

                        case "8" -> {
                            System.out.println("\n-- Edit Contact --");
                            System.out.print("Enter Contact Name: ");
                            String name = readTrim(sc);

                            System.out.print("New Phone (leave blank to skip): ");
                            String newPhone = readTrim(sc);

                            System.out.print("New Email (leave blank to skip): ");
                            String newEmail = readTrim(sc);

                            try {
                                Contact updated =
                                        contactService.editContactByName(name, newPhone, newEmail);

                                System.out.println("Contact updated successfully.");

                                System.out.println("\n===== UPDATED DETAILS =====");
                                System.out.println("Type    : " + updated.getType());
                                System.out.println("Name    : " + updated.getDisplayName());
                                System.out.println("Email   : " +
                                        (updated.getEmail() == null ? "-" : updated.getEmail()));
                                System.out.println("Phone   : " +
                                        (updated.getPhone() == null ? "-" : updated.getPhone()));
                                System.out.println("Created : " + updated.getCreatedAt());
                                System.out.println("============================");

                            } catch (Exception ex) {
                                System.out.println("Edit failed: " + ex.getMessage());
                            }
                        }

                        case "9" -> {
                            currentUser = null;
                            System.out.println("Logged out.");
                        }

                        case "10" -> {
                            running = false;
                            System.out.println("Bye!");
                        }

                        default -> System.out.println("Invalid choice.");
                    }

                } else {

                    System.out.println("1) Register");
                    System.out.println("2) Login (email + password)");
                    System.out.println("3) Login (display name + PIN)");
                    System.out.println("4) Exit");
                    System.out.print("> ");
                    String choice = readTrim(sc);

                    switch (choice) {

                        case "1" -> handleRegistration(sc, registration);

                        case "2" -> {
                            System.out.println("\n-- Login: email + password --");
                            System.out.print("Email: ");
                            String email = readTrim(sc);
                            System.out.print("Password: ");
                            String password = readTrim(sc);
                            User u = basicAuth.authenticate(email, password);
                            if (u != null) {
                                currentUser = u;
                                System.out.println("Logged in (Basic Auth).");
                            } else {
                                System.out.println("Login failed.");
                            }
                        }

                        case "3" -> {
                            System.out.println("\n-- Login: display name + PIN --");
                            System.out.print("Display Name: ");
                            String name = readTrim(sc);
                            System.out.print("PIN: ");
                            String pin = readTrim(sc);
                            User u = oauthAuth.authenticate(name, pin);
                            if (u != null) {
                                currentUser = u;
                                System.out.println("Logged in (Name + PIN).");
                            } else {
                                System.out.println("Login failed.");
                            }
                        }

                        case "4" -> {
                            running = false;
                            System.out.println("Bye!");
                        }

                        default -> System.out.println("Invalid choice.");
                    }
                }
            }
        }
    }

    private static void handleRegistration(Scanner sc, RegistrationService registration) {
        System.out.println("\n-- Registration --");
        System.out.print("Plan [FREE|PREMIUM]: ");
        String planStr = readTrim(sc).toUpperCase();

        System.out.print("Display Name: ");
        String name = readTrim(sc);

        System.out.print("Email: ");
        String email = readTrim(sc);

        System.out.print("Password: ");
        String password = readTrim(sc);

        UserProfile profile = new UserProfile(null, null, null);

        try {
            User.Plan plan = User.Plan.valueOf(planStr);
            User user = registration.register(name, email, password, plan, profile);
            System.out.println("Registered -> " + user);
            System.out.println("Perks: " + user.perks());
            System.out.println("Your PIN (keep it safe): " + user.getPin());
        } catch (Exception ex) {
            System.out.println("Registration Failed: " + ex.getMessage());
        }
    }

    private static String readTrim(Scanner sc) {
        try {
            String s = sc.nextLine();
            return (s == null) ? "" : s.trim();
        } catch (Exception e) {
            return "";
        }
    }
}