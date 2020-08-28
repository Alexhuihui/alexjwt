package top.alexmmd.alexjwt.exception;

/**
 * @author 汪永晖
 */
public class IdempotentException extends RuntimeException {

    public IdempotentException(String message) {
        super(message);
    }
}
