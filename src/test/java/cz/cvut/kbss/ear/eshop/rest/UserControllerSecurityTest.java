package cz.cvut.kbss.ear.eshop.rest;

import cz.cvut.kbss.ear.eshop.config.SecurityConfig;
import cz.cvut.kbss.ear.eshop.environment.Environment;
import cz.cvut.kbss.ear.eshop.environment.Generator;
import cz.cvut.kbss.ear.eshop.environment.TestConfiguration;
import cz.cvut.kbss.ear.eshop.environment.TestSecurityConfig;
import cz.cvut.kbss.ear.eshop.model.Role;
import cz.cvut.kbss.ear.eshop.model.User;
import cz.cvut.kbss.ear.eshop.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(
        classes = {TestSecurityConfig.class,
                UserControllerSecurityTest.TestConfig.class,
                SecurityConfig.class})
public class UserControllerSecurityTest extends BaseControllerTestRunner {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private User user;

    @Before
    public void setUp() {
        this.objectMapper = Environment.getObjectMapper();
        this.user = Generator.generateUser();
    }

    @After
    public void tearDown() {
        Environment.clearSecurityContext();
        Mockito.reset(userService);
    }

    @Configuration
    @TestConfiguration
    public static class TestConfig {

        @Mock
        private UserService userService;
        @InjectMocks
        private UserController userController;

        public TestConfig() {
            MockitoAnnotations.initMocks(this);
        }

        @Bean
        UserService userService() {
            return userService;
        }

        @Bean
        public UserController userController() {
            return userController;
        }
    }

    @WithAnonymousUser
    @Test
    public void registerSupportsAnonymousAccess() throws Exception {
        final User toRegister = Generator.generateUser();
        mockMvc.perform(
                post("/rest/users").content(toJson(toRegister)).contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isCreated());
        verify(userService).persist(any(User.class));
    }

    @WithAnonymousUser
    @Test
    public void registerAdminThrowsUnauthorizedForAnonymousUser() throws Exception {
        final User toRegister = Generator.generateUser();
        toRegister.setRole(Role.ADMIN);

        mockMvc.perform(
                post("/rest/users").content(toJson(toRegister)).contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isUnauthorized());
        verify(userService, never()).persist(any());
    }

    @WithMockUser
    @Test
    public void registerAdminThrowsForbiddenForNonAdminUser() throws Exception {
        user.setRole(Role.USER);
        Environment.setCurrentUser(user);
        final User toRegister = Generator.generateUser();
        toRegister.setRole(Role.ADMIN);

        mockMvc.perform(
                post("/rest/users").content(toJson(toRegister)).contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isForbidden());
        verify(userService, never()).persist(any());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void registerAdminIsAllowedForAdminUser() throws Exception {
        user.setRole(Role.ADMIN);
        Environment.setCurrentUser(user);
        final User toRegister = Generator.generateUser();
        toRegister.setRole(Role.ADMIN);

        mockMvc.perform(
                post("/rest/users").content(toJson(toRegister))
                                   .contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isCreated());
        verify(userService).persist(any(User.class));
    }
}
