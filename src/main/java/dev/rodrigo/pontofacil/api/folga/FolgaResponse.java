package dev.rodrigo.pontofacil.api.folga;

import dev.rodrigo.pontofacil.domain.folga.Folga;

import java.time.LocalDate;

public record FolgaResponse(
        Long id,
        Long funcionarioId,
        String funcionarioNome,
        String tipo,
        LocalDate inicio,
        LocalDate fim,
        String status,
        String observacao
) {
    public static FolgaResponse de(Folga f) {
        return new FolgaResponse(
                f.getId(),
                f.getFuncionario().getId(),
                f.getFuncionario().getNome(),
                f.getTipo(),
                f.getInicio(),
                f.getFim(),
                f.getStatus(),
                f.getObservacao()
        );
    }
}
