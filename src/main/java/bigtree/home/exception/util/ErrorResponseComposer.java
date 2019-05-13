package bigtree.home.exception.util;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import bigtree.home.exception.bo.APIErrorResponse;
import bigtree.home.exception.handlers.AbstractExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorResponseComposer<T extends Throwable> extends Object {

    private final Map<String, AbstractExceptionHandler<T>> handlers;

    public ErrorResponseComposer(List<AbstractExceptionHandler<T>> handlers) {
        this.handlers = handlers.stream().collect(Collectors.toMap(AbstractExceptionHandler::getExceptionName,
                Function.identity(),
                (handler1, handler2) -> (AnnotationAwareOrderComparator.INSTANCE.compare(handler1, handler2) < 0)
                        ? handler1
                        : handler2));

        log.info("ErrorResponseComposer Created....");
    }

    @SuppressWarnings("unchecked")
    public Optional<APIErrorResponse> compose(T ex) {
        AbstractExceptionHandler<T> handler = null;
        while (ex != null) {
            handler = handlers.get(ex.getClass().getSimpleName());
            if (handler != null) {
                break;
            }
            ex = (T) ex.getCause();
        }
        if (handler != null) {
            return Optional.of(handler.getAPIErrorResponse(ex));
        }
        return Optional.empty();
    }
}
