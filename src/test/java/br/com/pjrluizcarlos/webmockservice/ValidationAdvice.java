package br.com.pjrluizcarlos.webmockservice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.executable.ExecutableValidator;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.lang.reflect.Method;
import java.util.Set;

public class ValidationAdvice implements MethodBeforeAdvice {

    private static final ExecutableValidator VALIDATOR;

    static {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.afterPropertiesSet();
        VALIDATOR = factory.getValidator().forExecutables();
        factory.close();
    }

    @Override
    public void before(Method method, Object[] args, Object target) {
        Set<ConstraintViolation<Object>> violations = VALIDATOR.validateParameters(target, method, args);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

}