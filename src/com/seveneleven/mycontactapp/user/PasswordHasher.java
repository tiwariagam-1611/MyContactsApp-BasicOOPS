package com.seveneleven.mycontactapp.user;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public interface PasswordHasher {
    String hash(String rawPassword);
    boolean matches(String rawPassword, String storedHash);

    /**
     * Simple SHA-256 hasher Suitable for demos only.
     */
    final class SimpleSha256PasswordHasher implements PasswordHasher {

        @Override
        public String hash(String rawPassword) {
            if (rawPassword == null) {
                throw new IllegalArgumentException("Password cannot be null");
            }
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] digest = md.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
                return toHex(digest);
            } catch (Exception e) {
                throw new IllegalStateException("Hashing failed", e);
            }
        }

        @Override
        public boolean matches(String rawPassword, String storedHash) {
            if (rawPassword == null || storedHash == null) return false;
            try {
                String actual = hash(rawPassword);
                return constantTimeEquals(actual, storedHash);
            } catch (Exception e) {
                return false;
            }
        }

        // --- Helpers ---

        private static String toHex(byte[] bytes) {
            StringBuilder sb = new StringBuilder(bytes.length * 2);
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }

        private static boolean constantTimeEquals(String a, String b) {
            if (a == null || b == null) return false;
            if (a.length() != b.length()) return false;
            int diff = 0;
            for (int i = 0; i < a.length(); i++) {
                diff |= a.charAt(i) ^ b.charAt(i);
            }
            return diff == 0;
        }
    }
}