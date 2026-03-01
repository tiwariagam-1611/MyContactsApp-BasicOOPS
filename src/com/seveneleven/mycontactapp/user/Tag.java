package com.seveneleven.mycontactapp.user;

import java.util.UUID;

public class Tag {

    private final String id;
    private String name;

    public Tag(String name) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Tag name required");
        }

        this.id = UUID.randomUUID().toString();
        this.name = name.trim();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Tag name required");
        }
        this.name = name.trim();
    }

    @Override
    public String toString() {
        return name;
    }
}