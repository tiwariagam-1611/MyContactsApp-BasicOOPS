package com.seveneleven.mycontactapp.user;


//Strategy interface for authentication.
//  
//For BasicAuth: identifier = email,  secret = password
//For OAuth-like: identifier = display name,  secret = PIN

public interface AuthenticationStrategy {
    User authenticate(String identifier, String secret);
}
