package dev.rodrigo.pontofacil.shared;

/** Cálculos geográficos. */
public final class GeoUtil {

    private static final double RAIO_TERRA_METROS = 6_371_000.0;

    private GeoUtil() {}

    /** Distância em metros entre dois pontos (fórmula de Haversine). */
    public static double distanciaMetros(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return RAIO_TERRA_METROS * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}
