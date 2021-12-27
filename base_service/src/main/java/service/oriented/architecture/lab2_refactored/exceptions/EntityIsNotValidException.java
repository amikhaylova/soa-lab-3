package service.oriented.architecture.lab2_refactored.exceptions;


public class EntityIsNotValidException extends RuntimeException {

    public EntityIsNotValidException(String message) {
        super(message);
    }

}
