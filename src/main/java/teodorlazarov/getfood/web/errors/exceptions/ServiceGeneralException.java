package teodorlazarov.getfood.web.errors.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static teodorlazarov.getfood.constants.Errors.GENERAL_SERVICE_ERROR;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = GENERAL_SERVICE_ERROR)
public class ServiceGeneralException extends RuntimeException {

    public ServiceGeneralException(String message) {
        super(message);
    }
}
