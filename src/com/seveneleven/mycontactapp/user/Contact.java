package com.seveneleven.mycontactapp.user;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Contact {

    private final String id;
    private final LocalDateTime createdAt;

    private String displayName;
    private String phone;
    private String email;

    public Contact(String displayName, String phone, String email) {

        if (displayName == null || displayName.isBlank()) {
            throw new IllegalArgumentException("Name required");
        }

        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.displayName = displayName.trim();
        setPhone(phone);
        setEmail(email);
    }


    protected Contact(Contact other) {
        this.id = other.id;
        this.createdAt = other.createdAt;
        this.displayName = other.displayName;
        this.phone = other.phone;
        this.email = other.email;
    }

    public abstract String getType();

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        if (displayName == null || displayName.isBlank()) {
            throw new IllegalArgumentException("Name required");
        }
        this.displayName = displayName.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone != null && phone.length() < 5) {
            throw new IllegalArgumentException("Invalid phone");
        }
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }
    public String getId() {
        return id;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setEmail(String email) {
        if (email != null && !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
        this.email = email;
    }
    @Override
    public String toString() {
        return getType() + " | Name: " + displayName +
               " | Phone: " + (phone == null ? "-" : phone) +
               " | Email: " + (email == null ? "-" : email);
    }
}