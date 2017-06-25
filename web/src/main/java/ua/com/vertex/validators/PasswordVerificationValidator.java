package ua.com.vertex.validators;


import ua.com.vertex.beans.UserFormRegistration;
import ua.com.vertex.validators.interfaces.PasswordVerification;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordVerificationValidator implements ConstraintValidator<PasswordVerification, UserFormRegistration> {

    @Override
    public void initialize(PasswordVerification constraintAnnotation) {

    }

    @Override
    public boolean isValid(UserFormRegistration userFormRegistration, ConstraintValidatorContext context) {
        return userFormRegistration.getPassword().equals(userFormRegistration.getVerifyPassword());
    }
}
