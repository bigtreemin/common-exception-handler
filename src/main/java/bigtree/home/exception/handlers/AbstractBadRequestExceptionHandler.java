package bigtree.home.exception.handlers;

import org.springframework.http.HttpStatus;

public class AbstractBadRequestExceptionHandler<T extends Throwable> extends AbstractExceptionHandler<T> {

    protected AbstractBadRequestExceptionHandler(String exceptionName) {
        super(exceptionName);
    }

    @Override
    protected HttpStatus getStatus(T error) {
        return HttpStatus.BAD_REQUEST;
    }
}
