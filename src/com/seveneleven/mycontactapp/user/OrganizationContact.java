package com.seveneleven.mycontactapp.user;

public class OrganizationContact extends Contact {
    public OrganizationContact(String name, String phone, String email) {
        super(name, phone, email);
    }

    @Override
    public Type getType() {
        return Type.ORGANIZATION;
    }
}
