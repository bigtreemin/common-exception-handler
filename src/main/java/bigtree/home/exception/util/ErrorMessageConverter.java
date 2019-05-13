package bigtree.home.exception.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorMessageConverter {

    private final MessageSource messageSource;
    private final LocalValidatorFactoryBean validator;

    public LocalValidatorFactoryBean getValidator() {
        return this.validator;
    }

    public ErrorMessageConverter(MessageSource messageSource, LocalValidatorFactoryBean validator) {
        this.messageSource = messageSource;
        this.validator = validator;
    }

    public String getMessage(String messageKey, Object... args) {
        log.debug("Received request for messageKey:" + messageKey);
        if (messageSource == null) {
            return "Application unavailable probably unit test going on";
        }
        return messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
    }
}
