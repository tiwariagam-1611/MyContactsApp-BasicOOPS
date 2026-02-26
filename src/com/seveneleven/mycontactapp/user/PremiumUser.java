package com.seveneleven.mycontactapp.user;

import java.util.Random;

public class PremiumUser extends User {
    private final String supportPin;   // 4-digit PIN
    private boolean cloudBackup = true;

    public PremiumUser(String name, String email, String password, UserProfile profile) {
        super(name, email, password, User.Plan.PREMIUM, profile);
        this.supportPin = generateSupportPin();
    }

    public String getSupportPin() { return supportPin; }
    public boolean isCloudBackupEnabled() { return cloudBackup; }
    public void setCloudBackup(boolean enabled) { this.cloudBackup = enabled; }

    @Override
    public String perks() {
        return "Priority support, Cloud backup";
    }

    private String generateSupportPin() {
        Random r = new Random();
        int pin = 1000 + r.nextInt(9000); // 1000..9999
        return String.valueOf(pin);
    }
}