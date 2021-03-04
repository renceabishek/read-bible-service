package com.read.readbibleservice.config.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.read.readbibleservice.config.security.ApplicationPermission.*;

public enum ApplicationRole {
    USER(Sets.newHashSet(OTHER_READ,OTHER_WRITE)),
    ADMIN(Sets.newHashSet(OTHER_READ,OTHER_WRITE, ADMIN_READ, ADMIN_WRITE));

    private Set<ApplicationPermission> permissions;

    ApplicationRole(Set<ApplicationPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorieis = getPermissions().stream()
                .map(permissions -> new SimpleGrantedAuthority(permissions.getPermission()))
                .collect(Collectors.toSet());

        authorieis.add(new SimpleGrantedAuthority("ROLE_"+this.name()));

        return authorieis;

    }
}
