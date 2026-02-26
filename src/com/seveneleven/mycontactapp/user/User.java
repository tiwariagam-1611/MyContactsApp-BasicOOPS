package com.seveneleven.mycontactapp.user;

public class User {

    public enum Plan { FREE, PREMIUM }

    private String name;        // display name
    private String email;       // normalized
    private String password;    // (plain for UC-1 demo)
    private Plan plan;          // FREE or PREMIUM
    private UserProfile profile;

    public User(String name, String email, String password, Plan plan, UserProfile profile) {
        setName(name);
        setEmail(email);
        setPassword(password);
        setPlan(plan);
        this.profile = profile; // optional
    }

    // Encapsulation + basic validation
    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Name required");
        this.name = name.trim();
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty())
            throw new IllegalArgumentException("Email required");
        this.email = email.trim().toLowerCase();
    }

    public String getPassword() { return password; }
    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty())
            throw new IllegalArgumentException("Password required");
        this.password = password;
    }

    public Plan getPlan() { return plan; }
    public void setPlan(Plan plan) {
        if (plan == null) throw new IllegalArgumentException("Plan required");
        this.plan = plan;
    }

    public UserProfile getProfile() { return profile; }
    public void setProfile(UserProfile profile) { this.profile = profile; }

    public String perks() {
        return "Basic usage";
    }

    @Override
    public String toString() {
        return plan + " user: " + name + " <" + email + ">";
    }
}