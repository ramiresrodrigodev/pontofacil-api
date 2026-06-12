package dev.rodrigo.pontofacil.api.ponto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

/** Registro manual de ponto feito pelo gestor. */
public record PontoManualRequest(
        @NotNull Long funcionarioId,
        @NotNull LocalDate data,
        @NotNull LocalTime entrada,
        LocalTime alSaida,
        LocalTime alRetorno,
        LocalTime saida,
        String observacao
) {}
