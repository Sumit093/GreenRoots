package com.ecoscholars.entity;

/**
 * Enum for user roles in the system
 */
public enum UserRole {
    STUDENT("Student"),
    SCHOOL_ADMIN("School Administrator"),
    ADMIN("Administrator"),
    SUPER_ADMIN("Super Administrator");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
