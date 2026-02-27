package com.seveneleven.mycontactapp.user;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Contact {

    public enum Type { PERSON, ORGANIZATION }

    private final String id;
    private final LocalDateTime createdAt;
    private String displayName;
    private String phone;
    private String email;

    protected Contact(String displayName, String phone, String email) {
        if (displayName == null || displayName.isBlank()) throw new IllegalArgumentException("Contact name required");

        this.id = UUID.randomUUID().toString();
        this.displayName = displayName.trim();
        this.phone = (phone == null || phone.isBlank()) ? null : phone.trim();
        this.email = (email == null || email.isBlank()) ? null : email.trim().toLowerCase();
        this.createdAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getDisplayName() { return displayName; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    public void setDisplayName(String newName) {
        if (newName == null || newName.isBlank()) throw new IllegalArgumentException("Contact name required");
        this.displayName = newName.trim();
    }

    public void setPhone(String phone) { this.phone = (phone == null || phone.isBlank()) ? null : phone.trim(); }
    public void setEmail(String email) { this.email = (email == null || email.isBlank()) ? null : email.trim().toLowerCase(); }


    public abstract Type getType();

    @Override
    public String toString() {
        return "[" + getType() + "] " + displayName +
               " (id=" + id + ", email=" + (email == null ? "-" : email) +
               ", phone=" + (phone == null ? "-" : phone) +
               ", created=" + createdAt + ")";
    }
}
