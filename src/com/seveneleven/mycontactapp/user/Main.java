// Use Case-2: User Authentication
// The user accesses their account
// The password is securely hashed using MessageDigest with SHA-256
// The user may choose to authenticate via BasicAuth or OAuth methods
// @author Developer
// @version 2.0

package com.seveneleven.mycontactapp.user;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // --- Infrastructure / Data store ---
        UserRepository repo = new UserRepository();

        // --- Services ---
        RegistrationService registration = new RegistrationService(repo);
        AuthenticationStrategy basicAuth = new BasicAuthStrategy(repo); // email + password
        AuthenticationStrategy oauthAuth = new OAuthStrategy(repo);     // display name + PIN

        // --- Session ---
        User currentUser = null;

        try (Scanner sc = new Scanner(System.in)) {
            boolean running = true;
            System.out.println("=== MyContacts (Strategy Auth) ===");

            while (running) {
                System.out.println();

                if (currentUser != null) {
                    // -------- Logged-in menu --------
                    System.out.println("Logged in: " + currentUser.getName()
                            + " <" + currentUser.getEmail() + "> [" + currentUser.getPlan() + "]");
                    System.out.println("Perks: " + currentUser.perks());
                    System.out.println("1) Logout");
                    System.out.println("2) Exit");
                    System.out.print("> ");
                    String choice = readTrim(sc);

                    switch (choice) {
                        case "1" -> {
                            currentUser = null;
                            System.out.println("Logged out.");
                        }
                        case "2" -> {
                            running = false;
                            System.out.println("Bye!");
                        }
                        default -> System.out.println("Invalid choice.");
                    }
                } else {
                    // -------- Logged-out menu --------
                    System.out.println("1) Register");
                    System.out.println("2) Login (email + password)");
                    System.out.println("3) Login (display name + PIN)");
                    System.out.println("4) Who am I");
                    System.out.println("5) Exit");
                    System.out.print("> ");
                    String choice = readTrim(sc);

                    switch (choice) {
                        case "1" -> handleRegistration(sc, registration);

                        case "2" -> {
                            // --- BasicAuth ---
                            System.out.println("\n-- Login: email + password --");
                            System.out.print("Email: ");
                            String email = readTrim(sc);
                            System.out.print("Password: ");
                            String password = readTrim(sc);

                            User u = basicAuth.authenticate(email, password);
                            if (u != null) {
                                currentUser = u;
                                System.out.println("Logged in (Basic Auth).");
                                System.out.println("Perks: " + currentUser.perks());
                            } else {
                                System.out.println("Login failed.");
                            }
                        }

                        case "3" -> {
                            // --- OAuth-like (Name + PIN) ---
                            System.out.println("\n-- Login: display name + PIN --");
                            System.out.print("Display Name: ");
                            String name = readTrim(sc);
                            System.out.print("PIN: ");
                            String pin = readTrim(sc);

                            User u = oauthAuth.authenticate(name, pin);
                            if (u != null) {
                                currentUser = u;
                                System.out.println("Logged in (Name + PIN).");
                                System.out.println("Perks: " + currentUser.perks());
                            } else {
                                System.out.println("Login failed.");
                            }
                        }

                        case "4" -> {
                            System.out.println("No user is currently logged in.");
                        }

                        case "5" -> {
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
