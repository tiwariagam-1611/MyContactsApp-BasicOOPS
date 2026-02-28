package com.seveneleven.mycontactapp.user;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Contact {

    private final String id;
    private String displayName;
    private String phone;
    private String email;
    private String tag;

    // UC-10 NEW FIELDS
    private final LocalDateTime dateAdded;
    private int contactCount;

    public Contact(String displayName, String phone, String email, String tag) {

        if (displayName == null || displayName.isBlank()) {
            throw new IllegalArgumentException("Display name required");
        }

        this.id = UUID.randomUUID().toString();
        this.displayName = displayName.trim();
        this.phone = phone;
        this.email = email;
        this.tag = tag;

        this.dateAdded = LocalDateTime.now();
        this.contactCount = 0;
    }

    public Contact(Contact other) {
        this.id = other.id;
        this.displayName = other.displayName;
        this.phone = other.phone;
        this.email = other.email;
        this.tag = other.tag;
        this.dateAdded = other.dateAdded;
        this.contactCount = other.contactCount;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        if (displayName == null || displayName.isBlank()) {
            throw new IllegalArgumentException("Display name required");
        }
        this.displayName = displayName.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        incrementContactCount(); // Track frequency
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        if (tag == null || tag.isBlank()) {
            throw new IllegalArgumentException("Tag required");
        }
        this.tag = tag;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public int getContactCount() {
        return contactCount;
    }

    public void incrementContactCount() {
        contactCount++;
    }

    public abstract Contact copy();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;
        Contact contact = (Contact) o;
        return id.equals(contact.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Contact{" +
                "tag='" + tag + '\'' +
                ", name='" + displayName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", dateAdded=" + dateAdded +
                ", contactCount=" + contactCount +
                '}';
    }
}