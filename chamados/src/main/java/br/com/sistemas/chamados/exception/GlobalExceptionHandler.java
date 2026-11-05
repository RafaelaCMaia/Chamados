
package br.com.sistemas.chamados.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResposta> recursoNaoEncontrado(
            RecursoNaoEncontradoException ex) {

        return criarResposta(
            HttpStatus.NOT_FOUND,
            ex.getMessage()
        );
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroResposta> regraNegocio(
            RegraNegocioException ex) {

        return criarResposta(
            HttpStatus.BAD_REQUEST,
            ex.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> validacao(
            MethodArgumentNotValidException ex) {

        String mensagem = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(erro -> erro.getDefaultMessage())
            .findFirst()
            .orElse("Dados inválidos");

        return criarResposta(
            HttpStatus.BAD_REQUEST,
            mensagem
        );
    }

    private ResponseEntity<ErroResposta> criarResposta(
            HttpStatus status,
            String mensagem) {

        ErroResposta erro = new ErroResposta(
            LocalDateTime.now(),
            status.value(),
            status.getReasonPhrase(),
            mensagem
        );

        return ResponseEntity.status(status).body(erro);
    }
}
