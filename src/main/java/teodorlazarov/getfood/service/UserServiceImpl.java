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
import teodorlazarov.getfood.domain.models.service.UserServiceModel;
import teodorlazarov.getfood.repository.UserRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

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
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
    }

    private Set<UserRole> getRolesForRegistration(){
        Set<UserRole> roles = new HashSet<>();
        if (this.userRepository.findAll().isEmpty()){
            roles.add(this.modelMapper.map(this.userRoleService.getRoleByRoleName("ROLE_ROOT"),UserRole.class));
            roles.add(this.modelMapper.map(this.userRoleService.getRoleByRoleName("ROLE_ADMIN"),UserRole.class));
            roles.add(this.modelMapper.map(this.userRoleService.getRoleByRoleName("ROLE_EMPLOYEE"),UserRole.class));
            roles.add(this.modelMapper.map(this.userRoleService.getRoleByRoleName("ROLE_USER"),UserRole.class));
        } else {
            roles.add(this.modelMapper.map(this.userRoleService.getRoleByRoleName("ROLE_USER"),UserRole.class));
        }

        return roles;
    }

    @Override
    public List<UserServiceModel> findAllUsers(){
        return this.userRepository
                .findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }
}
