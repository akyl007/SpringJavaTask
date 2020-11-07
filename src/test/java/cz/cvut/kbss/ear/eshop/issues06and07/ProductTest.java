package cz.cvut.kbss.ear.eshop.issues06and07;

import cz.cvut.kbss.ear.eshop.environment.Generator;
import cz.cvut.kbss.ear.eshop.model.Category;
import cz.cvut.kbss.ear.eshop.model.Product;
import cz.cvut.kbss.ear.eshop.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProductTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CategoryService sut;

    @Test
    public void addProductToCategory(){
        final Product p = Generator.generateProduct();
        final Category category = new Category();
        category.setName("test");
        em.persist(p);
        em.persist(category);
        sut.addProduct(category,p);

        final Product result = em.find(Product.class, p.getId());
        assertTrue(result.categories.stream().anyMatch(c->c.getId().equals(category.getId())));

    }

    @Test
    public void addProductToSecondCategoryShouldReturnTrue(){
        final Product p = Generator.generateProduct();
        final Category category1 = new Category();
        category1.setName("test");
        em.persist(p);
        em.persist(category1);
        sut.addProduct(category1,p);

        final Category category2 = new Category();
        category2.setName("test2");
        em.persist(category2);
        sut.addProduct(category2,p);

        final Product result = em.find(Product.class, p.getId());
        assertTrue(result.categories.stream().anyMatch(c->c.getId().equals(category1.getId())));
        assertTrue(result.categories.stream().anyMatch(c->c.getId().equals(category2.getId())));

    }



}
