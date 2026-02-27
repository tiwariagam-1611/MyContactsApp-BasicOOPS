package com.seveneleven.mycontactapp.user;

import java.util.*;

public class ContactRepository {
    private final Map<String, Contact> contacts = new LinkedHashMap<>();

    public void save(Contact contact) {
        contacts.put(contact.getId(), contact);
    }

    public Contact findById(String contactId) {
        return contacts.get(contactId);
    }

    public List<Contact> findAll() {
        return new ArrayList<>(contacts.values());
    }

    public int count() {
        return contacts.size();
    }
    
    public void update(Contact contact) {

        if (!contacts.containsKey(contact.getId())) {
            throw new IllegalArgumentException("Contact not found");
        }

        contacts.put(contact.getId(), contact);
    }
    public void deleteById(String id) {

        if (!contacts.containsKey(id)) {
            throw new IllegalArgumentException("Contact not found");
        }

        contacts.remove(id);
    }
}
