package dev.rodrigo.pontofacil.api.ponto;

import jakarta.validation.constraints.NotNull;

/** Marca uma batida de ponto do funcionario no dia atual. tipo: ENTRADA, AL_SAIDA, AL_RETORNO, SAIDA */
public record MarcarPontoRequest(
        @NotNull Long funcionarioId,
        @NotNull String tipo,
        Double latitude,
        Double longitude
) {}
