package bigtree.home.exception.handlers;

import org.springframework.http.HttpStatus;

public class AbstractValidationExceptionHandler<T extends Throwable> extends AbstractExceptionHandler<T> {

    protected AbstractValidationExceptionHandler(String exceptionName) {
        super(exceptionName);
    }

    protected HttpStatus getStatus(T error) {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }

    public String getMessage(T ex) {
        return "";
    }
}
