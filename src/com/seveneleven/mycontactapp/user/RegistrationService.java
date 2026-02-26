package com.seveneleven.mycontactapp.user;

public class RegistrationService {
    private final UserRepository repo;

    public RegistrationService(UserRepository repo) {
        if (repo == null) throw new IllegalArgumentException("repo required");
        this.repo = repo;
    }

    public User register(String name,
                         String email,
                         String password,
                         User.Plan plan,
                         UserProfile profile) {

        // Basic validations (service-level safety)
        if (name == null || name.trim().isEmpty())         throw new IllegalArgumentException("Name required");
        if (password == null || password.trim().isEmpty()) throw new IllegalArgumentException("Password required");
        if (plan == null)                                  throw new IllegalArgumentException("Plan required");
        EmailValidator.validate(email);

        // Premium-specific rule: phone required
        if (plan == User.Plan.PREMIUM) {
            String phone = (profile == null) ? null : profile.getPhone();
            if (phone == null || phone.trim().isEmpty()) {
                throw new IllegalArgumentException("Phone required for PREMIUM plan");
            }
        }

        String normalizedEmail = email.trim().toLowerCase();
        if (repo.existsByEmail(normalizedEmail))
            throw new IllegalArgumentException("Email already registered");

        // Construct per plan (basic OOP)
        User user = (plan == User.Plan.FREE)
                ? new FreeUser(name, normalizedEmail, password, profile)
                : new PremiumUser(name, normalizedEmail, password, profile);

        repo.save(user);
        return user;
    }
}