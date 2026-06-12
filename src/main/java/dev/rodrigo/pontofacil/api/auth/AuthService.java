package dev.rodrigo.pontofacil.api.auth;

import dev.rodrigo.pontofacil.domain.usuario.UsuarioRepository;
import dev.rodrigo.pontofacil.security.JwtService;
import dev.rodrigo.pontofacil.shared.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        var usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> ApiException.naoAutorizado("E-mail ou senha inválidos"));

        if (!passwordEncoder.matches(request.senha(), usuario.getSenha())) {
            throw ApiException.naoAutorizado("E-mail ou senha inválidos");
        }

        String token = jwtService.gerarToken(usuario.getEmail());

        return new LoginResponse(
                token,
                usuario.getNome(),
                usuario.getPerfil().name()
        );
    }
}