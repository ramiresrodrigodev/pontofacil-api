package dev.rodrigo.pontofacil.api.folga;

import dev.rodrigo.pontofacil.domain.folga.Folga;
import dev.rodrigo.pontofacil.domain.folga.FolgaRepository;
import dev.rodrigo.pontofacil.domain.funcionario.Funcionario;
import dev.rodrigo.pontofacil.domain.funcionario.FuncionarioRepository;
import dev.rodrigo.pontofacil.domain.usuario.Usuario;
import dev.rodrigo.pontofacil.shared.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FolgaService {

    private static final Set<String> STATUS_VALIDOS = Set.of("PENDENTE", "APROVADO", "RECUSADO");

    private final FolgaRepository folgaRepository;
    private final FuncionarioRepository funcionarioRepository;

    @Transactional(readOnly = true)
    public List<FolgaResponse> listar(Usuario usuarioLogado) {
        return folgaRepository
                .findByFuncionarioEmpresaIdOrderByInicioDesc(usuarioLogado.getEmpresa().getId())
                .stream()
                .map(FolgaResponse::de)
                .toList();
    }

    @Transactional
    public FolgaResponse criar(FolgaRequest request, Usuario usuarioLogado) {
        Funcionario funcionario = funcionarioRepository.findById(request.funcionarioId())
                .filter(f -> f.getEmpresa().getId().equals(usuarioLogado.getEmpresa().getId()))
                .orElseThrow(() -> ApiException.naoEncontrado("Funcionário não encontrado"));

        Folga folga = new Folga();
        folga.setFuncionario(funcionario);
        folga.setTipo(request.tipo());
        folga.setInicio(request.inicio());
        folga.setFim(request.fim() != null ? request.fim() : request.inicio());
        folga.setObservacao(request.observacao());
        folga.setStatus("PENDENTE");

        return FolgaResponse.de(folgaRepository.save(folga));
    }

    @Transactional
    public FolgaResponse atualizarStatus(Long id, String status, Usuario usuarioLogado) {
        String novo = status == null ? "" : status.trim().toUpperCase();
        if (!STATUS_VALIDOS.contains(novo)) {
            throw ApiException.requisicaoInvalida("Status inválido: " + status);
        }
        Folga folga = folgaRepository.findById(id)
                .filter(f -> f.getFuncionario().getEmpresa().getId().equals(usuarioLogado.getEmpresa().getId()))
                .orElseThrow(() -> ApiException.naoEncontrado("Folga não encontrada"));
        folga.setStatus(novo);
        return FolgaResponse.de(folgaRepository.save(folga));
    }
}
