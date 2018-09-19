package exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TaskNotExistException extends RuntimeException {

    public TaskNotExistException(String message) {
        super(message);
    }

}
