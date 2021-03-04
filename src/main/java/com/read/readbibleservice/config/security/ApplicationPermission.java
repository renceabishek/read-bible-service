package com.read.readbibleservice.config.security;

public enum ApplicationPermission {
    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write"),
    OTHER_READ("other:read"),
    OTHER_WRITE("other:write");

    private String permission;

    ApplicationPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
