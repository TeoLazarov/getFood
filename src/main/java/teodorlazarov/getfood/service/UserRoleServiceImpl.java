package teodorlazarov.getfood.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teodorlazarov.getfood.domain.entities.UserRole;
import teodorlazarov.getfood.domain.models.service.UserRoleServiceModel;
import teodorlazarov.getfood.repository.UserRoleRepository;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, ModelMapper modelMapper) {
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserRoleServiceModel getRoleByRoleName(String roleName) {
        UserRole role = this.userRoleRepository.findByRole(roleName).orElseThrow(() -> new IllegalArgumentException("UserRole not found!"));

        return this.modelMapper.map(role, UserRoleServiceModel.class);
    }
}
