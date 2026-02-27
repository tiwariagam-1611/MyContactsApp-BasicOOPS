package com.seveneleven.mycontactapp.user;

import java.util.List;

public class ContactService {

    private final ContactRepository repo;

    public ContactService(ContactRepository repo) {
        this.repo = repo;
    }

    public Contact createPerson(String name, String phone, String email) {
        Contact c = new PersonContact(name, phone, email);
        repo.save(c);
        return c;
    }

    public Contact createOrganization(String name, String phone, String email) {
        Contact c = new OrganizationContact(name, phone, email);
        repo.save(c);
        return c;
    }

    public Contact viewContactByName(String name) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Contact name required");
        }

        List<Contact> contacts = repo.findAll();

        for (Contact c : contacts) {
            if (c.getDisplayName().equalsIgnoreCase(name.trim())) {
                return c;
            }
        }

        throw new IllegalArgumentException("Contact not found");
    }
}