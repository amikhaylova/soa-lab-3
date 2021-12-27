package service.oriented.architecture.lab2_refactored.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import service.oriented.architecture.lab2_refactored.dto.ExceptionDTO;
import service.oriented.architecture.lab2_refactored.exceptions.EntityIsNotValidException;

import javax.persistence.NoResultException;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(value = {ConstraintViolationException.class, EntityIsNotValidException.class, NumberFormatException.class})
    public ResponseEntity badRequestException(RuntimeException e) {
        System.out.println(e.getMessage());
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_XML)
                .body(exceptionDTO);
    }

    @ExceptionHandler(value = {NoResultException.class})
    public ResponseEntity notFoundException(RuntimeException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_XML)
                .body(exceptionDTO);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity serverException(RuntimeException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Произошла ошибка на сервере.");
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_XML)
                .body(exceptionDTO);
    }


}
