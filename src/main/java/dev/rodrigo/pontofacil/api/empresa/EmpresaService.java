package dev.rodrigo.pontofacil.api.empresa;

import dev.rodrigo.pontofacil.domain.empresa.Empresa;
import dev.rodrigo.pontofacil.domain.empresa.EmpresaRepository;
import dev.rodrigo.pontofacil.domain.usuario.Perfil;
import dev.rodrigo.pontofacil.domain.usuario.Usuario;
import dev.rodrigo.pontofacil.shared.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    @Transactional(readOnly = true)
    public EmpresaResponse obter(Usuario usuarioLogado) {
        return EmpresaResponse.de(carregar(usuarioLogado));
    }

    @Transactional
    public EmpresaResponse definirGeofence(GeofenceRequest request, Usuario usuarioLogado) {
        exigirGestor(usuarioLogado);
        Empresa empresa = carregar(usuarioLogado);
        empresa.setLatitude(request.latitude());
        empresa.setLongitude(request.longitude());
        empresa.setRaioMetros(request.raioMetros());
        return EmpresaResponse.de(empresaRepository.save(empresa));
    }

    @Transactional
    public EmpresaResponse removerGeofence(Usuario usuarioLogado) {
        exigirGestor(usuarioLogado);
        Empresa empresa = carregar(usuarioLogado);
        empresa.setLatitude(null);
        empresa.setLongitude(null);
        empresa.setRaioMetros(null);
        return EmpresaResponse.de(empresaRepository.save(empresa));
    }

    private Empresa carregar(Usuario usuarioLogado) {
        return empresaRepository.findById(usuarioLogado.getEmpresa().getId())
                .orElseThrow(() -> ApiException.naoEncontrado("Empresa não encontrada"));
    }

    private void exigirGestor(Usuario usuario) {
        if (usuario.getPerfil() != Perfil.GESTOR) {
            throw ApiException.naoAutorizado("Apenas o gestor pode alterar a cerca virtual.");
        }
    }
}
