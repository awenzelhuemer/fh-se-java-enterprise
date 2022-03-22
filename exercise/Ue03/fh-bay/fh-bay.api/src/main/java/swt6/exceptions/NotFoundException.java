package swt6.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super(String.format("Could not find entry '%d.'", id));
    }

    public NotFoundException(String firstName, String lastName) {
        super(String.format("Could not find entry with '%s.' and '%s'.", firstName, lastName));
    }
}
