package teodorlazarov.getfood.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import teodorlazarov.getfood.domain.entities.ShoppingCart;
import teodorlazarov.getfood.domain.entities.User;
import teodorlazarov.getfood.domain.entities.UserRole;
import teodorlazarov.getfood.domain.models.service.UserRoleServiceModel;
import teodorlazarov.getfood.domain.models.service.UserServiceModel;
import teodorlazarov.getfood.repository.UserRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static teodorlazarov.getfood.constants.GlobalConstants.*;

@Service
public class UserServiceImpl implements UserService {

    private static final String USERNAME_NOT_FOUND_EXCEPTION = "Username not found.";
    private static final String WRONG_PASSWORD_EXCEPTION = "Incorrect password!";
    private static final String CANNOT_MODIFY_ROLE_EXCEPTION = "Role cannot be modified";

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final ShoppingCartService shoppingCartService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService, ShoppingCartService shoppingCartService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.shoppingCartService = shoppingCartService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        user.setRoles(getRolesForRegistration());
        user.setRegisteredOn(LocalDate.now());
        user.setShoppingCart(this.modelMapper.map(this.shoppingCartService.createShoppingCart(), ShoppingCart.class));

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND_EXCEPTION));
    }

    private Set<UserRole> getRolesForRegistration() {
        Set<UserRole> roles = new HashSet<>();
        if (this.userRepository.findAll().isEmpty()) {
            roles.add(this.modelMapper.map(this.userRoleService.getRoleByRoleName(USER_ROLE_ROOT), UserRole.class));
            roles.add(this.modelMapper.map(this.userRoleService.getRoleByRoleName(USER_ROLE_ADMIN), UserRole.class));
            roles.add(this.modelMapper.map(this.userRoleService.getRoleByRoleName(USER_ROLE_EMPLOYEE), UserRole.class));
            roles.add(this.modelMapper.map(this.userRoleService.getRoleByRoleName(USER_ROLE_USER), UserRole.class));
        } else {
            roles.add(this.modelMapper.map(this.userRoleService.getRoleByRoleName(USER_ROLE_USER), UserRole.class));
        }

        return roles;
    }

    @Override
    public List<UserServiceModel> findAllUsers() {
        return this.userRepository
                .findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserServiceModel editProfile(UserServiceModel model, String oldPassword) {
        User user = this.userRepository.findUserByUsername(model.getUsername()).orElseThrow(() -> new IllegalArgumentException(USERNAME_NOT_FOUND_EXCEPTION));

        if (!this.bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException(WRONG_PASSWORD_EXCEPTION);
        }

        user.setPassword(!"".equals(model.getPassword()) ? this.bCryptPasswordEncoder.encode(model.getPassword()) : user.getPassword());
        user.setEmail(model.getEmail());
        user.setFullName(model.getFullName());
        user.setPhoneNumber(model.getPhoneNumber());

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        return this.modelMapper.map(this.userRepository.findUserByUsername(username).orElseThrow(() -> new IllegalArgumentException(USERNAME_NOT_FOUND_EXCEPTION)), UserServiceModel.class);
    }

    @Override
    public void changeUserRole(String username, String roleName) {
        if (USER_ROLE_ROOT.equals(roleName)){
            throw new IllegalArgumentException(CANNOT_MODIFY_ROLE_EXCEPTION);
        }

        UserServiceModel user = this.findUserByUsername(username);
        UserRoleServiceModel role = this.userRoleService.getRoleByRoleName(roleName);
        boolean userHasRole = false;
        boolean userHasEmployeeRole = false;
        List<UserRoleServiceModel> rolesToBeRemoved = new LinkedList<>();

        if (USER_ROLE_USER.equals(role.getRole())) {
            for (UserRoleServiceModel userRole : user.getRoles()) {
                if (!USER_ROLE_USER.equals(userRole.getRole())) {
                    rolesToBeRemoved.add(userRole);
                }
            }

            user.getRoles().removeAll(rolesToBeRemoved);
        } else {
            for (UserRoleServiceModel userRole : user.getRoles()) {
                if (userRole.getRole().equals(role.getRole())) {
                    userHasRole = true;
                }
            }

            if (userHasRole) {
                for (UserRoleServiceModel userRole : user.getRoles()) {
                    if (userRole.getRole().equals(role.getRole())) {
                        user.getRoles().remove(userRole);
                    }
                }
            } else if (USER_ROLE_ADMIN.equals(role.getRole())) {
                for (UserRoleServiceModel userRole : user.getRoles()) {
                    if (USER_ROLE_EMPLOYEE.equals(userRole.getRole())) {
                        userHasEmployeeRole = true;
                    }
                }

                if (!userHasEmployeeRole) {
                    UserRoleServiceModel employee = this.userRoleService.getRoleByRoleName(USER_ROLE_EMPLOYEE);

                    user.getRoles().add(employee);
                }

                user.getRoles().add(role);
            } else {
                user.getRoles().add(role);
            }
        }

        this.userRepository.save(this.modelMapper.map(user, User.class));
    }
}
