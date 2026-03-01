package com.seveneleven.mycontactapp.user;

import java.util.Collections;
import java.util.List;

public class ContactService {

    private final ContactRepository repo;

    public ContactService(ContactRepository repo) {
        this.repo = repo;
    }

    // UC-11 + UC-12
    public Contact createContact(String name, String phone, String email, String tagName) {

        Contact contact = new Contact(name, phone, email);

        if (tagName != null && !tagName.isBlank()) {
            contact.addTag(new Tag(tagName));
        }

        repo.save(contact);
        return contact;
    }

    public List<Contact> getAllContacts() {
        return repo.findAll();
    }

    public Contact viewContactByName(String name) {

        for (Contact contact : repo.findAll()) {

            if (contact.getDisplayName().equalsIgnoreCase(name)) {
                contact.incrementContactCount();
                repo.update(contact);
                return contact;
            }
        }

        throw new RuntimeException("Contact not found");
    }

    public Contact editContactByName(String name,
                                     String newPhone,
                                     String newEmail) {

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

    // UC-12 Add Tag
    public void addTagToContact(String name, String tagName) {
        Contact contact = viewContactByName(name);
        contact.addTag(new Tag(tagName));
        repo.update(contact);
    }

    // UC-12 Remove Tag
    public void removeTagFromContact(String name, String tagName) {
        Contact contact = viewContactByName(name);
        contact.removeTag(tagName);
        repo.update(contact);
    }

    // UC-09 Search
    public List<Contact> searchContacts(String type, String value) {

        List<Contact> allContacts = repo.findAll();
        SearchCriteria operation;

        if (type.equalsIgnoreCase("name")) {
            operation = new NameSearchCriteria();
        } else if (type.equalsIgnoreCase("phone")) {
            operation = new PhoneSearchCriteria();
        } else if (type.equalsIgnoreCase("email")) {
            operation = new EmailSearchCriteria();
        } else if (type.equalsIgnoreCase("tag")) {
            operation = new TagSearchCriteria();
        } else {
            throw new IllegalArgumentException("Invalid search type");
        }

        return operation.search(allContacts, value);
    }

    // UC-10 Filter
    public List<Contact> filterContacts(String type, String value) {

        List<Contact> contacts = repo.findAll();

        if (type.equalsIgnoreCase("tag")) {

            contacts.removeIf(contact -> {
                for (Tag tag : contact.getTags()) {
                	if (tag.getName().equalsIgnoreCase(value.trim())) {
                        return false;
                    }
                }
                return true;
            });

        } else if (type.equalsIgnoreCase("recent")) {

            Collections.sort(contacts,
                    (c1, c2) -> c2.getDateAdded().compareTo(c1.getDateAdded()));

        } else if (type.equalsIgnoreCase("frequent")) {

            Collections.sort(contacts,
                    (c1, c2) -> Integer.compare(
                            c2.getContactCount(),
                            c1.getContactCount()));

        } else {
            throw new IllegalArgumentException("Invalid filter type");
        }

        return contacts;
    }
}