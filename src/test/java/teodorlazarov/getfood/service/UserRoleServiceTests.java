package teodorlazarov.getfood.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import teodorlazarov.getfood.domain.entities.UserRole;
import teodorlazarov.getfood.domain.models.service.UserRoleServiceModel;
import teodorlazarov.getfood.repository.UserRoleRepository;
import teodorlazarov.getfood.web.errors.exceptions.NotFoundException;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRoleServiceTests {

    @Mock
    private UserRoleRepository userRoleRepository;

    private ModelMapper modelMapper;
    private UserRoleService userRoleService;

    @Before
    public void setUp(){
        modelMapper = new ModelMapper();
        userRoleService = new UserRoleServiceImpl(userRoleRepository, modelMapper);
    }

    @Test
    public void getRoleByRoleName_validName_returnRole(){
        UserRoleServiceModel actual = new UserRoleServiceModel();
        actual.setRole("TEST_ROLE");

        UserRole expected = new UserRole();
        expected.setRole(actual.getRole());

        when(userRoleRepository.findByRole(actual.getRole())).thenReturn(java.util.Optional.of(expected));

        Assert.assertEquals(expected.getRole(), userRoleService.getRoleByRoleName(actual.getRole()).getRole());
    }

    @Test(expected = NotFoundException.class)
    public void getRoleByRoleName_invalidName_throws(){
        UserRoleServiceModel actual = new UserRoleServiceModel();
        actual.setRole("TEST_ROLE");

        when(userRoleRepository.findByRole(actual.getRole())).thenReturn(Optional.empty());

        userRoleService.getRoleByRoleName(actual.getRole());
    }
}
