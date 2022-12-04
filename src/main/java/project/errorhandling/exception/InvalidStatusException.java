package project.errorhandling.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InvalidStatusException extends RuntimeException {

    public InvalidStatusException() {
        super("Invalid Status. Only subjects in status created can change ");
    }
}
