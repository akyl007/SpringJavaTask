package cz.cvut.kbss.ear.eshop.service;

import cz.cvut.kbss.ear.eshop.dao.UserDao;
import cz.cvut.kbss.ear.eshop.environment.Generator;
import cz.cvut.kbss.ear.eshop.model.Role;
import cz.cvut.kbss.ear.eshop.model.User;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;

/**
 * This test does not use Spring.
 *
 * It showcases how services can be unit tested without being dependent on the application framework or database.
 */
public class UserServiceTest {

    @Mock
    private UserDao userDaoMock;

    private UserService sut;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.sut = new UserService(userDaoMock);
    }

    @Test
    public void persistEncodesUserPassword() {
        final User user = Generator.generateUser();
        final String rawPassword = user.getPassword();
        sut.persist(user);

        final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userDaoMock).persist(captor.capture());
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
}
