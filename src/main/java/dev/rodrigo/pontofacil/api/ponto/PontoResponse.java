package dev.rodrigo.pontofacil.api.ponto;

import dev.rodrigo.pontofacil.domain.ponto.Ponto;

import java.time.LocalDate;
import java.time.LocalTime;

public record PontoResponse(
        Long id,
        Long funcionarioId,
        String funcionarioNome,
        LocalDate data,
        LocalTime entrada,
        LocalTime alSaida,
        LocalTime alRetorno,
        LocalTime saida,
        String status,
        String observacao
) {
    public static PontoResponse de(Ponto p) {
        return new PontoResponse(
                p.getId(),
                p.getFuncionario().getId(),
                p.getFuncionario().getNome(),
                p.getData(),
                p.getEntrada(),
                p.getAlSaida(),
                p.getAlRetorno(),
                p.getSaida(),
                p.getStatus(),
                p.getObservacao()
        );
    }
}
