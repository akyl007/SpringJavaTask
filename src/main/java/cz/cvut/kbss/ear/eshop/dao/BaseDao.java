package cz.cvut.kbss.ear.eshop.dao;

import cz.cvut.kbss.ear.eshop.exception.PersistenceException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class BaseDao<T> implements GenericDao<T> {

    @PersistenceContext
    protected EntityManager em;

    protected Class<T> type;
    public BaseDao(Class<T> type)
    {
        this.type = type;
    }    @Override
    public T find(Integer id) {
        Objects.requireNonNull(id);
        try {
            return em.find(type, id);
        }catch (RuntimeException e){
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<T> findAll() {
        try{
            return em.createQuery("SELECT e FROM " + type.getSimpleName()+" e",type).getResultList();
        }catch (RuntimeException e){
            throw new PersistenceException(e);
        }
    }

    @Override
    public void persist(T entity) {
        Objects.requireNonNull(entity);
        try {
            em.persist(entity);
        }catch (RuntimeException e){
            throw new PersistenceException(e);
        }
    }

    @Override
    public void persist(Collection<T> entities) {
        Objects.requireNonNull(entities);
        try{
            for(T entity : entities ){
            em.persist(entity);
            }
        }catch (RuntimeException e){
            throw new PersistenceException(e);
        }
    }

    @Override
    public T update(T entity) {
        try{
            em.merge(entity);
            return entity;
        }catch (RuntimeException e){
            throw new PersistenceException(e);
        }
    }

    @Override
    public void remove(T entity) {
        Objects.requireNonNull(entity);
        try{
            if (!em.contains(entity)) {
                entity = em.merge(entity);
            }

            em.remove(entity);
        }catch (RuntimeException e){
            throw new PersistenceException(e);
        }

    }

    @Override
    public boolean exists(Integer id) {
        try{
        if (id > 0) {
            if (find(id) == em.find(type, id)) {
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    }catch (RuntimeException e){
            throw new PersistenceException(e);
        }
    }
}
