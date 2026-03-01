package com.seveneleven.mycontactapp.user;

import java.util.ArrayList;
import java.util.List;

public class TagSearchCriteria implements SearchCriteria {

    @Override
    public List<Contact> search(List<Contact> contacts, String value) {

        List<Contact> result = new ArrayList<>();

        for (Contact contact : contacts) {

            for (Tag tag : contact.getTags()) {

                if (tag.getName().toLowerCase()
                        .contains(value.toLowerCase())) {

                    result.add(contact);
                    break;
                }
            }
        }

        return result;
    }
}