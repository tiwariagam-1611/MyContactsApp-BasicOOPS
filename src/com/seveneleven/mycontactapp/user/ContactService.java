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
	public void deleteContactByName(String name) {

	    if (name == null || name.isBlank()) {
	        throw new IllegalArgumentException("Contact name required");
	    }

	    for (Contact c : repo.findAll()) {

	        if (c.getDisplayName().equalsIgnoreCase(name.trim())) {

	            repo.deleteById(c.getId());
	            return;
	        }
	    }

	    throw new IllegalArgumentException("Contact not found");
	}


	public Contact editContactByName(String name, String newPhone, String newEmail) {

		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Contact name required");
		}

		for (Contact original : repo.findAll()) {

			if (original.getDisplayName().equalsIgnoreCase(name.trim())) {

				Contact copy;

				if (original instanceof PersonContact) {
					copy = new PersonContact((PersonContact) original);
				} else {
					copy = new OrganizationContact((OrganizationContact) original);
				}

				if (newPhone != null && !newPhone.isBlank()) {
					copy.setPhone(newPhone);
				}

				if (newEmail != null && !newEmail.isBlank()) {
					copy.setEmail(newEmail);
				}

				repo.update(copy);

				return copy;
			}
		}


		throw new IllegalArgumentException("Contact not found");
	}
}