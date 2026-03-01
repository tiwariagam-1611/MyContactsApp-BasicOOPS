package com.seveneleven.mycontactapp.user;

import java.time.LocalDateTime;
import java.util.UUID;

public class Contact {

    private final String id;
    private String displayName;
    private String phone;
    private String email;
    private Tag tag;

    private final LocalDateTime dateAdded;
    private int contactCount;

    public Contact(String displayName, String phone, String email, Tag tag) {

        if (displayName == null || displayName.isBlank()) {
            throw new IllegalArgumentException("Display name required");
        }

        if (tag == null) {
            throw new IllegalArgumentException("Tag required");
        }

        this.id = UUID.randomUUID().toString();
        this.displayName = displayName.trim();
        this.phone = phone;
        this.email = email;
        this.tag = tag;

        this.dateAdded = LocalDateTime.now();
        this.contactCount = 0;
    }

    public String getId() { return id; }
    public String getDisplayName() { return displayName; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public Tag getTag() { return tag; }
    public LocalDateTime getDateAdded() { return dateAdded; }
    public int getContactCount() { return contactCount; }

    public void setDisplayName(String displayName) {
        if (displayName == null || displayName.isBlank()) {
            throw new IllegalArgumentException("Display name required");
        }
        this.displayName = displayName.trim();
    }

    public void setPhone(String phone) {
        this.phone = phone;
        incrementContactCount();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTag(Tag tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Tag required");
        }
        this.tag = tag;
    }

    public void incrementContactCount() {
        contactCount++;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "tag='" + tag.getName() + '\'' +
                ", name='" + displayName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", dateAdded=" + dateAdded +
                ", contactCount=" + contactCount +
                '}';
    }
}