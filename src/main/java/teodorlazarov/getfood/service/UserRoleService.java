package teodorlazarov.getfood.service;

import teodorlazarov.getfood.domain.models.service.UserRoleServiceModel;

public interface UserRoleService {
    UserRoleServiceModel getRoleByRoleName(String roleName);
}
