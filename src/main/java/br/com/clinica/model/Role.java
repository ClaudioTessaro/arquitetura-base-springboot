package br.com.clinica.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  ROLE_ADMIN, ROLE_SECRETARIA, ROLE_PACIENTE;

  public String getAuthority() {
    return name();
  }

}
