package ci.trabrouss.saas.exceptions;

import ci.trabrouss.saas.exceptions.responses.ErreurResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(value = BusinessException.class)
  public ResponseEntity<ErreurResponse> handleException(
    final BusinessException ex,
    final HttpServletRequest request
    ){
     log.error("Entity not found", ex);

     ErreurResponse erreurResponse = ErreurResponse.builder()
       .message(ex.getMessage())
       .path(request.getRequestURI())
       .build();

     final HttpStatus httpStatus = getHttpStatus(ex);

     return ResponseEntity.status(httpStatus).body(erreurResponse);
  }

  @ExceptionHandler(value = BadCredentialsException.class)
  public ResponseEntity<ErreurResponse> handleException(
    final BadCredentialsException ex,
    final HttpServletRequest request
  ) {

    final ErreurResponse errorResponse = ErreurResponse.builder()
      .message("Login and / or password are incorrect.")
      .path(request.getRequestURI())
      .build();

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
      .body(errorResponse);
  }

  private HttpStatus getHttpStatus(BusinessException ex) {
    if(ex instanceof DuplicateResourceException){
      return HttpStatus.CONFLICT;
    } else if (ex instanceof UnauthorizedException) {
      return HttpStatus.UNAUTHORIZED;
      
    }
    return HttpStatus.BAD_REQUEST;
  }


  @ExceptionHandler(value = {EntityNotFoundException.class, UsernameNotFoundException.class})
  public ResponseEntity<ErreurResponse> handleException(
    final EntityNotFoundException ex,
    final HttpServletRequest request
    ){
     log.error("Entity not found");

     ErreurResponse erreurResponse = ErreurResponse.builder()
       .code("NOT_FOUND")
       .message(ex.getMessage())
       .path(request.getRequestURI())
       .build();

     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erreurResponse);
  }

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<ErreurResponse> handleException(
    final MethodArgumentNotValidException ex,
    final HttpServletRequest request
    ){
     log.error("Erreur de validation");

     final List<ErreurResponse.ValidationError> errors = new ArrayList<>();
     ex.getBindingResult()
       .getAllErrors()
       .forEach(objectError -> {
         final String fieldName = ((FieldError) objectError).getField();
         final String errorCode = objectError.getDefaultMessage();
         final String defaultMessage = objectError.getDefaultMessage();


        errors.add(ErreurResponse.ValidationError.builder()
            .field(fieldName)
            .code(errorCode)
            .message(defaultMessage)
          .build());

       });


     ErreurResponse erreurResponse = ErreurResponse.builder()
       .validationErrors(errors)
       .path(request.getRequestURI())
       .build();

     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurResponse);
  }
}
