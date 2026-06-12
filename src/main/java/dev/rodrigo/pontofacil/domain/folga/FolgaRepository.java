package dev.rodrigo.pontofacil.domain.folga;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolgaRepository extends JpaRepository<Folga, Long> {

    List<Folga> findByFuncionarioEmpresaIdOrderByInicioDesc(Long empresaId);

    void deleteByFuncionarioId(Long funcionarioId);
}
