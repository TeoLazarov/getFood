package teodorlazarov.getfood.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import teodorlazarov.getfood.domain.entities.ProductType;
import teodorlazarov.getfood.domain.entities.UserRole;
import teodorlazarov.getfood.repository.ProductTypeRepository;
import teodorlazarov.getfood.repository.UserRoleRepository;

import javax.annotation.PostConstruct;

import static teodorlazarov.getfood.constants.GlobalConstants.*;

@Component
public class DataBaseSeeder {

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
            root.setRole(USER_ROLE_ROOT);

            UserRole admin = new UserRole();
            admin.setRole(USER_ROLE_ADMIN);

            UserRole employee = new UserRole();
            employee.setRole(USER_ROLE_EMPLOYEE);

            UserRole user = new UserRole();
            user.setRole(USER_ROLE_USER);

            this.userRoleRepository.save(root);
            this.userRoleRepository.save(admin);
            this.userRoleRepository.save(user);
            this.userRoleRepository.save(employee);
        }
    }

    @PostConstruct
    public void seedProductTypes(){
        if (this.productTypeRepository.findAll().isEmpty()){
            String[] products = {
                    PRODUCT_CATEGORY_SALAD,
                    PRODUCT_CATEGORY_BURGER,
                    PRODUCT_CATEGORY_PASTA,
                    PRODUCT_CATEGORY_POTATOES,
                    PRODUCT_CATEGORY_DESSERT,
                    PRODUCT_CATEGORY_SAUCE,
                    PRODUCT_CATEGORY_BEVERAGE
            };

            for (String product : products) {
                ProductType productType = new ProductType();
                productType.setName(product);

                this.productTypeRepository.saveAndFlush(productType);
            }
        }
    }
}
