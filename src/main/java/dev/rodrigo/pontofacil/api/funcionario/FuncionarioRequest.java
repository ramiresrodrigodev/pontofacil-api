package dev.rodrigo.pontofacil.api.funcionario;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

public record FuncionarioRequest(
        @NotBlank String nome,
        String cargo,
        String departamento,
        BigDecimal salario,
        String contrato,
        String escala,
        String status,
        LocalDate dataAdmissao,
        String email,
        String telefone
) {}