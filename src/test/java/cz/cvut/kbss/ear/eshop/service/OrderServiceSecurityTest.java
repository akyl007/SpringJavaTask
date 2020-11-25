package cz.cvut.kbss.ear.eshop.service;

import cz.cvut.kbss.ear.eshop.environment.Generator;
import cz.cvut.kbss.ear.eshop.model.Order;
import cz.cvut.kbss.ear.eshop.model.Role;
import cz.cvut.kbss.ear.eshop.model.User;
import cz.cvut.kbss.ear.eshop.security.SecurityUtils;
import cz.cvut.kbss.ear.eshop.security.model.UserDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderServiceSecurityTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private OrderService orderService;

    @Test
    public void findAllReturnsOrdersOfUserForRegularUser() {
        final User user = Generator.generateUser();
        user.setRole(Role.USER);
        em.persist(user);
        SecurityUtils.setCurrentUser(new UserDetails(user));

        final List<Order> allOrders = generateOrders(user);
        final List<Order> result = orderService.findAll();
        final List<Order> expected = allOrders.stream().filter(o -> o.getCustomer().getId().equals(user.getId()))
                                              .collect(
                                                      Collectors.toList());
        assertEquals(expected.size(), result.size());
        for (Order o : result) {
            assertTrue(expected.stream().anyMatch(exp -> exp.getId().equals(o.getId())));
        }
    }

    @Test
    public void findAllReturnsAllOrdersForAdminUser() {
        final User user = Generator.generateUser();
        user.setRole(Role.ADMIN);
        em.persist(user);
        SecurityUtils.setCurrentUser(new UserDetails(user));

        final List<Order> allOrders = generateOrders(user);
        final List<Order> result = orderService.findAll();
        assertEquals(allOrders.size(), result.size());
    }

    private List<Order> generateOrders(User user) {
        final User otherUser = Generator.generateUser();
        otherUser.setRole(Role.USER);
        em.persist(otherUser);
        final List<Order> orders = new ArrayList<>(20);
        for (int i = 0; i < 20; i++) {
            final Order order = new Order();
            order.setCreated(LocalDateTime.now());
            order.setCustomer(Generator.randomBoolean() ? user : otherUser);
            em.persist(order);
            orders.add(order);
        }
        return orders;
    }

}
