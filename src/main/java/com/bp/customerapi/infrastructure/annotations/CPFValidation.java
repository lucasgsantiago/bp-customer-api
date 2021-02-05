package com.bp.customerapi.infrastructure.annotations;

import com.bp.customerapi.domain.customer.CpfValidatorService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CPFValidation implements ConstraintValidator<ValidCPF, String> {

      @Override
      public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
         return CpfValidatorService.isValid(field);
      }

}
