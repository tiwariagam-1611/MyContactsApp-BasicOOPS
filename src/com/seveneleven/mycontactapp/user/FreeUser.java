package com.seveneleven.mycontactapp.user;

public class FreeUser extends User {
    public FreeUser(String name, String email, String password, String pin, UserProfile profile) {
        super(name, email, password, Plan.FREE, pin, profile);
    }

    @Override
    public String perks() {
        return "Basic sync, Community support";
    }
}
