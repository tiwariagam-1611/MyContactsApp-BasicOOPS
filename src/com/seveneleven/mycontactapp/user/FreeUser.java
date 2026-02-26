package com.seveneleven.mycontactapp.user;

public class FreeUser extends User {
    private boolean adsEnabled = true;

    public FreeUser(String name, String email, String password, UserProfile profile) {
        super(name, email, password, User.Plan.FREE, profile);
    }

    public boolean isAdsEnabled() { return adsEnabled; }
    public void setAdsEnabled(boolean adsEnabled) { this.adsEnabled = adsEnabled; }

    @Override
    public String perks() {
        return "Basic sync, Community support";
    }
}