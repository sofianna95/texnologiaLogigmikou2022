package project.errorhandling.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InsufficientRoleException extends RuntimeException {

    public InsufficientRoleException() {
        super("Insufficient Role privileges");
    }
}
