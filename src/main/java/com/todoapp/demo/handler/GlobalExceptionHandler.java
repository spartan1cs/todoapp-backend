package com.todoapp.demo.handler;

    import com.todoapp.demo.exception.TodoNotFoundException;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.MethodArgumentNotValidException;
    import org.springframework.web.bind.annotation.ControllerAdvice;
    import org.springframework.web.bind.annotation.ExceptionHandler;

    import java.time.LocalDateTime;
    import java.util.HashMap;
    import java.util.Map;

    @ControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(TodoNotFoundException.class)
        public ResponseEntity<Map<String, Object>> handleTodoNotFoundException(TodoNotFoundException ex) {
            Map<String, Object> body = new HashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("status", HttpStatus.NOT_FOUND.value());
            body.put("error", "Not Found");
            body.put("message", ex.getMessage());
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }



        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
            Map<String, Object> body = new HashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("status", HttpStatus.BAD_REQUEST.value());
            body.put("error", "Validation Failed");
            body.put("message", ex.getBindingResult().getFieldError().getDefaultMessage());
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }

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