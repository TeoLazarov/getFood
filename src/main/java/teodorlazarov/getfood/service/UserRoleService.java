package teodorlazarov.getfood.service;

import teodorlazarov.getfood.domain.models.UserRoleServiceModel;

public interface UserRoleService {
    UserRoleServiceModel getRoleByRoleName(String roleName);
}
