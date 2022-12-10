package project.errorhandling.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SubjectNotFoundException extends RuntimeException {
    public SubjectNotFoundException(long id) {
        super(String.format("Subject with id %s not found", id));
    }
    public SubjectNotFoundException() {
        super("Subject  not found. Please check your subjects!");
    }

}
