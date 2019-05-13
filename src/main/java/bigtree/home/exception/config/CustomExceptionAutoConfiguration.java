package bigtree.home.exception.config;

import java.util.List;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import bigtree.home.exception.handlers.AbstractExceptionHandler;
import bigtree.home.exception.util.ErrorMessageConverter;
import bigtree.home.exception.util.ErrorResponseComposer;

@Configuration
@AutoConfigureBefore({ ValidationAutoConfiguration.class })
@ComponentScan(basePackageClasses = { AbstractExceptionHandler.class })
public class CustomExceptionAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean({ ErrorResponseComposer.class })
    public <T extends Throwable> ErrorResponseComposer<T> errorResponseComposer(
            List<AbstractExceptionHandler<T>> handlers) {
        return new ErrorResponseComposer<T>(handlers);
    }

    // public ErrorMessageConverter errorMsgUtil
    @Bean
    public ErrorMessageConverter eventMetdataConverter(MessageSource messageSource,
            LocalValidatorFactoryBean validator) {
        return new ErrorMessageConverter(messageSource, validator);
    }
}
