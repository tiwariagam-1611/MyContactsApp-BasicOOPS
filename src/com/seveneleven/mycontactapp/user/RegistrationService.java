package com.seveneleven.mycontactapp.user;

import java.security.SecureRandom;

public class RegistrationService {
    private final UserRepository repo;
    private final SecureRandom rnd = new SecureRandom();

    public RegistrationService(UserRepository repo) {
        if (repo == null) throw new IllegalArgumentException("repo required");
        this.repo = repo;
    }

    public User register(String name, String email, String password, User.Plan plan, UserProfile profile) {

        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name required");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email required");
        if (password == null || password.isBlank()) throw new IllegalArgumentException("Password required");
        if (plan == null) throw new IllegalArgumentException("Plan required");

        String normalizedEmail = email.trim().toLowerCase();
        if (repo.existsByEmail(normalizedEmail)) throw new IllegalArgumentException("Email already registered");

        String pin = generatePin6();

        User user = (plan == User.Plan.FREE)
                ? new FreeUser(name.trim(), normalizedEmail, password, pin, profile)
                : new PremiumUser(name.trim(), normalizedEmail, password, pin, profile);

        repo.save(user);
        return user;
    }

    private String generatePin6() {
        int n = rnd.nextInt(1_000_000);
        return String.format("%06d", n);
    }
}