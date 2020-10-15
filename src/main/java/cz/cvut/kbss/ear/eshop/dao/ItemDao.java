package cz.cvut.kbss.ear.eshop.dao;

import cz.cvut.kbss.ear.eshop.model.Item;
import org.springframework.stereotype.Repository;

@Repository
public class ItemDao extends BaseDao<Item> {
    public ItemDao() {
        super(Item.class);
    }
}
