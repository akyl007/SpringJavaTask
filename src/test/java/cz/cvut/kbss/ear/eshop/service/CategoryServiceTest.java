package cz.cvut.kbss.ear.eshop.service;

import cz.cvut.kbss.ear.eshop.environment.Generator;
import cz.cvut.kbss.ear.eshop.model.Category;
import cz.cvut.kbss.ear.eshop.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class CategoryServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CategoryService sut;

    @Test
    public void removeProductRemovesProductFromCategory() {
        final Product p = Generator.generateProduct();
        final Category category = new Category();
        category.setName("test");
        p.setCategories(new ArrayList<>(Collections.singletonList(category)));
        em.persist(p);
        em.persist(category);
        sut.removeProduct(category, p);

        final Product result = em.find(Product.class, p.getId());
        assertFalse(result.getCategories().stream().anyMatch(c -> c.getId().equals(category.getId())));
    }
}
