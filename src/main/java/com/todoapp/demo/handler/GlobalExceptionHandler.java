package com.todoapp.demo.handler;

    import com.todoapp.demo.exception.TodoNotFoundException;
    import jakarta.validation.ConstraintViolationException;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.http.converter.HttpMessageNotReadableException;
    import org.springframework.web.bind.MethodArgumentNotValidException;
    import org.springframework.web.bind.annotation.ControllerAdvice;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
    import org.springframework.web.servlet.NoHandlerFoundException;

    import java.time.LocalDateTime;
    import java.util.HashMap;
    import java.util.Map;

    @ControllerAdvice
    public class GlobalExceptionHandler {

        //TodoNotFoundException:
        // If no todo exists with the given id (handled in your service)
        // This is typically thrown when a todo is not found in the database.
        @ExceptionHandler(TodoNotFoundException.class)
        public ResponseEntity<Map<String, Object>> handleTodoNotFoundException(TodoNotFoundException ex) {
            Map<String, Object> body = new HashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("status", HttpStatus.NOT_FOUND.value());
            body.put("error", "Not Found: TodoNotFoundException");
            body.put("message", ex.getMessage());
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }

       // MethodArgumentNotValidException:
       // If the request body fails validation (e.g., missing required fields, invalid types)
        // This is typically used with @Valid on request bodies.
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
            Map<String, Object> body = new HashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("status", HttpStatus.BAD_REQUEST.value());
            body.put("error", "Argument Not Valid: MethodArgumentNotValidException");
            body.put("message", ex.getBindingResult().getFieldError().getDefaultMessage());
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }


        // Handle invalid parameter types
        // MethodArgumentTypeMismatchException:
        // If id is not a valid Long (e.g., /api/todos/abc).
        // This is typically used with @PathVariable or @RequestParam.
        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
            Map<String, Object> body = new HashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("status", HttpStatus.BAD_REQUEST.value());
            body.put("error", "Bad Request:MethodArgumentTypeMismatchException");
            body.put("message", "Invalid parameter: " + ex.getName());
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }

        // Handle malformed JSON
        // HttpMessageNotReadableException:
        // If the request body is not valid JSON (e.g., missing quotes, brackets).
        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<Map<String, Object>> handleNotReadable(HttpMessageNotReadableException ex) {
            Map<String, Object> body = new HashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("status", HttpStatus.BAD_REQUEST.value());
            body.put("error", "HttpMessageNotReadableException: Malformed JSON");
            body.put("message", ex.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }

        // Handle validation errors on path/query params
        //ConstraintViolationException:
        // If id < 1 (due to @Min(1) on the path variable).
        // we need to give @Validated annotation on the controller class
        // and @Min(1) on the path variable.
        // This will catch validation errors on method arguments.
        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
            Map<String, Object> body = new HashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("status", HttpStatus.BAD_REQUEST.value());
            body.put("error", "Constraint Violation");
            body.put("message", ex.getMessage());
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }

        // Handle 404 for unmapped endpoints
        // NoHandlerFoundException:
        // If a request is made to an endpoint that does not exist (e.g., /api/todos/999).
        // This requires setting "throw-exception-if-no-handler-found" to true in application.properties.
        // This is typically used when a request is made to an endpoint that does not exist.
        @ExceptionHandler(NoHandlerFoundException.class)
        public ResponseEntity<Map<String, Object>> handleNoHandlerFound(NoHandlerFoundException ex) {
            Map<String, Object> body = new HashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("status", HttpStatus.NOT_FOUND.value());
            body.put("error", "Not Found");
            body.put("message", "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL());
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }

        //Generic Exception: Any unexpected server error.
        @ExceptionHandler(Exception.class)
        public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {
            Map<String, Object> body = new HashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            body.put("error", "Internal Server Error");
            body.put("message", ex.getMessage());
            return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }