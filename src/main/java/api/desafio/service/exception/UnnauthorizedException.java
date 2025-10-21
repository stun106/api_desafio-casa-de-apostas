package api.desafio.service.exception;

public class UnnauthorizedException extends RuntimeException {
    public UnnauthorizedException (String message) {
        super(message);
    }
}
