package br.beanascigom.testesoftplan.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage() != null ? ex.getMessage() : "Recurso nao encontrado",
                request.getRequestURI(), null);
    }

    @ExceptionHandler(ResourceNoContentException.class)
    public ResponseEntity<Object> handleResourceNoContentException(ResourceNoContentException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NO_CONTENT, ex.getMessage() != null ? ex.getMessage() : "Não há registro para retornar",
                request.getRequestURI(), null);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        Map<String, String> violacoes = new LinkedHashMap<>();
        ex.getConstraintViolations().forEach(violation -> violacoes.put(
                violation.getPropertyPath().toString(),
                violation.getMessage()
        ));

        return buildResponse(HttpStatus.BAD_REQUEST, "Falha de validacao nos parametros da requisicao",
                request.getRequestURI(), violacoes);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, "Violacao de integridade de dados", request.getRequestURI(),
                null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpectedException(Exception ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno inesperado",
                request.getRequestURI(), null);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        Map<String, String> erros = new LinkedHashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            erros.put(error.getField(), error.getDefaultMessage());
        }

        return buildResponse(HttpStatus.BAD_REQUEST, "Falha de validacao no corpo da requisicao", extractPath(request),
                erros);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        return buildResponse(HttpStatus.BAD_REQUEST, "Corpo da requisicao invalido ou malformado",
                extractPath(request), null);
    }

    private ResponseEntity<Object> buildResponse(HttpStatus status, String mensagem, String caminho, Map<String, String> erros) {
        ErrorResponse body = new ErrorResponse(OffsetDateTime.now(), status.value(), status.getReasonPhrase(),
                mensagem, caminho, erros);
        return ResponseEntity.status(status).body(body);
    }

    private String extractPath(WebRequest request) {
        String descricao = request.getDescription(false);
        return descricao != null ? descricao.replace("uri=", "") : null;
    }

    private record ErrorResponse(OffsetDateTime timestamp, int status, String erro,
            String mensagem, String caminho, Map<String, String> erros) {}
}
