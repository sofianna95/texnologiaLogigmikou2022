package project.errorhandling.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NewsTitleAlreadyExistsException extends RuntimeException {

    public NewsTitleAlreadyExistsException(String title) {
        super(String.format("Title %s already exists", title));
    }

}
