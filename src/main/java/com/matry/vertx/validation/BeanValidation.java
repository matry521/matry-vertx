package com.matry.vertx.validation;

import org.apache.bval.jsr303.ApacheValidationProvider;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;

public class BeanValidation {
    private static Validator validator;

    static {
        ValidatorFactory factory = Validation.byProvider(ApacheValidationProvider.class).configure().buildValidatorFactory();
        validator = factory.getValidator();
    }

    public static <T> String validate(T t) {
        Set<ConstraintViolation<T>> violations = validator.validate(t);

        if (null == violations || violations.size() == 0) {
            return null;
        } else {
            Iterator<ConstraintViolation<T>> iterators = violations.iterator();
            ConstraintViolation<T> constraintViolation = iterators.next();
            return constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage();
        }
    }
}
