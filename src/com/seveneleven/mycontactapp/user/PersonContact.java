package com.seveneleven.mycontactapp.user;

public class PersonContact extends Contact {
    public PersonContact(String name, String phone, String email) {
        super(name, phone, email);
    }

    @Override
    public Type getType() {
        return Type.PERSON;
    }
}
