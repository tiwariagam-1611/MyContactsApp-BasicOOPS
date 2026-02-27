// UserProfile.java
package com.seveneleven.mycontactapp.user;

public class UserProfile {
    private String firstName;
    private String lastName;
    private String phone;

    public UserProfile(String firstName, String lastName, String phone) {
        this.firstName = trimOrNull(firstName);
        this.lastName  = trimOrNull(lastName);
        this.phone     = trimOrNull(phone);
    }

    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public String getPhone()     { return phone; }

    public void setFirstName(String firstName) { this.firstName = trimOrNull(firstName); }
    public void setLastName(String lastName)   { this.lastName  = trimOrNull(lastName); }
    public void setPhone(String phone)         { this.phone     = trimOrNull(phone); }

    private static String trimOrNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}
