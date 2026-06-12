package dev.rodrigo.pontofacil.api.ponto;

import dev.rodrigo.pontofacil.domain.funcionario.Funcionario;
import dev.rodrigo.pontofacil.domain.funcionario.FuncionarioRepository;
import dev.rodrigo.pontofacil.domain.ponto.Ponto;
import dev.rodrigo.pontofacil.domain.ponto.PontoRepository;
import dev.rodrigo.pontofacil.domain.usuario.Usuario;
import dev.rodrigo.pontofacil.shared.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PontoService {

    private final PontoRepository pontoRepository;
    private final FuncionarioRepository funcionarioRepository;

    @Transactional(readOnly = true)
    public List<PontoResponse> listar(Usuario usuarioLogado) {
        return pontoRepository
                .findByFuncionarioEmpresaIdOrderByDataDescIdDesc(usuarioLogado.getEmpresa().getId())
                .stream()
                .map(PontoResponse::de)
                .toList();
    }

    @Transactional
    public PontoResponse marcar(MarcarPontoRequest request, Usuario usuarioLogado) {
        Funcionario funcionario = buscarFuncionarioDaEmpresa(request.funcionarioId(), usuarioLogado);
        LocalDate hoje = LocalDate.now();
        LocalTime agora = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

        Ponto ponto = pontoRepository.findByFuncionarioIdAndData(funcionario.getId(), hoje)
                .orElseGet(() -> {
                    Ponto novo = new Ponto();
                    novo.setFuncionario(funcionario);
                    novo.setData(hoje);
                    return novo;
                });

        String tipo = request.tipo() == null ? "" : request.tipo().trim().toUpperCase();
        switch (tipo) {
            case "ENTRADA" -> {
                if (ponto.getEntrada() != null) throw ApiException.requisicaoInvalida("Entrada já registrada hoje");
                ponto.setEntrada(agora);
            }
            case "AL_SAIDA" -> {
                if (ponto.getEntrada() == null) throw ApiException.requisicaoInvalida("Registre a entrada primeiro");
                if (ponto.getAlSaida() != null) throw ApiException.requisicaoInvalida("Saída para almoço já registrada");
                ponto.setAlSaida(agora);
            }
            case "AL_RETORNO" -> {
                if (ponto.getAlSaida() == null) throw ApiException.requisicaoInvalida("Registre a saída para almoço primeiro");
                if (ponto.getAlRetorno() != null) throw ApiException.requisicaoInvalida("Retorno já registrado");
                ponto.setAlRetorno(agora);
            }
            case "SAIDA" -> {
                if (ponto.getEntrada() == null) throw ApiException.requisicaoInvalida("Registre a entrada primeiro");
                ponto.setSaida(agora);
            }
            default -> throw ApiException.requisicaoInvalida("Tipo de marcação inválido: " + request.tipo());
        }

        ponto.setStatus(ponto.getSaida() != null ? "COMPLETO" : "INCOMPLETO");
        return PontoResponse.de(pontoRepository.save(ponto));
    }

    @Transactional
    public PontoResponse criarManual(PontoManualRequest request, Usuario usuarioLogado) {
        Funcionario funcionario = buscarFuncionarioDaEmpresa(request.funcionarioId(), usuarioLogado);

        Ponto ponto = pontoRepository.findByFuncionarioIdAndData(funcionario.getId(), request.data())
                .orElseGet(() -> {
                    Ponto novo = new Ponto();
                    novo.setFuncionario(funcionario);
                    novo.setData(request.data());
                    return novo;
                });

        ponto.setEntrada(request.entrada());
        ponto.setAlSaida(request.alSaida());
        ponto.setAlRetorno(request.alRetorno());
        ponto.setSaida(request.saida());
        ponto.setObservacao(request.observacao());
        ponto.setStatus(request.saida() != null ? "COMPLETO" : "INCOMPLETO");

        return PontoResponse.de(pontoRepository.save(ponto));
    }

    private Funcionario buscarFuncionarioDaEmpresa(Long funcionarioId, Usuario usuarioLogado) {
        return funcionarioRepository.findById(funcionarioId)
                .filter(f -> f.getEmpresa().getId().equals(usuarioLogado.getEmpresa().getId()))
                .orElseThrow(() -> ApiException.naoEncontrado("Funcionário não encontrado"));
    }
}
