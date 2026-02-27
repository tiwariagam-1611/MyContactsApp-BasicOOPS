package com.seveneleven.mycontactapp.user;

public class PersonContact extends Contact {

    public PersonContact(String name, String phone, String email) {
        super(name, phone, email, "PERSON");
    }

    public PersonContact(PersonContact other) {
        super(other);
    }

    @Override
    public Contact copy() {
        return new PersonContact(this);
    }
}