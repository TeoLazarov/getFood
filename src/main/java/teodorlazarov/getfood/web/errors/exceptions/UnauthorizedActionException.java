package teodorlazarov.getfood.web.errors.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static teodorlazarov.getfood.constants.Errors.UNAUTHORIZED;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = UNAUTHORIZED)
public class UnauthorizedActionException extends RuntimeException {

    public UnauthorizedActionException(String message) {
        super(message);
    }
}
