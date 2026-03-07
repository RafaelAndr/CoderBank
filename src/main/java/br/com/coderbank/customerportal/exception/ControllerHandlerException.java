package br.com.coderbank.customerportal.exception;

import br.com.coderbank.customerportal.dto.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerHandlerException {

    @ExceptionHandler({ClientAlreadyExistsException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleClientAlreadyExists(ClientAlreadyExistsException e){
        return new ErrorResponseDto(e.getMessage());
    }

    @ExceptionHandler({DuplicatedEmailException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleDuplicatedEmailException(DuplicatedEmailException e){
        return new ErrorResponseDto(e.getMessage());
    }
}
