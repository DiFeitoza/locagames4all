package ufc.pds.locagames4all.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ExcecaoPadrao> operacaoNaoSuportada(UnsupportedOperationException e){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        return ResponseEntity.status(status).body(geraExcecao(e,status.value()));
    }

    private ExcecaoPadrao geraExcecao(Exception e, Integer codigo){
        return new ExcecaoPadrao("Regra de Negocio não atendida.", e.getMessage(),codigo);
    }
}
