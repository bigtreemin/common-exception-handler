package bigtree.home.exception.bo;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.support.WebExchangeBindException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class APIFieldError {
    private String field;
    private String object;
    private Object rejectedValue;
    private String message;

    APIFieldError(String pObject, String pMessage) {
        this.object = pObject;
        this.message = pMessage;
    }

    APIFieldError(String pObject, String field, String pMessage) {
        this.object = pObject;
        this.field = field;
        this.message = pMessage;
    }

    public static List<APIFieldError> getErrors(Set<ConstraintViolation<?>> constraintViolations) {
        return constraintViolations.stream().map(APIFieldError::of).collect(Collectors.toList());
    }

    private static APIFieldError of(ConstraintViolation<?> constraintViolations) {
        String field = StringUtils.substringAfter(constraintViolations.getPropertyPath().toString(), ".");
        return new APIFieldError(constraintViolations.getMessageTemplate(), field, constraintViolations.getMessage());
    }

    public static List<APIFieldError> getErrors(WebExchangeBindException ex) {
        List<APIFieldError> errorDetails = ex.getFieldErrors().stream().map(APIFieldError::addFieldError)
                .collect(Collectors.toList());
        errorDetails
                .addAll(ex.getGlobalErrors().stream().map(APIFieldError::addObjectError).collect(Collectors.toSet()));
        return errorDetails;
    }

    public static List<APIFieldError> getErrors(List<FieldError> fieldErrors, List<ObjectError> globalErrors) {
        List<APIFieldError> errorDetails = fieldErrors.stream().map(APIFieldError::addFieldError)
                .collect(Collectors.toList());
        errorDetails.addAll(globalErrors.stream().map(APIFieldError::addObjectError).collect(Collectors.toSet()));
        return errorDetails;
    }

    private static APIFieldError addFieldError(FieldError fieldError) {
        return new APIFieldError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    private static APIFieldError addObjectError(ObjectError objectError) {
        return new APIFieldError(objectError.getObjectName(), objectError.getDefaultMessage());
    }
}
