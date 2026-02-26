package com.seveneleven.mycontactapp.user;


public class BasicAuthStrategy implements AuthenticationStrategy {

    private final UserRepository repo;

    public BasicAuthStrategy(UserRepository repo) {
        if (repo == null) throw new IllegalArgumentException("repo required");
        this.repo = repo;
    }

    @Override
    public User authenticate(String email, String password) {
        if (email == null || password == null) return null;
        User u = repo.findByEmail(email);
        if (u == null) return null;
        return password.equals(u.getPassword()) ? u : null;
    }
}