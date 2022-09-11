package com.greenfox.masterworkwebshop.users.models.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {
  ROLE_ADMIN {
    @Override
    public SimpleGrantedAuthority getAuthority() {
      return new SimpleGrantedAuthority(this.name());
    }
  }, ROLE_USER {
    @Override
    public SimpleGrantedAuthority getAuthority() {
      return new SimpleGrantedAuthority(this.name());
    }
  };

  public abstract SimpleGrantedAuthority getAuthority();
}
