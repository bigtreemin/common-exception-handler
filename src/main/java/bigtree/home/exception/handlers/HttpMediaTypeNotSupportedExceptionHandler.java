package bigtree.home.exception.handlers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotSupportedException;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class HttpMediaTypeNotSupportedExceptionHandler
        extends AbstractBadRequestExceptionHandler<HttpMediaTypeNotSupportedException> {

    public HttpMediaTypeNotSupportedExceptionHandler() {
        super(HttpMediaTypeNotSupportedException.class.getSimpleName());
        log.info("HttpMediaTypeNotSupportedExceptionHandler created ..");
    }

    @Override
    public HttpStatus getStatus(HttpMediaTypeNotSupportedException ex) {
        return HttpStatus.UNSUPPORTED_MEDIA_TYPE;
    }

    @Override
    protected String getMessage(HttpMediaTypeNotSupportedException ex) {
        StringBuilder sb = new StringBuilder();
        sb.append(ex.getContentType() + " media type is not supported.");
        sb.append("Supported Media Types are ");
        ex.getSupportedMediaTypes().forEach(t -> sb.append(t).append(", "));
        return sb.substring(0, sb.length() - 2);
    }
}
