package teodorlazarov.getfood.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import teodorlazarov.getfood.domain.models.service.UserServiceModel;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserServiceModel register(UserServiceModel userServiceModel);

    List<UserServiceModel> findAllUsers();

    UserServiceModel findUserByUsername(String username);

    UserServiceModel editProfile(UserServiceModel model, String oldPassword);

    void changeUserRole(String username, String roleName);
}
