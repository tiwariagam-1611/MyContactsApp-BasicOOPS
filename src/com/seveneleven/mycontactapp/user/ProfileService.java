package com.seveneleven.mycontactapp.user;

public class ProfileService {


    public void changeDisplayName(User user, String newDisplayName) {
        if (user == null) throw new IllegalArgumentException("User required");
        user.setName(newDisplayName);
    }


    public void changeEmail(User user, String newEmail, UserRepository repo) {
        if (user == null) throw new IllegalArgumentException("User required");
        if (repo == null) throw new IllegalArgumentException("Repository required");
        repo.updateEmail(user, newEmail);
    }

    public boolean changePassword(User user, String currentPassword, String newPassword) {
        if (user == null) throw new IllegalArgumentException("User required");
        if (currentPassword == null || newPassword == null) return false;
        if (!currentPassword.equals(user.getPassword())) return false;

        user.setPassword(newPassword);
        return true;
    }
}