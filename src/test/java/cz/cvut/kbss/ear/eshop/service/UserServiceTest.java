package cz.cvut.kbss.ear.eshop.service;

import cz.cvut.kbss.ear.eshop.dao.UserDao;
import cz.cvut.kbss.ear.eshop.environment.Environment;
import cz.cvut.kbss.ear.eshop.environment.Generator;
import cz.cvut.kbss.ear.eshop.exception.CartAccessException;
import cz.cvut.kbss.ear.eshop.model.Cart;
import cz.cvut.kbss.ear.eshop.model.Role;
import cz.cvut.kbss.ear.eshop.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * This test does not use Spring.
 * <p>
 * It showcases how services can be unit tested without being dependent on the application framework or database.
 */
public class UserServiceTest {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mock
    private UserDao userDaoMock;

    private UserService sut;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.sut = new UserService(userDaoMock, passwordEncoder);
    }

    @Test
    public void persistEncodesUserPassword() {
        final User user = Generator.generateUser();
        final String rawPassword = user.getPassword();
        sut.persist(user);

        final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userDaoMock).persist(captor.capture());
        assertTrue(passwordEncoder.matches(rawPassword, captor.getValue().getPassword()));
    }

    @Test
    public void persistCreatesCartForRegularUser() {
        final User user = Generator.generateUser();
        user.setRole(Role.USER);
        sut.persist(user);

        final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userDaoMock).persist(captor.capture());
        assertNotNull(captor.getValue().getCart());
    }

    @Test
    public void persistCreatesCartForGuestUser() {
        final User user = Generator.generateUser();
        user.setRole(Role.GUEST);
        sut.persist(user);

        final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userDaoMock).persist(captor.capture());
        assertNotNull(captor.getValue().getCart());
    }

    @Test
    public void persistSetsUserRoleToDefaultWhenItIsNotSpecified() {
        final User user = Generator.generateUser();
        user.setRole(null);
        sut.persist(user);

        final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userDaoMock).persist(captor.capture());
        assertEquals(Role.USER, captor.getValue().getRole());
    }

    @Test
    public void getCurrentUserCartRetrievesCurrentUsersCart() {
        final User user = Generator.generateUser();
        user.setCart(new Cart());
        Environment.setCurrentUser(user);
        assertSame(user.getCart(), sut.getCurrentUserCart());
    }

    @Test(expected = CartAccessException.class)
    public void getCurrentUserCartThrowsCartAccessExceptionForAdminUser() {
        final User user = Generator.generateUser();
        user.setRole(Role.ADMIN);
        Environment.setCurrentUser(user);
        sut.getCurrentUserCart();
    }
}
