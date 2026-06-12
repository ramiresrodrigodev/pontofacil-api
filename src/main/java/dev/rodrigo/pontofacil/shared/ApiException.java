package dev.rodrigo.pontofacil.shared;

import org.springframework.http.HttpStatus;

/** Excecao de negocio com status HTTP associado. */
public class ApiException extends RuntimeException {

    private final HttpStatus status;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public static ApiException naoEncontrado(String msg) {
        return new ApiException(HttpStatus.NOT_FOUND, msg);
    }

    public static ApiException naoAutorizado(String msg) {
        return new ApiException(HttpStatus.UNAUTHORIZED, msg);
    }

    public static ApiException requisicaoInvalida(String msg) {
        return new ApiException(HttpStatus.BAD_REQUEST, msg);
    }
}
