package com.example.demo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Curatore;
import com.example.demo.service.CuratoreService;


@Component
public class CuratoreValidator implements Validator {

	@Autowired
	CuratoreService curatoreService;
	
	@Override
	public void validate(Object o, Errors errors) {
		if (this.curatoreService.getById(((Curatore)o).getMatricola())!=null) {
			errors.reject("duplicatoCuratore");
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Curatore.class.equals(aClass);
	}
}
