package teodorlazarov.getfood.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import teodorlazarov.getfood.domain.entities.ProductType;
import teodorlazarov.getfood.domain.entities.UserRole;
import teodorlazarov.getfood.repository.ProductTypeRepository;
import teodorlazarov.getfood.repository.UserRoleRepository;

import javax.annotation.PostConstruct;

@Component
public class DataBaseSeeder {

    //TODO change repositories with services

    private final UserRoleRepository userRoleRepository;
    private final ProductTypeRepository productTypeRepository;

    @Autowired
    public DataBaseSeeder(UserRoleRepository userRoleRepository, ProductTypeRepository productTypeRepository) {
        this.userRoleRepository = userRoleRepository;
        this.productTypeRepository = productTypeRepository;
    }

    @PostConstruct
    public void seedUserRoles() {
        if (this.userRoleRepository.findAll().isEmpty()) {
            UserRole root = new UserRole();
            root.setRole("ROLE_ROOT");

            UserRole admin = new UserRole();
            admin.setRole("ROLE_ADMIN");

            UserRole user = new UserRole();
            user.setRole("ROLE_USER");

            UserRole employee = new UserRole();
            employee.setRole("ROLE_EMPLOYEE");

            this.userRoleRepository.save(root);
            this.userRoleRepository.save(admin);
            this.userRoleRepository.save(user);
            this.userRoleRepository.save(employee);
        }
    }

    @PostConstruct
    public void seedProductTypes(){
        //SALAD, BURGER, PASTA, POTATOES, DESSERT, SAUCE, BEVERAGE;
        if (this.productTypeRepository.findAll().isEmpty()){
            String[] products = {"Salad", "Burger", "Pasta", "Potatoes", "Dessert", "Sauce", "Beverage"};

            for (String product : products) {
                ProductType productType = new ProductType();
                productType.setName(product);
                productType.setHidden(false);

                this.productTypeRepository.saveAndFlush(productType);
            }
        }
    }
}
