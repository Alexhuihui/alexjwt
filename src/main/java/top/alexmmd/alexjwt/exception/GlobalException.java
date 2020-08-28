package top.alexmmd.alexjwt.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.alexmmd.alexjwt.model.ResultUtils;

/**
 * @author 汪永晖
 */
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(IdempotentException.class)
    public ResultUtils idempotentException(IdempotentException e) {
        return new ResultUtils(500, e.getMessage());
    }
}
