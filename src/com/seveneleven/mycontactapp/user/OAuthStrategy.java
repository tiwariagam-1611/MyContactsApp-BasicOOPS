package com.seveneleven.mycontactapp.user;

/**
 * "OAuth-like" demo: display name + PIN
 * Works for any plan (FREE or PREMIUM).
 */
public class OAuthStrategy implements AuthenticationStrategy {

    private final UserRepository repo;

    public OAuthStrategy(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User authenticate(String displayName, String pin) {
        if (displayName == null || pin == null) return null;

        User u = repo.findByName(displayName);
        if (u == null) return null;

        return pin.equals(u.getPin()) ? u : null;
    }
}