package com.seveneleven.mycontactapp.user;

public class OrganizationContact extends Contact {

    public OrganizationContact(String name, String phone, String email) {
        super(name, phone, email, "ORG");
    }

    public OrganizationContact(OrganizationContact other) {
        super(other);
    }

    @Override
    public Contact copy() {
        return new OrganizationContact(this);
    }
}