package api.desafio.service.exception;

public class UniqueViolationExeception extends RuntimeException{
    public UniqueViolationExeception (String message) {
        super(message);
    }
}
