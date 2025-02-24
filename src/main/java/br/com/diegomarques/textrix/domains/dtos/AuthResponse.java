package br.com.diegomarques.textrix.domains.dtos;

import br.com.diegomarques.textrix.domains.enums.TipoRole;

import java.util.Set;

public record AuthResponse(
        String token,
        String tipoToken,
        String email,
        Set<TipoRole> roles) {

    public AuthResponse(String token, String email, Set<TipoRole> roles) {
        this(token, "Bearer", email, roles);
    }
}
