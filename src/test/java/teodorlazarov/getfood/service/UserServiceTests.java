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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import teodorlazarov.getfood.domain.entities.User;
import teodorlazarov.getfood.repository.ProductRepository;
import teodorlazarov.getfood.repository.ShoppingCartRepository;
import teodorlazarov.getfood.repository.UserRepository;
import teodorlazarov.getfood.repository.UserRoleRepository;
import teodorlazarov.getfood.web.errors.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserRoleRepository userRoleRepository;
    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    @Mock
    private ProductRepository productRepository;

    private UserRoleService userRoleService;
    private ShoppingCartService shoppingCartService;
    private ProductService productService;

    private ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;

    @Before
    public void setup(){
        modelMapper = new ModelMapper();
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        userRoleService = new UserRoleServiceImpl(userRoleRepository, modelMapper);
        userService = new UserServiceImpl(userRepository, userRoleService, shoppingCartService, modelMapper, bCryptPasswordEncoder);
    }

    @Test
    public void loadUserByUsername_validUser_returnsUser(){
        String username = "pesho";

        User user = new User();
        user.setUsername(username);
        when(userRepository.findUserByUsername(username)).thenReturn(java.util.Optional.of(user));

        Assert.assertEquals(user, userService.loadUserByUsername(username));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_notValidUser_throws(){
        String username = "pesho";

        when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());

        userService.loadUserByUsername(username);
    }

    @Test
    public void findAllUsers_1validUser_returnsUsers(){
        User user = new User();
        user.setUsername("pesho");

        when(userRepository.findAll()).thenReturn(List.of(user));

        Assert.assertEquals(1, userService.findAllUsers().size());
    }

    @Test
    public void findUserByUsername_validUser_returnsUser(){
        String username = "pesho";

        User user = new User();
        user.setUsername(username);
        when(userRepository.findUserByUsername(username)).thenReturn(java.util.Optional.of(user));

        Assert.assertEquals(user.getUsername(), userService.findUserByUsername(username).getUsername());
    }

    @Test(expected = NotFoundException.class)
    public void findUserByUsername_notValidUser_throws(){
        String username = "pesho";

        User user = new User();
        user.setUsername(username);
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());

        Assert.assertEquals(user.getUsername(), userService.findUserByUsername(username).getUsername());
    }


}
