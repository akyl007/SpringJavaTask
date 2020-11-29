package cz.cvut.kbss.ear.eshop.security;

import cz.cvut.kbss.ear.eshop.environment.Generator;
import cz.cvut.kbss.ear.eshop.model.User;
import cz.cvut.kbss.ear.eshop.security.model.UserDetails;
import cz.cvut.kbss.ear.eshop.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
// SpringBootTest starts the whole Spring application context in test mode
@SpringBootTest
// Transactional on class -> each test is run in a transaction which is rolled back after test finishes
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class DefaultAuthenticationProviderTest {

    @Autowired
    private UserService userService;

    @Autowired
    private DefaultAuthenticationProvider provider;

    private final User user = Generator.generateUser();
    private final String rawPassword = user.getPassword();

    @Before
    public void setUp() {
        userService.persist(user);
        SecurityContextHolder.setContext(new SecurityContextImpl());
    }

    @After
    public void tearDown() {
        SecurityContextHolder.setContext(new SecurityContextImpl());
    }

    @Test
    public void successfulAuthenticationSetsSecurityContext() {
        final Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), rawPassword);
        final SecurityContext context = SecurityContextHolder.getContext();
        assertNull(context.getAuthentication());
        final Authentication result = provider.authenticate(auth);
        assertNotNull(result);
        assertTrue(result.isAuthenticated());
        assertNotNull(SecurityContextHolder.getContext());
        final UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        assertEquals(user.getUsername(), details.getUsername());
        assertTrue(result.isAuthenticated());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void authenticateThrowsUserNotFoundExceptionForUnknownUsername() {
        final Authentication auth = new UsernamePasswordAuthenticationToken("unknownUsername", rawPassword);
        try {
            provider.authenticate(auth);
        } finally {
            final SecurityContext context = SecurityContextHolder.getContext();
            assertNull(context.getAuthentication());
        }
    }

    @Test(expected = BadCredentialsException.class)
    public void authenticateThrowsBadCredentialsForInvalidPassword() {
        final Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), "unknownPassword");
        try {
            provider.authenticate(auth);
        } finally {
            final SecurityContext context = SecurityContextHolder.getContext();
            assertNull(context.getAuthentication());
        }
    }

    @Test
    public void supportsUsernameAndPasswordAuthentication() {
        assertTrue(provider.supports(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void successfulLoginErasesPasswordInSecurityContextUser() {
        final Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), rawPassword);
        provider.authenticate(auth);
        assertNotNull(SecurityContextHolder.getContext());
        final UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        assertNull(details.getUser().getPassword());
    }
}
