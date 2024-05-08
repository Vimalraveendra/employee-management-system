package com.emsbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends RuntimeException{

    private final static String MESSAGE = "Employee with the given id is not found : %s";
    public EmployeeNotFoundException(Long employeeId){
        super(String.format(MESSAGE,employeeId));
    }
}
