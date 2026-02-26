package com.seveneleven.mycontactapp.user;


//Basic authentication: email + password
//Works for any plan (FREE or PREMIUM).

public class BasicAuthStrategy implements AuthenticationStrategy {

    private final UserRepository repo;

    public BasicAuthStrategy(UserRepository repo) {
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
