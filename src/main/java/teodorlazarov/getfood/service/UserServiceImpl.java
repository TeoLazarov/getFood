package teodorlazarov.getfood.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import teodorlazarov.getfood.domain.entities.User;
import teodorlazarov.getfood.domain.entities.UserRole;
import teodorlazarov.getfood.domain.models.UserServiceModel;
import teodorlazarov.getfood.repository.UserRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        user.setRoles(getRolesForRegistration());
        user.setRegisteredOn(LocalDate.now());

        return this.modelMapper.map(this.userRepository.save(user), UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
    }

    private Set<UserRole> getRolesForRegistration(){
        Set<UserRole> roles = new HashSet<>();
        if (this.userRepository.findAll().isEmpty()){
            roles.add(this.modelMapper.map(this.userRoleService.getRoleByRoleName("ADMIN"),UserRole.class));
        } else {
            roles.add(this.modelMapper.map(this.userRoleService.getRoleByRoleName("USER"),UserRole.class));
        }

        return roles;
    }
}
