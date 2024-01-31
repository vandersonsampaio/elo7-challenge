package br.com.elo7.sonda.candidato.config;

import br.com.elo7.sonda.candidato.model.expection.EntityNotFoundException;
import br.com.elo7.sonda.candidato.model.expection.ErrorResponse;
import br.com.elo7.sonda.candidato.model.expection.MovementNotPermittedException;
import br.com.elo7.sonda.candidato.model.expection.ResizeMapException;
import br.com.elo7.sonda.candidato.model.expection.SimpleErrorResponse;
import br.com.elo7.sonda.candidato.model.expection.SpaceNotAvailableException;
import br.com.elo7.sonda.candidato.model.expection.UniqueEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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

    private ResponseEntity<Object> buildResponseEntity(ErrorResponse apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
