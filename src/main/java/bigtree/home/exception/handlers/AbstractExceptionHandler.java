package bigtree.home.exception.handlers;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import org.springframework.http.HttpStatus;

import bigtree.home.exception.bo.APIErrorResponse;
import bigtree.home.exception.bo.APIFieldError;
import lombok.Getter;

public abstract class AbstractExceptionHandler<T extends Throwable> extends Object {
    @Getter
    private final String exceptionName;
    private final APIErrorResponse.ErrorCategory errorCategory;


    protected AbstractExceptionHandler(String exceptionName) {
        this(APIErrorResponse.ErrorCategory.TECHNICAL, exceptionName);
    }

    protected AbstractExceptionHandler(APIErrorResponse.ErrorCategory errorCategory, String exceptionName) {
        this.exceptionName = exceptionName;
        this.errorCategory = errorCategory;
    }

    protected String getMessage(T error) {
        return (error.getMessage() == null) ? error.getClass().getSimpleName() : error.getMessage();
    }

    protected int getErrorCode(T error) {
        return 0;
    }

    protected Collection<APIFieldError> getFieldErrors(T error) {
        return Collections.emptyList();
    }

    public APIErrorResponse getAPIErrorResponse(T error) {
        HttpStatus httpStatus = getStatus(error);

        return APIErrorResponse.builder()
                .errorMessage(getMessage(error))
                .errorCategory(errorCategory)
                .errorCode(getErrorCode(error))
                .status(httpStatus)
                .exceptionType(exceptionName)
                .debugMessage((error.getMessage() == null) ? error.getClass().getSimpleName() : error.getMessage())
                .errorDetails(getFieldErrors(error))
                .localTime(LocalDateTime.now())
                .statusCode(httpStatus.value())
                .build();
    }

    protected abstract HttpStatus getStatus(T paramT);
}
