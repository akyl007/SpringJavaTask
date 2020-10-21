package cz.cvut.kbss.ear.eshop.dao;

import cz.cvut.kbss.ear.eshop.model.Order;

public class OrderDao extends BaseDao<Order> {

    public OrderDao() {
        super(Order.class);
    }
}
