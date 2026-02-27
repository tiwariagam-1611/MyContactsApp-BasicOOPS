package com.seveneleven.mycontactapp.user;

import java.util.ArrayList;
import java.util.List;

public class PhoneSearchCriteria implements SearchCriteria {

    @Override
    public List<Contact> search(List<Contact> contacts, String value) {

        List<Contact> result = new ArrayList<>();

        for (Contact contact : contacts) {

            if (contact.getPhone() != null &&
                    contact.getPhone().toLowerCase()
                            .contains(value.toLowerCase())) {

                result.add(contact);
            }
        }

        return result;
    }
}