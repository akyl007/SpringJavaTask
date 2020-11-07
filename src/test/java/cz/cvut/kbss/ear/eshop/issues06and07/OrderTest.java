package cz.cvut.kbss.ear.eshop.issues06and07;

import cz.cvut.kbss.ear.eshop.environment.Generator;
import cz.cvut.kbss.ear.eshop.model.Cart;
import cz.cvut.kbss.ear.eshop.model.CartItem;
import cz.cvut.kbss.ear.eshop.model.Order;
import cz.cvut.kbss.ear.eshop.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderTest {

    @PersistenceContext
    private EntityManager em;


    @Test
    public void createOrderCleansCart() {
        final Cart cart = initCartWithItems();
        assertFalse(cart.getItems().isEmpty());
        Objects.requireNonNull(cart);
        final Order order = new Order(cart);
        order.setCreated(LocalDateTime.now());
        em.persist(order);

        assertTrue(cart.getItems().isEmpty());
    }
    private Cart initCartWithItems() {
        final Cart cart = new Cart();
        cart.setOwner(Generator.generateUser());
        em.persist(cart.getOwner());
        em.persist(cart);
        for (int i = 0; i < 5; i++) {
            final Product p = Generator.generateProduct();
            p.setAmount(5);
            em.persist(p);
            final CartItem item = new CartItem();
            item.setProduct(p);
            item.setAmount(i);
            cart.addItem(item);
            em.persist(item);
        }
        return cart;
    }
}
