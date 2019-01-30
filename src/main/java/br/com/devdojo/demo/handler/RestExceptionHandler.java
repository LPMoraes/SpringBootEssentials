package br.com.devdojo.demo.handler;

import br.com.devdojo.demo.error.ResourceNotFoundException;
import br.com.devdojo.demo.error.ResourseNotFoundDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

/*
*  Todas as vezes que a exceção 'ResourceNotFoundException' for lançada, por causa do '@ControllerAdvice'
*  o próprio Spring vai se encarregar de ativar o '@ExceptionHandler(ResourceNotFoundException.class)',
*  chamando assim o método 'handleResourceNotFoundException' para montar exceção conforme descrito na classe
*  'ResourseNotFoundDetails'.
*
*   OBS1: @ControllerAdvice => Transforma esta classe em um Bean
*
* */

@ControllerAdvice
public class RestExceptionHandler {
    //Recebe um rnfExcepetion
    //Retorna um rnfDetails
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfExcepetion){
        ResourseNotFoundDetails rnfDetails = ResourseNotFoundDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Resource not found")
                .detail(rnfExcepetion.getMessage())
                .developerMessage(rnfExcepetion.getClass().getName())
                .build();

        return new ResponseEntity<>(rnfDetails, HttpStatus.NOT_FOUND);
    }
}
