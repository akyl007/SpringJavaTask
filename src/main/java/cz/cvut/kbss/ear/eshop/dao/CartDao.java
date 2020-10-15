package cz.cvut.kbss.ear.eshop.dao;

import cz.cvut.kbss.ear.eshop.model.Cart;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
@Repository
public class CartDao extends BaseDao<Cart>{

    public CartDao() {
        super(Cart.class);
    }
}
