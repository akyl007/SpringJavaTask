package cz.cvut.kbss.ear.eshop.dao;

import cz.cvut.kbss.ear.eshop.exception.PersistenceException;
import cz.cvut.kbss.ear.eshop.model.Category;
import cz.cvut.kbss.ear.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao extends BaseDao<Product>{
    public ProductDao() {
        super(Product.class);
    }
    public List<Product> findAll() {
        try{
            return em.createQuery("SELECT p FROM Product p WHERE NOT p.removed",Product.class).getResultList();
        }catch (RuntimeException e){
            throw new PersistenceException(e);
        }
    }
    public List<Product> findAll(Category cat){
        return em.createNamedQuery("Product.findByCategory",Product.class).setParameter("category",cat).getResultList();
    }
}
