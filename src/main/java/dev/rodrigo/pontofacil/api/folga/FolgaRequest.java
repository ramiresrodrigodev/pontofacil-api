package dev.rodrigo.pontofacil.api.folga;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record FolgaRequest(
        @NotNull Long funcionarioId,
        @NotBlank String tipo,
        @NotNull LocalDate inicio,
        LocalDate fim,
        String observacao
) {}
