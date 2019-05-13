package bigtree.home.exception.handlers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import bigtree.home.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class ObjectNotFoundExceptionHandler extends AbstractExceptionHandler<ObjectNotFoundException> {

    public ObjectNotFoundExceptionHandler() {
        super(ObjectNotFoundException.class.getSimpleName());
        log.info("ObjectNotFoundExceptionHandler created ..");
    }

    @Override
    public HttpStatus getStatus(ObjectNotFoundException ex) {
        return HttpStatus.NOT_FOUND;
    }
}
