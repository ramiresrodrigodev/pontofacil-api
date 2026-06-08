package dev.rodrigo.pontofacil.api.auth;

public record LoginResponse(
        String token,
        String nome,
        String perfil
) {}