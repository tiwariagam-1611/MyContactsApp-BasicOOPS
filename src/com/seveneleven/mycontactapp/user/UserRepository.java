package com.seveneleven.mycontactapp.user;

import java.util.*;

public class UserRepository {
    private final Map<String, User> usersByEmail = new HashMap<>();

    public boolean existsByEmail(String email) {
        return usersByEmail.containsKey(normalize(email));
    }

    public void save(User user) {
        usersByEmail.put(user.getEmail(), user);
    }

    public User findByEmail(String email) {
        return usersByEmail.get(normalize(email)); // returns null if not found
    }

    public User findByName(String name) {
        if (name == null) return null;
        String target = name.trim().toLowerCase();
        for (User u : usersByEmail.values()) {
            if (u.getName() != null && u.getName().trim().toLowerCase().equals(target)) {
                return u; // first match
            }
        }
        return null;
    }

    private static String normalize(String email) {
        return (email == null) ? null : email.trim().toLowerCase();
    }
}
