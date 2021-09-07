package com.reesecake.simple_hsk.security;

public enum ApplicationUserPermission {
    // TODO: Add permissions?
    ;

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
