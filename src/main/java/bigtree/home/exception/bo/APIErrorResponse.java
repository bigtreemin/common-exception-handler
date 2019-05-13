package bigtree.home.exception.bo;import java.time.LocalDateTime;import java.util.Collection;import org.springframework.http.HttpStatus;import com.fasterxml.jackson.annotation.JsonAutoDetect;import com.fasterxml.jackson.annotation.JsonFormat;import com.fasterxml.jackson.annotation.JsonIgnoreProperties;import com.fasterxml.jackson.annotation.JsonProperty;import com.fasterxml.jackson.databind.annotation.JsonDeserialize;import com.fasterxml.jackson.databind.annotation.JsonSerialize;import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;import lombok.AllArgsConstructor;import lombok.Builder;import lombok.Getter;import lombok.NoArgsConstructor;@Getter@AllArgsConstructor@NoArgsConstructor@Builder@JsonAutoDetect@JsonIgnoreProperties(ignoreUnknown = true)public class APIErrorResponse {    public enum ErrorCategory {        TECHNICAL, BUSINESS;    }    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")    @JsonDeserialize(using = LocalDateTimeDeserializer.class)    @JsonSerialize(using = LocalDateTimeSerializer.class)    @JsonProperty("localTime")    private LocalDateTime localTime;    @JsonProperty("status")    private HttpStatus status;    @JsonProperty("statusCode")    private int statusCode;    @JsonProperty("errorCode")    private int errorCode;    @JsonProperty("errorCategory")    private ErrorCategory errorCategory;    @JsonProperty("exceptionType")    private String exceptionType;    @JsonProperty("errorMessage")    private String errorMessage;    @JsonProperty("debugMessage")    private String debugMessage;    @JsonProperty("errorDetails")    private Collection<APIFieldError> errorDetails;    public boolean inComplete() {        return errorMessage == null || status == null;    }    public void updateException(String exception) {        this.exceptionType = exception;    }}