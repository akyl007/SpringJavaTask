package cz.cvut.kbss.ear.eshop.dao;

import cz.cvut.kbss.ear.eshop.exception.PersistenceException;
import cz.cvut.kbss.ear.eshop.model.User;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class UserDao extends BaseDao<User> {
    public UserDao() {
        super(User.class);
    }

    public User findByUsername() {
        try{
        return em.createNamedQuery("User.findByUsername", User.class).getSingleResult();
    }catch (RuntimeException e){
            throw new PersistenceException(e);
        }
    }


    public User findByUsername(String username){
        try{
        return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class).setParameter("username",username).getSingleResult();
    }catch (RuntimeException e){
            return null;
        }
    }


}
