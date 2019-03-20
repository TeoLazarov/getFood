package teodorlazarov.getfood.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import teodorlazarov.getfood.domain.models.UserServiceModel;

public interface UserService extends UserDetailsService {

    UserServiceModel register(UserServiceModel userServiceModel);
}
