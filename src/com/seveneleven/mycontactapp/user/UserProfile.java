package com.seveneleven.mycontactapp.user;

public class UserProfile {
    private String firstName;  // optional
    private String lastName;   // optional
    private String phone;      // optional (required for PREMIUM at registration)

    public UserProfile(String firstName, String lastName, String phone) {
        this.firstName = trimOrNull(firstName);
        this.lastName  = trimOrNull(lastName);
        this.phone     = trimOrNull(phone);
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = trimOrNull(firstName); }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = trimOrNull(lastName); }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = trimOrNull(phone); }

    private static String trimOrNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}