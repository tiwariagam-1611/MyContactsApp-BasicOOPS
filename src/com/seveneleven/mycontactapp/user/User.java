package com.seveneleven.mycontactapp.user;

public class User {

    public enum Plan { FREE, PREMIUM }

    private String name;        // display name
    private String email;      
    private String password;  
    private String pin;         // 6-digit PIN for OAuth-like login
    private Plan plan;
    private UserProfile profile; // optional

    public User(String name, String email, String password, Plan plan, String pin, UserProfile profile) {
        this.name = name.trim();
        this.email = email.trim().toLowerCase();
        this.password = password;
        this.plan = plan;
        this.pin = pin;
        this.profile = profile;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPin() { return pin; }
    public Plan getPlan() { return plan; }
    public UserProfile getProfile() { return profile; }

    public void setProfile(UserProfile profile) { this.profile = profile; }

    public String perks() { return "Basic usage"; }

    @Override
    public String toString() {
        return plan + " user: " + name + " <" + email + ">";
    }
}