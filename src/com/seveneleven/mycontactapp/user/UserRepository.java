package com.seveneleven.mycontactapp.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepository {
    private final Map<String, User> usersByEmail = new HashMap<>();

    public boolean existsByEmail(String email) {
        return usersByEmail.containsKey(normalize(email));
    }

    public void save(User user) {
        usersByEmail.put(normalize(user.getEmail()), user);
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(usersByEmail.get(normalize(email)));
    }

    private String normalize(String email) {
        return (email == null) ? null : email.trim().toLowerCase();
    }
}