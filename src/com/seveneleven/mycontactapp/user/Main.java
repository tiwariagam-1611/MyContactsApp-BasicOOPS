package com.seveneleven.mycontactapp.user;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        UserRepository repo = new UserRepository();
        RegistrationService registrationService = new RegistrationService(repo);

        try (Scanner sc = new Scanner(System.in)) {
            boolean running = true;
            System.out.println("=== MyContacts App (UC-01: Registration Only) ===");

            while (running) {
                System.out.println();
                System.out.println("Choose an option:");
                System.out.println("1) Register");
                System.out.println("2) Exit");
                System.out.print("> ");

                String choice = readTrim(sc);

                switch (choice) {
                    case "1":
                        handleRegistration(sc, registrationService);
                        break;
                    case "2":
                        running = false;
                        System.out.println("Bye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            }
        }
    }

    private static void handleRegistration(Scanner sc, RegistrationService registrationService) {
        System.out.println("\n-- Registration --");

        System.out.print("Plan [FREE|PREMIUM] (required): ");
        String planStr = readTrim(sc).toUpperCase();

        System.out.print("Display Name (required): ");
        String name = readTrim(sc);

        System.out.print("Email (required): ");
        String email = readTrim(sc);

        System.out.print("Password (required): ");
        String password = readTrim(sc);

        System.out.print("First Name (optional): ");
        String firstName = readTrim(sc);

        System.out.print("Last Name (optional): ");
        String lastName = readTrim(sc);

        System.out.print("Phone (optional for FREE, required for PREMIUM): ");
        String phone = readTrim(sc);

        // UI-side validation to show all issues at once
        StringBuilder errors = new StringBuilder();

        // Plan parse using inner enum
        User.Plan plan = null;
        if (planStr.isEmpty()) {
            errors.append("- Plan required (FREE or PREMIUM)\n");
        } else {
            try {
                plan = User.Plan.valueOf(planStr);
            } catch (IllegalArgumentException ex) {
                errors.append("- Invalid plan. Enter FREE or PREMIUM\n");
            }
        }

        if (name.isEmpty()) errors.append("- Name required\n");

        if (email.isEmpty()) {
            errors.append("- Email required\n");
        } else {
            try { EmailValidator.validate(email); }
            catch (IllegalArgumentException ex) { errors.append("- ").append(ex.getMessage()).append("\n"); }
        }

        if (password.isEmpty()) errors.append("- Password required\n");

        if (plan == User.Plan.PREMIUM && phone.isEmpty()) {
            errors.append("- Phone required for PREMIUM plan\n");
        }

        if (errors.length() > 0) {
            System.out.println("❌ Registration Failed:");
            System.out.print(errors.toString());
            return;
        }

        UserProfile profile = new UserProfile(
                nullIfBlank(firstName),
                nullIfBlank(lastName),
                nullIfBlank(phone)
        );

        try {
            User user = registrationService.register(name, email, password, plan, profile);
            System.out.println("✅ Registration Successful!");
            System.out.println(" -> " + user);
            System.out.println("Perks: " + user.perks());
            if (user instanceof PremiumUser) {
                System.out.println("Support PIN: " + ((PremiumUser) user).getSupportPin());
            } else if (user instanceof FreeUser) {
                System.out.println("Ads Enabled: " + ((FreeUser) user).isAdsEnabled());
            }
        } catch (Exception ex) {
            System.out.println("❌ Registration Failed: " + ex.getMessage());
        }
    }

    private static String readTrim(Scanner sc) {
        try { String s = sc.nextLine(); return (s == null) ? "" : s.trim(); }
        catch (Exception e) { return ""; }
    }
    private static String nullIfBlank(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }
}