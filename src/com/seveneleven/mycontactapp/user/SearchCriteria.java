package com.seveneleven.mycontactapp.user;

import java.util.List;

public interface SearchCriteria {

    List<Contact> search(List<Contact> contacts, String value);
}