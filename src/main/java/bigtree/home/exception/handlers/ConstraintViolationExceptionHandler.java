package bigtree.home.exception.handlers;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import bigtree.home.exception.bo.APIFieldError;
import bigtree.home.exception.util.ErrorMessageConverter;
import lombok.extern.slf4j.Slf4j;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class ConstraintViolationExceptionHandler<E extends ConstraintViolationException>
        extends AbstractExceptionHandler<E> {

    @Autowired
    private ErrorMessageConverter errorMessageConverter;

    public ConstraintViolationExceptionHandler() {
        super(ConstraintViolationException.class.getSimpleName());
        log.info("Create ConstraintViolationExceptionHandler .. ");
    }

    public ConstraintViolationExceptionHandler(String exceptionName) {
        super(exceptionName);
    }

    @Override
    public HttpStatus getStatus(E ex) {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }

    @Override
    public Collection<APIFieldError> getFieldErrors(E ex) {
        return APIFieldError.getErrors(ex.getConstraintViolations());
    }

    @Override
    public String getMessage(E e) {
        return this.errorMessageConverter.getMessage("bigtree.home.exception.handlers.validationError", new Object[0]);
    }
}
