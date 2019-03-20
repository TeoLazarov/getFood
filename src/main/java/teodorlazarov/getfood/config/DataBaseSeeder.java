package teodorlazarov.getfood.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import teodorlazarov.getfood.domain.entities.UserRole;
import teodorlazarov.getfood.repository.UserRoleRepository;

import javax.annotation.PostConstruct;

@Component
public class DataBaseSeeder {

    //TODO change userRoleRepository with userRoleService
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public DataBaseSeeder(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @PostConstruct
    public void seed() {
        if (this.userRoleRepository.findAll().isEmpty()) {
            UserRole admin = new UserRole();
            admin.setRole("ADMIN");

            UserRole user = new UserRole();
            user.setRole("USER");

            UserRole employee = new UserRole();
            employee.setRole("EMPLOYEE");

            this.userRoleRepository.save(admin);
            this.userRoleRepository.save(user);
            this.userRoleRepository.save(employee);
        }
    }
}
