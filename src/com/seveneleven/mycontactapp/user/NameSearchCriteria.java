package com.seveneleven.mycontactapp.user;

import java.util.ArrayList;
import java.util.List;

public class NameSearchCriteria implements SearchCriteria {

    @Override
    public List<Contact> search(List<Contact> contacts, String value) {

        List<Contact> result = new ArrayList<>();

        for (Contact contact : contacts) {

            if (contact.getDisplayName() != null &&
                    contact.getDisplayName().toLowerCase()
                            .contains(value.toLowerCase())) {

                result.add(contact);
            }
        }

        return result;
    }
}