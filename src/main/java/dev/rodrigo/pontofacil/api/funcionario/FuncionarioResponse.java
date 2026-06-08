package dev.rodrigo.pontofacil.api.funcionario;

import dev.rodrigo.pontofacil.domain.funcionario.Funcionario;
import java.math.BigDecimal;
import java.time.LocalDate;

public record FuncionarioResponse(
        Long id,
        String nome,
        String cargo,
        String departamento,
        BigDecimal salario,
        String contrato,
        String escala,
        String status,
        LocalDate dataAdmissao,
        String email,
        String telefone
) {
    public static FuncionarioResponse de(Funcionario f) {
        return new FuncionarioResponse(
                f.getId(), f.getNome(), f.getCargo(), f.getDepartamento(),
                f.getSalario(), f.getContrato() != null ? f.getContrato().name() : null,
                f.getEscala(), f.getStatus(), f.getDataAdmissao(),
                f.getEmail(), f.getTelefone()
        );
    }
}