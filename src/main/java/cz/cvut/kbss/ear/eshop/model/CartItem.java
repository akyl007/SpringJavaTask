package cz.cvut.kbss.ear.eshop.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity

@DiscriminatorValue("CART_ITEM")
public class CartItem extends Item {

    public CartItem(){}
}
