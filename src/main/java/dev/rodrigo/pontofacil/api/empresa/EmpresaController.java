package dev.rodrigo.pontofacil.api.empresa;

import dev.rodrigo.pontofacil.domain.usuario.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empresa")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaService service;

    @GetMapping
    public ResponseEntity<EmpresaResponse> obter(@AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(service.obter(usuario));
    }

    @PutMapping("/geofence")
    public ResponseEntity<EmpresaResponse> definirGeofence(@Valid @RequestBody GeofenceRequest request,
                                                           @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(service.definirGeofence(request, usuario));
    }

    @DeleteMapping("/geofence")
    public ResponseEntity<EmpresaResponse> removerGeofence(@AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(service.removerGeofence(usuario));
    }
}
