package br.com.devdojo.demo.handler;

import br.com.devdojo.demo.error.ResourceNotFoundException;
import br.com.devdojo.demo.error.ResourseNotFoundDetails;
import br.com.devdojo.demo.error.ValidationErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

/*public class RestExceptionHandler extends ResponseEntityExceptionHandler {*/

public class RestExceptionHandler{
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException manvExcepetion){

        List<FieldError> filderErrors = manvExcepetion.getBindingResult().getFieldErrors();
        String  fields = filderErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String  fieldMessages = filderErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        ValidationErrorDetails manvDetails = ValidationErrorDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Field validation error")
                .detail(manvExcepetion.getMessage())
                .developerMessage(manvExcepetion.getClass().getName())
                .field(fields)
                .fieldMessage(fieldMessages)
                .build();

        return new ResponseEntity<>(manvDetails, HttpStatus.BAD_REQUEST);
    }

}
