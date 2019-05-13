package bigtree.home.exception.handlers;

import java.util.Collection;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import bigtree.home.exception.bo.APIFieldError;
import lombok.extern.slf4j.Slf4j;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class MethodArgumentNotValidExceptionHandler
        extends AbstractBadRequestExceptionHandler<MethodArgumentNotValidException> {

    public MethodArgumentNotValidExceptionHandler() {
        super(MethodArgumentNotValidException.class.getSimpleName());
        log.info("MethodArgumentNotValidExceptionHandler Created....");
    }

    @Override
    protected String getMessage(MethodArgumentNotValidException error) {
        return "Validation Error";
    }

    @Override
    protected Collection<APIFieldError> getFieldErrors(MethodArgumentNotValidException ex) {
        return APIFieldError.getErrors(ex.getBindingResult().getFieldErrors(), ex.getBindingResult().getGlobalErrors());
    }
}
