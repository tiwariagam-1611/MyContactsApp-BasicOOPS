package com.seveneleven.mycontactapp.user;

import java.util.List;

public class ContactService {

    private final ContactRepository repo;

    public ContactService(ContactRepository repo) {
        this.repo = repo;
    }

    public Contact createContact(String name, String phone, String email, String tag) {

        if (tag == null || tag.isBlank()) {
            throw new IllegalArgumentException("Tag required");
        }

        Contact contact;

        if (tag.equalsIgnoreCase("PERSON")) {
            contact = new PersonContact(name, phone, email);
        } else if (tag.equalsIgnoreCase("ORG")) {
            contact = new OrganizationContact(name, phone, email);
        } else {
            throw new IllegalArgumentException("Invalid tag");
        }

        repo.save(contact);
        return contact;
    }

    public List<Contact> getAllContacts() {
        return repo.findAll();
    }

    public Contact viewContactByName(String name) {
        return repo.findAll()
                .stream()
                .filter(c -> c.getDisplayName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    public Contact editContactByName(String name, String newPhone, String newEmail) {

        Contact contact = viewContactByName(name);

        if (newPhone != null && !newPhone.isBlank()) {
            contact.setPhone(newPhone);
        }

        if (newEmail != null && !newEmail.isBlank()) {
            contact.setEmail(newEmail);
        }

        repo.update(contact);
        return contact;
    }

    public void deleteContactByName(String name) {

        Contact contact = viewContactByName(name);
        repo.deleteById(contact.getId());
    }
}