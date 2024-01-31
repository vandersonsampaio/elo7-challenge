package br.com.elo7.sonda.candidato.config;

import br.com.elo7.sonda.candidato.model.expection.EntityNotFoundException;
import br.com.elo7.sonda.candidato.model.expection.ErrorResponse;
import br.com.elo7.sonda.candidato.model.expection.MovementNotPermittedException;
import br.com.elo7.sonda.candidato.model.expection.ResizeMapException;
import br.com.elo7.sonda.candidato.model.expection.SimpleErrorResponse;
import br.com.elo7.sonda.candidato.model.expection.SpaceNotAvailableException;
import br.com.elo7.sonda.candidato.model.expection.UniqueEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleInternalServerError(Exception ex) {
        log.error(ex.getMessage(), ex);
        return buildResponseEntity(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex));
    }

    @ExceptionHandler({ UniqueEntityException.class, MovementNotPermittedException.class,
            ResizeMapException.class, SpaceNotAvailableException.class })
    protected ResponseEntity<Object> handleBadRequest(Exception ex) {
        log.error(ex.getMessage(), ex);
        return buildResponseEntity(new ErrorResponse(HttpStatus.BAD_REQUEST, ex));
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(Exception ex) {
        log.error(ex.getMessage());

        return new ResponseEntity<>(new SimpleErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage(), ex);

        String message = ex.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("\r\n"));

        return buildResponseEntity(new ErrorResponse(HttpStatus.BAD_REQUEST, message));
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorResponse apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
