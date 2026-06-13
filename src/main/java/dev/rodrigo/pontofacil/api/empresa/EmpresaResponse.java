package dev.rodrigo.pontofacil.api.empresa;

import dev.rodrigo.pontofacil.domain.empresa.Empresa;

public record EmpresaResponse(
        Long id,
        String nome,
        String cnpj,
        Double latitude,
        Double longitude,
        Integer raioMetros,
        boolean geofenceAtivo
) {
    public static EmpresaResponse de(Empresa e) {
        return new EmpresaResponse(
                e.getId(), e.getNome(), e.getCnpj(),
                e.getLatitude(), e.getLongitude(), e.getRaioMetros(),
                e.temGeofence()
        );
    }
}
