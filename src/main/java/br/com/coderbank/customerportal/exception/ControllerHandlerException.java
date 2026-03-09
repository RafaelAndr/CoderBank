package br.com.coderbank.customerportal.exception;

import br.com.coderbank.customerportal.dto.response.ErrorResponseDto;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerHandlerException {

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ProblemDetail handleMethodArgumentNotValidExcepition(MethodArgumentNotValidException e){
//
//        final var validationErrors = buildHashMapValidationErrors(e);
//
//        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, validationErrors.toString());
//
//        problemDetail.setTitle("Data sent in the request is invalid");
//        problemDetail.setType(URI.create("http://www.siteexemplo.com.br"));
//        problemDetail.setProperty("errors", validationErrors);
//
//        return problemDetail;
//    }
//
//    private static HashMap<Object, Object> buildHashMapValidationErrors(MethodArgumentNotValidException e) {
//        final var errors = new HashMap<>();
//
//        e.getBindingResult()
//                .getAllErrors()
//                .forEach((error) -> {
//
//                    var fieldName = ((FieldError) error).getField();
//
//                    var errorMessage = error.getDefaultMessage();
//
//                    errors.put(fieldName, errorMessage);
//                });
//        return errors;
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleValidation(MethodArgumentNotValidException e){

        Map<String, String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage
                ));

        var problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Validation failed"
        );

        problemDetail.setTitle("Invalid request data");
        problemDetail.setProperty("errors", errors);

        return problemDetail;
    }

    @ExceptionHandler({ClientAlreadyExistsException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ProblemDetail handleClientAlreadyExists(ClientAlreadyExistsException e){

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());

        problemDetail.setTitle("Conflict: Existing cpf");
        problemDetail.setType(URI.create("http://www.siteexemplo.com.br"));

        return problemDetail;
    }

    @ExceptionHandler({DuplicatedEmailException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ProblemDetail handleDuplicatedEmailException(DuplicatedEmailException e){

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());

        problemDetail.setTitle("Conflict: Existing email");
        problemDetail.setType(URI.create("http://www.siteexemplo.com.br"));

        return problemDetail;
    }

}
