package dev.rodrigo.pontofacil.domain.ponto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PontoRepository extends JpaRepository<Ponto, Long> {

    List<Ponto> findByFuncionarioEmpresaIdOrderByDataDescIdDesc(Long empresaId);

    Optional<Ponto> findByFuncionarioIdAndData(Long funcionarioId, LocalDate data);

    void deleteByFuncionarioId(Long funcionarioId);
}
