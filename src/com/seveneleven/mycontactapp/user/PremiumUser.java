package com.seveneleven.mycontactapp.user;

public class PremiumUser extends User {
    public PremiumUser(String name, String email, String password, String pin, UserProfile profile) {
        super(name, email, password, Plan.PREMIUM, pin, profile);
    }

    @Override
    public String perks() {
        return "Priority sync, Premium support";
    }
}
