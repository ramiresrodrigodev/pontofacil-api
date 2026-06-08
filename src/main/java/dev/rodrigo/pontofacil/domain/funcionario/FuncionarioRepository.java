package dev.rodrigo.pontofacil.domain.funcionario;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    List<Funcionario> findByEmpresaId(Long empresaId);
}