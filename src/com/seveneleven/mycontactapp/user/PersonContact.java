package com.seveneleven.mycontactapp.user;

public class PersonContact extends Contact {

    public PersonContact(String name, String phone, String email) {
        super(name, phone, email);
    }


    public PersonContact(PersonContact other) {
        super(other);
    }

    @Override
    public String getType() {
        return "Person";
    }
}