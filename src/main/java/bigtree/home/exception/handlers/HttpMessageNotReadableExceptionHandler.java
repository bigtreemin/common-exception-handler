package bigtree.home.exception.handlers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class HttpMessageNotReadableExceptionHandler
        extends AbstractBadRequestExceptionHandler<HttpMessageNotReadableException> {

    public HttpMessageNotReadableExceptionHandler() {
        super(HttpMessageNotReadableException.class.getSimpleName());
        log.info("HttpMessageNotReadableExceptionHandler created ..");
    }

    @Override
    protected String getMessage(HttpMessageNotReadableException error) {
        return "Malfomed JSON Object";
    }

    @Override
    public HttpStatus getStatus(HttpMessageNotReadableException ex) {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }
}
