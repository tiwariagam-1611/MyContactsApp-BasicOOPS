package com.seveneleven.mycontactapp.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Contact {

    private final String id;
    private String displayName;
    private String phone;
    private String email;
    private final List<Tag> tags;

    private final LocalDateTime dateAdded;
    private int contactCount;

    public Contact(String displayName, String phone, String email) {

        if (displayName == null || displayName.isBlank()) {
            throw new IllegalArgumentException("Display name required");
        }

        this.id = UUID.randomUUID().toString();
        this.displayName = displayName.trim();
        this.phone = phone;
        this.email = email;
        this.tags = new ArrayList<>();

        this.dateAdded = LocalDateTime.now();
        this.contactCount = 0;
    }

    public String getId() { return id; }
    public String getDisplayName() { return displayName; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public List<Tag> getTags() { return tags; }
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

    public void incrementContactCount() {
        contactCount++;
    }

    // UC-12: Add tag
    public void addTag(Tag tag) {

        if (tag == null) {
            throw new IllegalArgumentException("Tag required");
        }

        String cleaned = tag.getName().trim();

        for (Tag t : tags) {
            if (t.getName().equalsIgnoreCase(cleaned)) {
                return; // prevent duplicate
            }
        }

        tags.add(new Tag(cleaned));
    }

    // UC-12: Remove tag
    public void removeTag(String tagName) {

        if (tagName == null) return;

        String cleaned = tagName.trim();

        tags.removeIf(t -> t.getName().equalsIgnoreCase(cleaned));
    }

    @Override
    public String toString() {

        StringBuilder tagString = new StringBuilder();

        for (Tag t : tags) {
            tagString.append(t.getName()).append(" ");
        }

        return "Contact{" +
                "tags=" + tagString +
                ", name='" + displayName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", dateAdded=" + dateAdded +
                ", contactCount=" + contactCount +
                '}';
    }
}