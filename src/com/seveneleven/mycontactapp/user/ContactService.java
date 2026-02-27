package com.seveneleven.mycontactapp.user;

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
}
