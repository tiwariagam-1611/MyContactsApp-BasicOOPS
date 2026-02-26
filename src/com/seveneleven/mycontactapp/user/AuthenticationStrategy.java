package com.seveneleven.mycontactapp.user;


public interface AuthenticationStrategy {

    User authenticate(String identifier, String secret);
}