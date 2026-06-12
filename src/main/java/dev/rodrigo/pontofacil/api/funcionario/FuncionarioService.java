package dev.rodrigo.pontofacil.api.funcionario;

import dev.rodrigo.pontofacil.domain.folga.FolgaRepository;
import dev.rodrigo.pontofacil.domain.funcionario.Contrato;
import dev.rodrigo.pontofacil.domain.funcionario.Funcionario;
import dev.rodrigo.pontofacil.domain.funcionario.FuncionarioRepository;
import dev.rodrigo.pontofacil.domain.ponto.PontoRepository;
import dev.rodrigo.pontofacil.domain.usuario.Usuario;
import dev.rodrigo.pontofacil.shared.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository repository;
    private final PontoRepository pontoRepository;
    private final FolgaRepository folgaRepository;

    public List<FuncionarioResponse> listar(Usuario usuarioLogado) {
        return repository.findByEmpresaId(usuarioLogado.getEmpresa().getId())
                .stream()
                .map(FuncionarioResponse::de)
                .toList();
    }

    public FuncionarioResponse criar(FuncionarioRequest request, Usuario usuarioLogado) {
        var f = new Funcionario();
        f.setEmpresa(usuarioLogado.getEmpresa());
        f.setNome(request.nome());
        f.setCargo(request.cargo());
        f.setDepartamento(request.departamento());
        f.setSalario(request.salario());
        f.setContrato(request.contrato() != null ? Contrato.valueOf(request.contrato()) : null);
        f.setEscala(request.escala());
        f.setStatus(request.status() != null ? request.status() : "ATIVO");
        f.setDataAdmissao(request.dataAdmissao());
        f.setEmail(request.email());
        f.setTelefone(request.telefone());
        return FuncionarioResponse.de(repository.save(f));
    }

    public FuncionarioResponse atualizar(Long id, FuncionarioRequest request, Usuario usuarioLogado) {
        var f = repository.findById(id)
                .filter(func -> func.getEmpresa().getId().equals(usuarioLogado.getEmpresa().getId()))
                .orElseThrow(() -> ApiException.naoEncontrado("Funcionário não encontrado"));
        f.setNome(request.nome());
        f.setCargo(request.cargo());
        f.setDepartamento(request.departamento());
        f.setSalario(request.salario());
        f.setContrato(request.contrato() != null ? Contrato.valueOf(request.contrato()) : null);
        f.setEscala(request.escala());
        f.setStatus(request.status());
        f.setDataAdmissao(request.dataAdmissao());
        f.setEmail(request.email());
        f.setTelefone(request.telefone());
        return FuncionarioResponse.de(repository.save(f));
    }

    @Transactional
    public void deletar(Long id, Usuario usuarioLogado) {
        var f = repository.findById(id)
                .filter(func -> func.getEmpresa().getId().equals(usuarioLogado.getEmpresa().getId()))
                .orElseThrow(() -> ApiException.naoEncontrado("Funcionário não encontrado"));
        // Remove registros dependentes antes do funcionário (evita violacao de FK)
        pontoRepository.deleteByFuncionarioId(f.getId());
        folgaRepository.deleteByFuncionarioId(f.getId());
        repository.delete(f);
    }
}