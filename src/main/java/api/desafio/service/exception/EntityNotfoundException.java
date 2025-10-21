package api.desafio.service.exception;

public class EntityNotfoundException extends RuntimeException{
    public EntityNotfoundException(String message){
        super(message);
    }
}
