package com.carfax

import org.springframework.validation.FieldError

class ErrorResponse {
	List<FieldError> errors = new ArrayList<FieldError>();
}
