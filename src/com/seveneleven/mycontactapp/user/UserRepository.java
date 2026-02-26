package com.seveneleven.mycontactapp.user;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private final Map<String, User> usersByEmail = new HashMap<>();

    public boolean existsByEmail(String email) {
        return usersByEmail.containsKey(normalize(email));
    }

    public void save(User user) {
        usersByEmail.put(user.getEmail(), user);
    }

    public User findByEmail(String email) {
        return usersByEmail.get(normalize(email));
    }

    public User findByName(String name) {
        if (name == null) return null;
        String target = name.trim().toLowerCase();
        for (User u : usersByEmail.values()) {
            if (u.getName() != null && u.getName().trim().toLowerCase().equals(target)) {
                return u;
            }
        }
        return null;
    }

    public void updateEmail(User user, String newEmail) {
        if (user == null) throw new IllegalArgumentException("User required");
        String oldKey = user.getEmail();          // already normalized
        String newKey = normalize(newEmail);      // target normalized

        if (oldKey == null) throw new IllegalStateException("User email missing");
        if (newKey == null || newKey.isBlank()) throw new IllegalArgumentException("New email required");

        if (oldKey.equals(newKey)) return;


        if (existsByEmail(newKey)) throw new IllegalArgumentException("Email already registered");


        usersByEmail.remove(oldKey);
        user.setEmail(newKey);    // update entity
        usersByEmail.put(newKey, user);
    }

    private static String normalize(String email) {
        return (email == null) ? null : email.trim().toLowerCase();
    }
}