package com.seveneleven.mycontactapp.user;

import java.util.ArrayList;
import java.util.List;

public class EmailSearchCriteria implements SearchCriteria {

    @Override
    public List<Contact> search(List<Contact> contacts, String value) {

        List<Contact> result = new ArrayList<>();

        for (Contact contact : contacts) {

            if (contact.getEmail() != null &&
                    contact.getEmail().toLowerCase()
                            .contains(value.toLowerCase())) {

                result.add(contact);
            }
        }

        return result;
    }
}