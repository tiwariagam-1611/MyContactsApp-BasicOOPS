package com.seveneleven.mycontactapp.user;

public class UserProfile {
    private String firstName;  // optional
    private String lastName;   // optional
    private String phone;      // optional

    public UserProfile(String firstName, String lastName, String phone) {
        this.firstName = trimOrNull(firstName);
        this.lastName  = trimOrNull(lastName);
        this.phone     = trimOrNull(phone);
    }

    private static String trimOrNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public String getPhone()     { return phone; }
}
