package bigtree.home.exception.handlers;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import bigtree.home.exception.bo.APIErrorResponse;
import bigtree.home.exception.util.ErrorResponseComposer;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@ConditionalOnMissingBean({ ResponseEntityExceptionHandler.class })
@Slf4j
public class CustomExceptionHandlerControllerAdvice<T extends Throwable> extends Object {

    private ErrorResponseComposer<T> errorResponseComposer;

    public CustomExceptionHandlerControllerAdvice(ErrorResponseComposer<T> errorReponseComoser) {
        this.errorResponseComposer = errorReponseComoser;
        log.info("DefaultExceptionHandlerControllerAdvice Created ... ");
    }

    @RequestMapping(produces = { "application/json" })
    @ExceptionHandler({ Throwable.class })
    public ResponseEntity<?> handleException(T ex) throws T {
        APIErrorResponse errorResponse = (APIErrorResponse) this.errorResponseComposer.compose(ex)
                .orElseThrow(() -> ex);
        if (errorResponse.inComplete()) {
            throw ex;
        }
        log.warn("handling exception", ex);
        errorResponse.updateException(ex.getClass().getSimpleName());
        return new ResponseEntity<APIErrorResponse>(errorResponse, HttpStatus.valueOf(errorResponse.getStatusCode()));
    }
}
